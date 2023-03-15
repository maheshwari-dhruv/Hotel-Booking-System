package com.example.bookingsystem.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Booking implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private LocalDate checkInDate;

    @Column(nullable = false)
    private LocalDate checkOutDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "catalog_id", referencedColumnName = "id")
    @ToString.Exclude
    private Catalog catalog;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_id", referencedColumnName = "id")
    private Payment payment;

    @Column(nullable = false)
    private Boolean isPaymentMade = false;

    @ManyToMany
    @JoinTable(
            name = "booked_rooms",
            joinColumns = @JoinColumn(name = "booking_id"),
            inverseJoinColumns = @JoinColumn(name = "room_id")
    )
    @ToString.Exclude
    private List<Room> bookedRooms = new ArrayList<Room>();

    @ManyToMany
    @JoinTable(
            name = "booked_rooms_guest",
            joinColumns = @JoinColumn(name = "booking_id"),
            inverseJoinColumns = @JoinColumn(name = "guest_id")
    )
    @ToString.Exclude
    private List<Guest> bookedRoomsGuest = new ArrayList<Guest>();
}