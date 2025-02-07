package com.demo.paymentservice.payment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "PAYMENT")
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PAYMENT_ID")
    private long paymentId;

    @Column(name = "RESERVATION_ID")
    private long reservationId;

    @Column(name = "MODE")
    private String Mode;

    @Column(name = "AMOUNT")
    private double amount;

    @Column(name = "STATUS")
    private String status;

}
