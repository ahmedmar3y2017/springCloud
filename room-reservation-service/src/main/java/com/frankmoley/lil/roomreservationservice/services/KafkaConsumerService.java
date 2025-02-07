//package com.frankmoley.lil.roomreservationservice.services;
//
//
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Service;
//
//@Service
//public class KafkaConsumerService {
//
//    @KafkaListener(topics = "room-reservation-reversed", groupId = "room-reservation-consumer")
//    public void listen(String message) {
//        System.out.println("Received Message: " + message);
//    }
//}