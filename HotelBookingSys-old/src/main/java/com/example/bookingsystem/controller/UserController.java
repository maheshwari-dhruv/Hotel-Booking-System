package com.example.bookingsystem.controller;

import com.example.bookingsystem.domain.User;
import com.example.bookingsystem.domain.jwt.JwtAuthRequest;
import com.example.bookingsystem.domain.jwt.JwtAuthResponse;
import com.example.bookingsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v2/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register/staff")
    public ResponseEntity<String> saveAdminUser(@RequestBody User user) {
        return userService.saveUser(user, "staff");
    }

    @PostMapping("/register")
    public ResponseEntity<String> saveUser(@RequestBody User user) {
        return userService.saveUser(user, "customer");
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> loginUser(@RequestBody JwtAuthRequest jwtAuthRequest) {
        return userService.signInUser(jwtAuthRequest);
    }

    @PreAuthorize("hasAuthority('staff')")
    @GetMapping("/view/all")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @PreAuthorize("hasAnyAuthority('staff', 'customer')")
    @GetMapping("/view/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok().body(userService.getUser(username));
    }

    @PreAuthorize("hasAnyAuthority('staff', 'customer')")
    @PutMapping("/update/{username}")
    public ResponseEntity<User> updateUserByUsername(@PathVariable String username, @RequestBody User user) {
        return ResponseEntity.ok().body(userService.updateUser(username, user));
    }

    @PreAuthorize("hasAnyAuthority('staff', 'customer')")
    @DeleteMapping("/delete/{username}")
    public ResponseEntity<String> deleteUserByUsername(@PathVariable String username) {
        return userService.deleteUser(username);
    }
}
