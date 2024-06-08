package com.iraianbu.orderservice.service;

import com.iraianbu.orderservice.body.OrderLineRequest;
import com.iraianbu.orderservice.body.OrderRequest;
import com.iraianbu.orderservice.body.OrderResponse;
import com.iraianbu.orderservice.body.PurchaseRequest;
import com.iraianbu.orderservice.customer.CustomerClient;
import com.iraianbu.orderservice.kafka.OrderConfirmation;
import com.iraianbu.orderservice.kafka.OrderProducer;
import com.iraianbu.orderservice.orderline.OrderLineService;
import com.iraianbu.orderservice.payment.PaymentClient;
import com.iraianbu.orderservice.payment.PaymentRequest;
import com.iraianbu.orderservice.product.ProductClient;
import com.iraianbu.orderservice.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CustomerClient customerClient;
    private final PaymentClient paymentClient;
    private final ProductClient productClient;
    private final ProductMapper mapper;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;
    public Integer createOrder(OrderRequest request) {
        // Check the Customer -> OpenFeign
         var customer = this.customerClient.findCustomerById(request.customerId())
                 .orElseThrow(() -> new RuntimeException("Cannot Create Order -> No Customer Found"));

        // Purchase the Products -> RestTemplate
       var purchasedProducts =  this.productClient.purchaseProducts(request.products());

        // Persist Order

        var order = this.orderRepository.save(mapper.toOrder(request));

        //Persist OrderLine

        for(PurchaseRequest purchaseRequest : request.products()){
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );
        }

        // Start Payment Process -> Kafka
        var paymentRequest = new PaymentRequest(
            request.amount(),
                request.paymentMethod(),
                order.getId(),
                order.getReference(),
                customer
        );
        paymentClient.requestOrderPayment(paymentRequest);

        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        request.reference(),
                        request.amount(),
                        request.paymentMethod(),
                        customer,
                        purchasedProducts
                )
        );

        // Sent the order confirmation

    return order.getId();
    }

    public List<OrderResponse > findAll() {
        return orderRepository.findAll()
                .stream()
                .map(mapper::fromOrder)
                .collect(Collectors.toList());
    }

    public OrderResponse findById(Integer orderId) {
        return orderRepository.findById(orderId)
                .map(mapper::fromOrder)
                .orElseThrow(() -> new EntityNotFoundException("No order found with this id " + orderId));
    }
}
