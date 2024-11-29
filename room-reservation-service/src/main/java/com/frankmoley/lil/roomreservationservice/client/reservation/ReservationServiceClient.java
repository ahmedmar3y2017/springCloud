package com.frankmoley.lil.roomreservationservice.client.reservation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReservationServiceClient {
    private final RestTemplate restTemplate;

    @Value("${RESERVATION_SERVICE_URL}")
    private String reservationServiceUrl;

    private final static String RESERVATIONS_URL_PART = "/reservations";
    private final static String SLASH = "/";

    public ReservationServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Reservation> getAll(String dateString, Long guestId) {
        UriComponentsBuilder urlTemplate = UriComponentsBuilder.fromHttpUrl(reservationServiceUrl + RESERVATIONS_URL_PART);
        Map<String, String> params = new HashMap<>();
        if (StringUtils.hasLength(dateString)) {
            urlTemplate.queryParam("date", "{date}");
            params.put("date", dateString);
        }
        if (guestId != null) {
            urlTemplate.queryParam("guestId", "{guestId}");
            params.put("guestId", String.valueOf(guestId));
        }
        urlTemplate.encode();

        ResponseEntity<Reservation[]> response = this.restTemplate.getForEntity(urlTemplate.toUriString(), Reservation[].class, params);
        return Arrays.asList(response.getBody());
    }

    public Reservation addReservation(Reservation reservation) {
        String url = reservationServiceUrl + RESERVATIONS_URL_PART;
        ResponseEntity<Reservation> response = this.restTemplate.postForEntity(url, reservation, Reservation.class);
        return response.getBody();
    }

    public Reservation getReservation(long id) {
        String url = reservationServiceUrl + RESERVATIONS_URL_PART + SLASH + id;
        ResponseEntity<Reservation> response = this.restTemplate.getForEntity(url, Reservation.class);
        return response.getBody();
    }

    public void updateReservation(Reservation reservation) {
        String url = reservationServiceUrl + RESERVATIONS_URL_PART + SLASH + reservation.getReservationId();
        this.restTemplate.put(url, reservation);
    }

    public void deleteReservation(long id) {
        String url = reservationServiceUrl + RESERVATIONS_URL_PART + SLASH + id;
        this.restTemplate.delete(url);
    }

}
