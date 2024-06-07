package com.iraianbu.orderservice.service;

import com.iraianbu.orderservice.body.OrderRequest;
import com.iraianbu.orderservice.body.OrderResponse;
import com.iraianbu.orderservice.model.Order;
import org.springframework.stereotype.Service;

@Service
public class ProductMapper {
    public Order toOrder(OrderRequest request) {
        return  Order.builder()
                .id(request.id())
                .customerId(request.customerId())
                .reference(request.reference())
                .totalAmount(request.amount())
                .paymentMethod(request.paymentMethod())
                .build();
    }

    public OrderResponse fromOrder(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getReference(),
                order.getTotalAmount(),
                order.getPaymentMethod(),
                order.getCustomerId()
        );
    }
}
