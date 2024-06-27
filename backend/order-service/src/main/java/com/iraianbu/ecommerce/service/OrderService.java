package com.iraianbu.ecommerce.service;

import com.iraianbu.ecommerce.body.OrderLineRequest;
import com.iraianbu.ecommerce.body.OrderRequest;
import com.iraianbu.ecommerce.body.OrderResponse;
import com.iraianbu.ecommerce.body.PurchaseRequest;
import com.iraianbu.ecommerce.customer.CustomerClient;
import com.iraianbu.ecommerce.kafka.OrderConfirmation;
import com.iraianbu.ecommerce.kafka.OrderProducer;
import com.iraianbu.ecommerce.orderline.OrderLineService;
import com.iraianbu.ecommerce.payment.PaymentClient;
import com.iraianbu.ecommerce.payment.PaymentRequest;
import com.iraianbu.ecommerce.product.ProductClient;
import com.iraianbu.ecommerce.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Slf4j
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
         log.info("Order Request {}" , request);
        log.info("Customer Details {}" , customer);
        // Purchase the Products -> RestTemplate
       var purchasedProducts =  this.productClient.purchaseProducts(request.products());
        log.info("Purchased Products Detail {}",purchasedProducts);
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
