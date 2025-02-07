package com.frankmoley.lil.roomreservationservice.client.room;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

//@Service
@FeignClient(value = "room-service", fallback = RoomServiceClientFallBack.class)
public interface RoomServiceClient {

    @GetMapping("/rooms")
    public List<Room> getAll();

    @PostMapping("/rooms")
    public Room addRoom(@RequestBody Room room);

    @GetMapping("/rooms/{id}")
    public Room getRoom(@PathVariable("id") long id);

    @PutMapping("/rooms/{id}")
    public void updateRoom(@PathVariable("id") long id, @RequestBody Room room);

    @DeleteMapping("/rooms/{id}")
    public void deleteRoom(@PathVariable("id") long id);
}
