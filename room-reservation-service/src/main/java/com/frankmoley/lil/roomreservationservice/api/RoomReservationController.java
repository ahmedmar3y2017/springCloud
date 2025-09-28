package com.frankmoley.lil.roomreservationservice.api;

import com.frankmoley.lil.roomreservationservice.client.guest.Guest;
import com.frankmoley.lil.roomreservationservice.client.guest.GuestServiceClient;
import com.frankmoley.lil.roomreservationservice.client.reservation.Reservation;
import com.frankmoley.lil.roomreservationservice.client.reservation.ReservationServiceClient;
import com.frankmoley.lil.roomreservationservice.client.room.Room;
import com.frankmoley.lil.roomreservationservice.client.room.RoomServiceClient;
import com.frankmoley.lil.roomreservationservice.dto.RoomReservationDto;
import com.frankmoley.lil.roomreservationservice.dto.RoomReservationEvent;
import com.frankmoley.lil.roomreservationservice.error.BadReqeustException;
import com.frankmoley.lil.roomreservationservice.error.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("roomReservations")
@RefreshScope
public class RoomReservationController {

    private final GuestServiceClient guestServiceClient;
    private final ReservationServiceClient reservationServiceClient;
    private final RoomServiceClient roomServiceClient;
//    private final RestTemplate restTemplate;
    private final Environment environment;
    private final EurekaDiscoveryClient discoveryClient;
    private final LoadBalancerClientFactory loadBalancerClientFactory;



    @Value("${refresh.test}")
    String test;

    @Value("${spring.application.name}")
    String applicationName;

    public RoomReservationController(GuestServiceClient guestServiceClient, ReservationServiceClient reservationServiceClient, RoomServiceClient roomServiceClient, Environment environment, EurekaDiscoveryClient discoveryClient, LoadBalancerClientFactory loadBalancerClientFactory) {
        this.guestServiceClient = guestServiceClient;
        this.reservationServiceClient = reservationServiceClient;
        this.roomServiceClient = roomServiceClient;
//        this.restTemplate = restTemplate;
        this.environment = environment;
        this.discoveryClient = discoveryClient;
        this.loadBalancerClientFactory = loadBalancerClientFactory;
    }

