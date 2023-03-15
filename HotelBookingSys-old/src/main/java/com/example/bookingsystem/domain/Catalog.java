package com.example.bookingsystem.domain;

import com.example.bookingsystem.domain.catalog.Facility;
import com.example.bookingsystem.domain.catalog.Meal;
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
public class Catalog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "catalog_facilities",
            joinColumns = @JoinColumn(name = "catalog_id"),
            inverseJoinColumns = @JoinColumn(name = "facility_id")
    )
    @ToString.Exclude
    private List<Facility> catalogFacilities = new ArrayList<Facility>();

    @ManyToMany
    @JoinTable(
            name = "catalog_meals",
            joinColumns = @JoinColumn(name = "catalog_id"),
            inverseJoinColumns = @JoinColumn(name = "meal_id")
    )
    @ToString.Exclude
    private List<Meal> catalogMeals = new ArrayList<Meal>();

    @JsonIgnore
    @OneToOne(mappedBy = "catalog")
    private Booking booking;
}
