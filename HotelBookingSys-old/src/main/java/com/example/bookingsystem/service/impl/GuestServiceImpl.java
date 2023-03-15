package com.example.bookingsystem.service.impl;

import com.example.bookingsystem.domain.Guest;
import com.example.bookingsystem.repository.GuestRepository;
import com.example.bookingsystem.service.GuestService;
import com.example.bookingsystem.validation.GuestValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class GuestServiceImpl implements GuestService {
    @Autowired
    private GuestRepository guestRepository;

    @Override
    public ResponseEntity<String> saveGuest(Guest guest) {
        log.debug("Guest: " + guest);

        if (GuestValidation.checkGuest(guest)) {
            log.error("Guest details incorrect");
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Guest details incorrect");
        }

        log.info("Saving guest: " + guest);
        guestRepository.save(guest);
        log.info("Guest saved in db");
        return ResponseEntity.ok().body("Guest saved.");
    }

    @Override
    public List<Guest> getAllGuests() {
        log.info("fetching all guests");
        List<Guest> allGuests = guestRepository.findAll();

        log.debug("All guests: " + allGuests);

        if (allGuests.isEmpty()) {
            log.error("no guest found in db");
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "no guest found in db");
        }

        log.info("All guests fetched");
        return allGuests;
    }

    @Override
    public Guest getGuestById(Long id) {
        log.debug("Function getGuestById - id: " + id);

        if (id < 0) {
            log.error("id can't be less than 0");
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "id can't be less than 0");
        }

        log.info("fetching guest by id: " + id);
        Guest guest = guestRepository.getById(id);

        log.debug("Guest: " + guest);

        if (guest == null || GuestValidation.checkGuest(guest)) {
            log.error("Guest details incorrect");
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Guest details incorrect");
        }

        return guest;
    }

    @Override
    public Guest updateGuest(Long id, Guest guest) {
        log.debug("Function updateGuest - id: " + id + " | guest: " + guest);

        if (id < 0 || GuestValidation.checkGuest(guest)) {
            log.error("id or guest details can't be null or empty");
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "id or guest details can't be null or empty");
        }

        log.info("updating guest with id: " + id);
        Guest guestById = getGuestById(id);

        log.debug("Guest found: " + guestById);

        if (GuestValidation.checkGuest(guestById)) {
            log.error("guest details incorrect");
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "guest details incorrect");
        }

        log.debug("Guest Age: " + guest.getAge());
        guestById.setAge(guest.getAge());

        log.debug("Guest Firstname: " + guest.getFirstName());
        guestById.setFirstName(guest.getFirstName());

        log.debug("Guest Lastname: " + guest.getLastName());
        guestById.setFirstName(guest.getLastName());

        log.debug("Guest: " + guestById);
        return guestRepository.save(guestById);
    }

    @Override
    public ResponseEntity<String> deleteGuestById(Long id) {
        log.debug("Function deleteGuestById - id: " + id);

        if (id < 0) {
            log.error("id can't be null or empty");
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "id can't be null or empty");
        }

        log.info("deleting guest with id: " + id);
        guestRepository.deleteById(id);
        return ResponseEntity.ok().body("guest with guest id: " + id + " deleted successfully");
    }

}
