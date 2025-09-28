//package com.frankmoley.lil.roomreservationservice.services;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.frankmoley.lil.roomreservationservice.client.reservation.Reservation;
//import com.frankmoley.lil.roomreservationservice.client.reservation.ReservationClientFallBack;
//import com.frankmoley.lil.roomreservationservice.client.reservation.ReservationServiceClient;
//import com.frankmoley.lil.roomreservationservice.dto.RoomReservationDto;
//import com.frankmoley.lil.roomreservationservice.dto.RoomReservationEvent;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Service;
//
//@Service
//public class ReservationReverse {
//
//    @Autowired
//    ReservationServiceClient reservationServiceClient;
//
//    @KafkaListener(topics = "room-reservation-reversed", groupId = "room-reservation-consumer")
//    public void listen(String message) throws JsonProcessingException {
//
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
//    }
//}