    @GetMapping
    public Collection<RoomReservation> getRoomReservations(@RequestParam(required = false) String dateString
    ) {
        String instanceId = environment.getProperty("eureka.instance.instance-id");
        String port = environment.getProperty("local.server.port");
        System.out.println("Instance ID: " + instanceId + " | Port: " + port);
//        ReactorServiceInstanceLoadBalancer lb =
//                loadBalancerClientFactory.getInstance(instanceId, ReactorServiceInstanceLoadBalancer.class);
//        System.out.println("Service " + instanceId + " is using " + lb.getClass().getSimpleName());

//        System.out.println("List of instances : " + applicationName);
//        List<ServiceInstance> instances = discoveryClient.getInstances(applicationName);
//        instances.stream().forEach(serviceInstance -> {
//
//            System.out.println("Instance ID: " + serviceInstance.getInstanceId() + " | Port: " + serviceInstance.getPort());
//        } );

        if (!StringUtils.hasLength(dateString)) {
            Date date = new Date(System.currentTimeMillis());
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateString = dateFormat.format(date);
        }
        final String usableDateString = dateString;
        //get all rooms first - openFien lib
        List<Room> rooms = this.roomServiceClient.getAll();
//        ResponseEntity<List<Room>> response = restTemplate.exchange(
//                "http://room-service/rooms",
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<List<Room>>() {
//                }
//        );
//        List<Room> rooms = response.getBody();

        //now build a room reservation for each one
        Map<Long, RoomReservation> roomReservations = new HashMap<>(rooms.size());
        rooms.forEach(room -> {
            RoomReservation roomReservation = new RoomReservation();
            roomReservation.setRoomId(room.getRoomId());
            roomReservation.setRoomNumber(room.getRoomNumber());
            roomReservation.setBedInfo(room.getBedInfo());
            roomReservation.setName(room.getName());
            roomReservation.setDate(usableDateString);
            roomReservations.put(room.getRoomId(), roomReservation);
        });
        // - openFien lib
        List<Reservation> reservations = this.reservationServiceClient.getAll(null, usableDateString);

//        ResponseEntity<List<Reservation>> responseReservations = restTemplate.exchange(
//                "http://reservation-service/reservations",
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<List<Reservation>>() {
//                }
//        );
//        List<Reservation> reservations = responseReservations.getBody();

        reservations.forEach(reservation -> {
            RoomReservation roomReservation = roomReservations.get(reservation.getRoomId());
            roomReservation.setReservationId(reservation.getReservationId());
            roomReservation.setGuestId(reservation.getGuestId());
            // - openFien lib
            Guest guest = this.guestServiceClient.getGuest(roomReservation.getGuestId());

//            Guest guest = restTemplate.getForObject("http://guest-service/guests/" + roomReservation.getGuestId(), Guest.class);

            roomReservation.setFirstName(guest.getFirstName());
            roomReservation.setLastName(guest.getLastName());
        });
        return roomReservations.values();
    }

    // add new reservation
    // {
    // create reservation Api
    // 1 - check guest exists or Not
    // 2 - if not exists : return error
    // room not available , update reservation failed
    // room available , update reservation success -> update DB -> send message reservation_Created
    // create reservation_reversed -> DB update reservation status failed
    // 5 - listen to message reservation_Created
    // payment - > create payment -> update status : success
    // if failed -> update status -> send message to payment_reversed
    // create payment_reversed consumer for set status failed
    // }


    @PostMapping
    public void addRoomReservation(
            @RequestHeader("UUID_") String UUID,
            @RequestBody RoomReservationDto roomReservationDto
    ) {
        RoomReservationEvent roomReservationEvent = new RoomReservationEvent();
        /*
         * Reservation Dto
         *
         *
         * */

        // status { succes , failed }
        // payment table -> ID , reservationID , Mode , amount , status
        // handle get guest fallback in guest service
        // handle add guest fallback in guest service
        // handle checkRoomAvailability fallback in room service
        // handle flyway migration for other services except (guest , payment)

        // checek guest exists or Not
        String emailAddress = roomReservationDto.getEmail_address();
        Guest guestByEmail = guestServiceClient.getGuestByEmail(emailAddress);
        if (guestByEmail == null) {
            Guest guest = new Guest();
            guest.setEmailAddress(roomReservationDto.getEmail_address());
            guest.setState("EG");
            guest.setFirstName(roomReservationDto.getFirstName());
            guest.setLastName(roomReservationDto.getLastName());
            guest.setPhoneNumber(roomReservationDto.getPhone());
            // create new Guest
            guestByEmail = guestServiceClient.addGuest(guest);
            roomReservationDto.setGuestId(guestByEmail.getGuestId());
        }
        // check room available in that date or Not
        Room room = roomServiceClient.getRoom(roomReservationDto.getRoomNumber());
        Boolean roomAvailable = true;
        if (room == null) {

            // room Not Found
            throw new NotFoundException("Room Not Found ");

        } else {
            roomReservationDto.setCost(room.getCost());
            roomReservationDto.setMode("Paypal");
            // check day availabe for reserve it in thts room
            ResponseEntity<Boolean> booleanResponseEntity = reservationServiceClient.checkRoomAvailability(roomReservationDto.getRoomNumber(), roomReservationDto.getDate());
            roomAvailable = booleanResponseEntity.getBody();

        }
        if (roomAvailable) {

            // room available , update reservation success -> update DB -> send message reservation_Created
            Reservation reservation = new Reservation();
            reservation.setGuestId(guestByEmail.getGuestId());
            reservation.setRoomId(room.getRoomId());
            reservation.setDate(roomReservationDto.getDate());
            Reservation serviceClientReservation = reservationServiceClient.createReservation(reservation);

            roomReservationDto.setReservationId(serviceClientReservation.getReservationId());

            // send message created on kafka topic :
            roomReservationEvent.setType("room-reservation-created");
            roomReservationEvent.setRoomReservationDto(roomReservationDto);
//            roomReservationEventKafkaTemplate.send("room-reservation-created", roomReservationEvent);
//        } else {
            // throw room not available
            throw new BadReqeustException("Room Not Available For reservation ");

        }


    }


    @GetMapping("/test/refresh")
    public String getTest() {

        return test;
    }


}
