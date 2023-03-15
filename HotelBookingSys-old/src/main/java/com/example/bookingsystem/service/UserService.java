package com.example.bookingsystem.service;

import com.example.bookingsystem.domain.User;
import com.example.bookingsystem.domain.jwt.JwtAuthRequest;
import com.example.bookingsystem.domain.jwt.JwtAuthResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    ResponseEntity<String> saveUser(User user, String roleName);
    void addRoleToUser(String username, String roleName);
    User getUser(String username);
    User updateUser(String username, User user);
    ResponseEntity<JwtAuthResponse> signInUser(JwtAuthRequest jwtAuthRequest);
    List<User> getUsers();
    ResponseEntity<String> deleteUser(String username);
}
