package com.example.bookingsystem.service;

import com.example.bookingsystem.domain.Guest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface GuestService {
    ResponseEntity<String> saveGuest(Guest guest);
    List<Guest> getAllGuests();
    Guest getGuestById(Long id);
    Guest updateGuest(Long id, Guest guest);
    ResponseEntity<String> deleteGuestById(Long id);
}
