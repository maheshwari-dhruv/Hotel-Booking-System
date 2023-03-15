package com.example.bookingsystem.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String paymentMode;

    @Column(nullable = false)
    private LocalDate paymentDate;

    @Column(nullable = false)
    private Double totalPaymentAmount;

    @JsonIgnore
    @OneToOne(mappedBy = "payment")
    @ToString.Exclude
    private Booking booking;
}
