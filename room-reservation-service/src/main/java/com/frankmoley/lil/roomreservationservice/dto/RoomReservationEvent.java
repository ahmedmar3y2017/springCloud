package com.frankmoley.lil.roomreservationservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)

public class RoomReservationEvent {
    String type;
    RoomReservationDto roomReservationDto;
}
