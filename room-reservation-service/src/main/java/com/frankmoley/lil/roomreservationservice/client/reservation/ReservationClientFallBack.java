package com.frankmoley.lil.roomreservationservice.client.reservation;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReservationClientFallBack implements ReservationServiceClient {

    @Override
    public List<Reservation> getAll(Long guestId, String dateString) {
        return List.of();
    }

    @Override
    public ResponseEntity<Boolean> checkRoomAvailability(Long roomId, String dateString) {
        return null;
    }

    @Override
    public Reservation createReservation(Reservation reservation) {
        return null;
    }

    @Override
    public Reservation getReservation(long id) {
        return null;
    }

    @Override
    public void updateReservation(long id, Reservation reservation) {

    }

    @Override
    public void deleteReservation(long id) {

    }
}
