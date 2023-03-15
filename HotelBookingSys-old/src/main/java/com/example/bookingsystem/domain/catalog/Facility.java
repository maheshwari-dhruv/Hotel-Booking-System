package com.example.bookingsystem.domain.catalog;

import com.example.bookingsystem.domain.Catalog;
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
public class Facility {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private double price;

    @JsonIgnore
    @ManyToMany(mappedBy = "catalogFacilities")
    @ToString.Exclude
    private List<Catalog> catalogs = new ArrayList<Catalog>();
}
