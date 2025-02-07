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
public class ReservationCreated {

    @Autowired
    PaymentRepository paymentRepository;

    private final KafkaTemplate<String, PaymentReservationEvent> paymentReservationEventKafkaTemplate;

    public ReservationCreated(KafkaTemplate<String, PaymentReservationEvent> paymentReservationEventKafkaTemplate) {
        this.paymentReservationEventKafkaTemplate = paymentReservationEventKafkaTemplate;
    }

    @KafkaListener(topics = "room-reservation-created", groupId = "room-payment-consumer")
    public void listen(String message) throws JsonProcessingException {

        RoomReservationEvent roomReservationEvent = new ObjectMapper().readValue(message, RoomReservationEvent.class);
        Payment payment = new Payment();
        RoomReservationDto roomReservationDto = roomReservationEvent.getRoomReservationDto();

        try {

            // payment - > create payment -> update status : success
            // if failed -> update status -> send message to payment_reversed
            // create payment_reversed consumer for set status failed


            payment.setReservationId(roomReservationDto.getReservationId());
            payment.setAmount(roomReservationDto.getCost());
            payment.setStatus("Success");
            payment.setMode(roomReservationDto.getMode());

            Payment save = paymentRepository.save(payment);

            roomReservationDto.setPaymentId(save.getPaymentId());
//            throw new Exception("Error ddd") ;
            // send mail success


        } catch (Exception e) {

            e.printStackTrace();
            payment.setStatus("Failed");
            Payment save = paymentRepository.save(payment);

            PaymentReservationEvent reservationEvent=new PaymentReservationEvent();
            reservationEvent.setType("payment-created");
            reservationEvent.setRoomReservationDto(roomReservationDto);


            paymentReservationEventKafkaTemplate.send("payment-reservation-reversed" ,reservationEvent );
        }


//        // convert to Event Model
//        RoomReservationEvent roomReservationEvent = new ObjectMapper().readValue(message, RoomReservationEvent.class);
//        RoomReservationDto roomReservationDto = roomReservationEvent.getRoomReservationDto();
//
//        // find reservation is exists or Not
//        Reservation serviceClientReservation = reservationServiceClient.getReservation(roomReservationDto.getReservationId());
//
//
//        // update DB status Failed
//        serviceClientReservation.setStatus("Failed");
//        // update DB
//        reservationServiceClient.updateReservation(serviceClientReservation.getReservationId(), serviceClientReservation);
//        // send mail failed reservation
//        // in progress
//
//        System.out.println("Reversed Reservation Message: " + message);
    }
}
