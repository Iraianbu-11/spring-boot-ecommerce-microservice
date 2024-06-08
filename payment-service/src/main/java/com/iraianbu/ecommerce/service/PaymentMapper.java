package com.iraianbu.ecommerce.service;

import com.iraianbu.ecommerce.body.PaymentRequest;
import com.iraianbu.ecommerce.model.Payment;
import org.springframework.stereotype.Service;

@Service
public class PaymentMapper {

    public Payment toPayment(PaymentRequest request) {
        return Payment.builder()
                .id(request.id())
                        .orderId(request.orderId())
                                .paymentMethod(request.paymentMethod())
                                        .amount(request.amount())
                .build();
    }
}
