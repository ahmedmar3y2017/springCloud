package com.frankmoley.lil.roomservice.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

@Entity
@Table(name = "ROOMS")
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ROOM_ID")
    private long roomId;
    @Column(name = "NAME")
    private String Name;
    @Column(name = "ROOM_NUMBER")
    private String roomNumber;
    @Column(name = "BED_INFO")
    private String bedInfo;
    @Column(name = "COST")
    private double cost;

}
