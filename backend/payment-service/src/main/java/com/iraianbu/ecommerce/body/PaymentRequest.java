package com.iraianbu.ecommerce.body;

import com.iraianbu.ecommerce.model.Customer;
import com.iraianbu.ecommerce.model.PaymentMethod;

import java.io.Serializable;
import java.math.BigDecimal;

public record PaymentRequest(
        Integer id,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        Customer customer
) implements Serializable {
}
