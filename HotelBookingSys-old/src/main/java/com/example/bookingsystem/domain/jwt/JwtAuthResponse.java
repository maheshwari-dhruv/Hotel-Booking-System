package com.example.bookingsystem.domain.jwt;

import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class JwtAuthResponse implements Serializable {
    private String jwtToken;
}
