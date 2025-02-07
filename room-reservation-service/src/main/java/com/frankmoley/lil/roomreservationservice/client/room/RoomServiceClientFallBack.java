package com.frankmoley.lil.roomreservationservice.client.room;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RoomServiceClientFallBack implements RoomServiceClient{
    @Override
    public List<Room> getAll() {
        return new ArrayList<>();
    }

    @Override
    public Room addRoom(Room room) {
        return null;
    }

    @Override
    public Room getRoom(long id) {
        return null;
    }


    @Override
    public void updateRoom(long id, Room room) {

    }

    @Override
    public void deleteRoom(long id) {

    }
}
