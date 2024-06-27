package com.iraianbu.ecommerce.service;

import com.iraianbu.ecommerce.body.PaymentNotificationRequest;
import com.iraianbu.ecommerce.body.PaymentRequest;
import com.iraianbu.ecommerce.model.Payment;
import com.iraianbu.ecommerce.notification.NotificationProducer;
import com.iraianbu.ecommerce.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final NotificationProducer notificationProducer;
    public Integer createPayment(PaymentRequest request) {
        Payment payment = paymentRepository.save(paymentMapper.toPayment(request));
        if(request.orderReference() != null && request.amount()!=null
        && request.paymentMethod()!=null && request.customer().firstName() != null &&
        request.customer().lastName() !=null && request.customer().email() !=null) {
          log.warn("No Values are null");
        }
        else {
            log.warn("One or More values are null in PaymentRequest");
        }
        notificationProducer.sendNotification(
                new PaymentNotificationRequest(
                        request.orderReference(),
                        request.amount(),
                        request.paymentMethod(),
                        request.customer().firstName(),
                        request.customer().lastName(),
                        request.customer().email()
                )
        );
        return payment.getId();
    }
}
