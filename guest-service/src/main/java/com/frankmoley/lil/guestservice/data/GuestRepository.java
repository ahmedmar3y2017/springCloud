package com.frankmoley.lil.guestservice.data;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface GuestRepository extends CrudRepository<Guest, Long> {
//    Iterable<Guest> findGuestsByEmailAddress(String emailAddress);

    Optional<Guest> findGuestsByEmailAddress(String emailAddress);
}
