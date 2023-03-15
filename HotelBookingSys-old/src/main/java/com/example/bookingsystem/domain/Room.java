package com.example.bookingsystem.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Boolean isReserved = false;

    @Column(nullable = false)
    private double cost;
    private int adults;
    private int children;

    @JsonIgnore
    @ManyToMany(mappedBy = "bookedRooms")
    @ToString.Exclude
    private List<Booking> bookings = new ArrayList<Booking>();

    @Column(nullable = false)
    private String type;
}