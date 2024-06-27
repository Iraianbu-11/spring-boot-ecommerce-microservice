package com.iraianbu.ecommerce.kafka;

import com.iraianbu.ecommerce.body.PurchaseResponse;
import com.iraianbu.ecommerce.customer.CustomerResponse;
import com.iraianbu.ecommerce.model.PaymentMethod;
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
