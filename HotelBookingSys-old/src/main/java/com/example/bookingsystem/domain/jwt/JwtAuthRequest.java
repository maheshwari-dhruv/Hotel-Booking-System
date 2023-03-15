package com.example.bookingsystem.domain.jwt;

import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class JwtAuthRequest implements Serializable {
    private String username;
    private String password;
}
