package com.frankmoley.lil.roomreservationservice.client.room;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class RoomServiceClient {

    private final RestTemplate restTemplate;

    @Value("${ROOM_SERVICE_URL}")
    private String roomServiceUrl;

    private final static String ROOMS_URL_PART = "/rooms";
    private final static String SLASH = "/";

    public RoomServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Room> getAll() {
        String url = roomServiceUrl + ROOMS_URL_PART;
        ResponseEntity<Room[]> response = this.restTemplate.getForEntity(url, Room[].class);
        return Arrays.asList(response.getBody());
    }

    public Room addRoom(Room room) {
        String url = roomServiceUrl + ROOMS_URL_PART;
        ResponseEntity<Room> response = this.restTemplate.postForEntity(url, room, Room.class);
        return response.getBody();
    }

    public Room getRoom(long id) {
        String url = roomServiceUrl + ROOMS_URL_PART + SLASH + id;
        ResponseEntity<Room> response = this.restTemplate.getForEntity(url, Room.class);
        return response.getBody();
    }

    public void updateRoom(Room room) {
        String url = roomServiceUrl + ROOMS_URL_PART + SLASH + room.getRoomId();
        this.restTemplate.put(url, room);
    }

    public void deleteRoom(long id) {
        String url = roomServiceUrl + ROOMS_URL_PART + SLASH + id;
        this.restTemplate.delete(url);
    }
}
