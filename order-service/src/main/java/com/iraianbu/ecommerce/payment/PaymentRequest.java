package com.iraianbu.ecommerce.payment;

import com.iraianbu.ecommerce.customer.CustomerResponse;
import com.iraianbu.ecommerce.model.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        CustomerResponse customer
) {
}
