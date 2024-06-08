package com.iraianbu.notificationservice.repository;

import com.iraianbu.notificationservice.kafka.payment.PaymentConfirmation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends MongoRepository<PaymentConfirmation, String> {
}
