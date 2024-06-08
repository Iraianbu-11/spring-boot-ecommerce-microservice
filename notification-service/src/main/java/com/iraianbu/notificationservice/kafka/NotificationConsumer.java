package com.iraianbu.notificationservice.kafka;

import com.iraianbu.notificationservice.kafka.payment.PaymentConfirmation;
import com.iraianbu.notificationservice.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {
    private final NotificationRepository notificationRepository;

    public void consumePaymentSuccessNotification(PaymentConfirmation ) {

    }
}
