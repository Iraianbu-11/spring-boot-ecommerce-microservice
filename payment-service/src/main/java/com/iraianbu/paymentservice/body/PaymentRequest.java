package com.iraianbu.paymentservice.body;

import com.iraianbu.paymentservice.model.Customer;
import com.iraianbu.paymentservice.model.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        Integer id,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        Customer customer
) {
}
