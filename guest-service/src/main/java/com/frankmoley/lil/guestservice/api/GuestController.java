package com.frankmoley.lil.guestservice.api;

import com.frankmoley.lil.guestservice.data.Guest;
import com.frankmoley.lil.guestservice.data.GuestRepository;
import com.frankmoley.lil.guestservice.error.BadReqeustException;
import com.frankmoley.lil.guestservice.error.NotFoundException;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.core.env.Environment;
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
    private final Environment environment;
    private final LoadBalancerClientFactory loadBalancerClientFactory;

    public GuestController(GuestRepository guestRepository, Environment environment, LoadBalancerClientFactory loadBalancerClientFactory) {
        this.guestRepository = guestRepository;
        this.environment = environment;
        this.loadBalancerClientFactory = loadBalancerClientFactory;
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
        String instanceId = environment.getProperty("eureka.instance.instance-id");
        String port = environment.getProperty("local.server.port");
        System.out.println("Instance ID: " + instanceId + " | Port: " + port);
        ReactorServiceInstanceLoadBalancer lb =
                loadBalancerClientFactory.getInstance(instanceId, ReactorServiceInstanceLoadBalancer.class);
        System.out.println("Service " + instanceId + " is using " + lb.getClass().getSimpleName());
        Optional<Guest> guest = this.guestRepository.findById(id);
        if (guest.isEmpty()) {
            throw new NotFoundException("id not found: " + id);
        }
        return guest.get();
    }

    @GetMapping("/email/{email}")
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
