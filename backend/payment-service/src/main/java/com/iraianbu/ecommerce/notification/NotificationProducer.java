package com.iraianbu.ecommerce.notification;

import com.iraianbu.ecommerce.body.PaymentNotificationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationProducer {
    private final KafkaTemplate<String, PaymentNotificationRequest> kafkaTemplate;

    public void sendNotification(PaymentNotificationRequest request) {
        log.info("Sending payment notification with body <{}> ",request);
        Message<PaymentNotificationRequest> msg = MessageBuilder
                .withPayload(request)
                .setHeader(KafkaHeaders.TOPIC,"payment-topic")
                .build();
        kafkaTemplate.send(msg);
    }
}
