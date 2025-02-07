package com.frankmoley.lil.reservationservice.data;

import org.springframework.data.repository.CrudRepository;

import java.sql.Date;
import java.util.List;

public interface ReservationRepository extends CrudRepository<ReservationEntity, Long> {
    Iterable<ReservationEntity> findReservationEntitiesByGuestId(long guestId);

    Iterable<ReservationEntity> findReservationEntitiesByDate(Date date);

    Iterable<ReservationEntity> findReservationEntitiesByDateAndGuestId(Date date, long guestId);
    List<ReservationEntity> findReservationEntitiesByDateAndRoomId(Date date, long roomId);
}
