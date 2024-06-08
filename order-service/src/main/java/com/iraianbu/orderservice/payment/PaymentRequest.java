package com.iraianbu.orderservice.payment;

import com.iraianbu.orderservice.customer.CustomerResponse;
import com.iraianbu.orderservice.model.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        CustomerResponse customer
) {
}
