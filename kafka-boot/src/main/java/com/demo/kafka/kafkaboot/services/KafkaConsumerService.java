package com.demo.kafka.kafkaboot.services;


import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @KafkaListener(topics = "kafka.boot.orders", groupId = "kafka-boot-consumer")
    public void listen(String message) {
        System.out.println("Received Message: " + message);
    }
}