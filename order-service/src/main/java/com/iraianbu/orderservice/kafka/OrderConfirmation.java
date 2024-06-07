package com.iraianbu.orderservice.kafka;

import com.iraianbu.orderservice.body.PurchaseResponse;
import com.iraianbu.orderservice.customer.CustomerResponse;
import com.iraianbu.orderservice.model.PaymentMethod;
import java.util.List;
import java.math.BigDecimal;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products
) {
}
