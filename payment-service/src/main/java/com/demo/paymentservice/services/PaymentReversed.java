package com.demo.paymentservice.services;

import com.demo.paymentservice.dto.PaymentReservationEvent;
import com.demo.paymentservice.dto.RoomReservationDto;
import com.demo.paymentservice.dto.RoomReservationEvent;
import com.demo.paymentservice.payment.Payment;
import com.demo.paymentservice.payment.PaymentRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PaymentReversed {

    @Autowired
    PaymentRepository paymentRepository;

    private final KafkaTemplate<String, RoomReservationEvent> paymentReservationEventKafkaTemplate;

    public PaymentReversed(KafkaTemplate<String, RoomReservationEvent> paymentReservationEventKafkaTemplate) {
        this.paymentReservationEventKafkaTemplate = paymentReservationEventKafkaTemplate;
    }

    @KafkaListener(topics = "payment-reservation-reversed", groupId = "room-payment-consumer")
    public void listen(String message) throws JsonProcessingException {

        PaymentReservationEvent paymentReservationEvent = new ObjectMapper().readValue(message, PaymentReservationEvent.class);
        RoomReservationDto roomReservationDto = paymentReservationEvent.getRoomReservationDto();

        RoomReservationEvent roomReservationEvent1=new RoomReservationEvent();

        roomReservationEvent1.setRoomReservationDto(roomReservationDto);
        roomReservationEvent1.setType("payment-failed");

        paymentReservationEventKafkaTemplate.send("room-reservation-reversed", roomReservationEvent1);

    }
}
