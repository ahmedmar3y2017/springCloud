package com.frankmoley.lil.roomreservationservice.client.guest;

import com.frankmoley.lil.roomreservationservice.client.reservation.ReservationClientFallBack;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

//@Service
@FeignClient(value = "guest-service" , fallback = GuestServiceClientFallBack.class)
public interface GuestServiceClient {

    @GetMapping("/guests")
    public List<Guest> getAll();

    @PostMapping("/guests")
    public Guest addGuest(@RequestBody Guest guest);

    @GetMapping("/guests/{id}")
    public Guest getGuest(@PathVariable("id") long id);

    @GetMapping("/guests/{email}")
    public Guest getGuestByEmail(@PathVariable("email") String email);

    @PutMapping("/guests/{id}")
    public void updateGuest(@PathVariable("id") long id, @RequestBody Guest guest);

    @DeleteMapping("/guests/{id}")
    public void deleteGuest(@PathVariable("id") long id);

}
