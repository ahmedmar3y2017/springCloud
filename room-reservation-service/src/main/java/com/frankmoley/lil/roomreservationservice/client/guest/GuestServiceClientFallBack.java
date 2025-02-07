package com.frankmoley.lil.roomreservationservice.client.guest;


import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class GuestServiceClientFallBack implements GuestServiceClient {
    @Override
    public List<Guest> getAll() {
        return new ArrayList<>();
    }

    @Override
    public Guest addGuest(Guest guest) {
        return null;
    }

    @Override
    public Guest getGuest(long id) {
        return null;
    }

    @Override
    public Guest getGuestByEmail(String email) {
        return null;
    }

    @Override
    public void updateGuest(long id, Guest guest) {

    }

    @Override
    public void deleteGuest(long id) {

    }
}
