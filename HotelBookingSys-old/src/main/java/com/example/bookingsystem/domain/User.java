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
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String firstName;
    private String lastName;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String phone;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles = new ArrayList<Role>();

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private List<Booking> bookings = new ArrayList<Booking>();
}
