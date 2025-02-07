package com.frankmoley.lil.guestservice.api;

import com.frankmoley.lil.guestservice.data.Guest;
import com.frankmoley.lil.guestservice.data.GuestRepository;
import com.frankmoley.lil.guestservice.error.BadReqeustException;
import com.frankmoley.lil.guestservice.error.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequestMapping("/guests")
public class GuestController {

    private final GuestRepository guestRepository;

    public GuestController(GuestRepository guestRepository) {
        this.guestRepository = guestRepository;
    }


    @GetMapping
    public Guest getGuests(@RequestParam(value = "emailAddress", required = false) String emailAddress) {
        if (StringUtils.hasLength(emailAddress)) {
            return this.guestRepository.findGuestsByEmailAddress(emailAddress).orElseThrow(() ->
            {
                throw new NotFoundException("Guest not found: " + emailAddress);

            });
        }
        throw new NotFoundException("Guest not found: " + emailAddress);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Guest addGuest(@RequestBody Guest guest) {
        return this.guestRepository.save(guest);
    }

    @GetMapping("/{id}")
    public Guest getGuest(@PathVariable("id") Long id) {
        Optional<Guest> guest = this.guestRepository.findById(id);
        if (guest.isEmpty()) {
            throw new NotFoundException("id not found: " + id);
        }
        return guest.get();
    }

    @GetMapping("/{email}")
    public ResponseEntity<Guest> getGuestByEmail(@PathVariable(value = "email", required = true) String emailAddress) {
        if (StringUtils.hasLength(emailAddress)) {
            Optional<Guest> guestsByEmailAddress = this.guestRepository.findGuestsByEmailAddress(emailAddress);
            if (!guestsByEmailAddress.isPresent())
                throw new NotFoundException("id not found: " + id);
            else return ResponseEntity.ok(guestsByEmailAddress.get());


        }
        throw new NotFoundException("id not found: " + id);
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateGuest(@PathVariable("id") Long id, @RequestBody Guest guest) {
        if (id != guest.getGuestId()) {
            throw new BadReqeustException("incoming id in body doesn't match path");
        }
        this.guestRepository.save(guest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.RESET_CONTENT)
    public void deleteGuest(@PathVariable("id") Long id) {
        this.guestRepository.deleteById(id);
    }
}
