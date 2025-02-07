package com.demo.paymentservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoomReservationDto {

    private long guestId;
    private long reservationId;
    private long paymentId;
    private String firstName;
    private String lastName;
    private String EMAIL_ADDRESS; // mandatory
    private String phone;
    private long roomNumber; // mandatory
    private String date; // mandatory
    private String mode;
    private double cost; // mandatory
    private String status;

}
