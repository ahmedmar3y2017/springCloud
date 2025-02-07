package com.frankmoley.lil.roomreservationservice.client.reservation;

import com.frankmoley.lil.roomreservationservice.client.room.RoomServiceClientFallBack;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@Service
@FeignClient(value = "reservation-service" , fallback = ReservationClientFallBack.class)
public interface ReservationServiceClient {


    @GetMapping("/reservations")
    public List<Reservation> getAll(@RequestParam(value = "guestId", required = false) Long guestId,
                                    @RequestParam(value = "date", required = false) String dateString
    );

    @GetMapping("/reservations/available")
    public ResponseEntity<Boolean> checkRoomAvailability(@RequestParam(value = "roomId", required = true) Long roomId,
                                    @RequestParam(value = "date", required = true) String dateString
    );

    @PostMapping("/reservations")
    public Reservation createReservation(@RequestBody Reservation reservation);

    @GetMapping("/reservations/{id}")
    public Reservation getReservation(@PathVariable("id") long id);

    @PutMapping("/reservations/{id}")
    public void updateReservation(@PathVariable("id") long id, @RequestBody Reservation reservation);

    @DeleteMapping("/reservations/{id}")
    public void deleteReservation(@PathVariable("id") long id);
}
