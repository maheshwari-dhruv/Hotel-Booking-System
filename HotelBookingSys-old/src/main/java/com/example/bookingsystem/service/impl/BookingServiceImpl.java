package com.example.bookingsystem.service.impl;

import com.example.bookingsystem.domain.Booking;
import com.example.bookingsystem.domain.Guest;
import com.example.bookingsystem.domain.Room;
import com.example.bookingsystem.domain.User;
import com.example.bookingsystem.repository.BookingRepository;
import com.example.bookingsystem.repository.RoomRepository;
import com.example.bookingsystem.service.*;
import com.example.bookingsystem.validation.BookingValidation;
import com.example.bookingsystem.validation.GuestValidation;
import com.example.bookingsystem.validation.RoomValidation;
import com.example.bookingsystem.validation.StringValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;

@Service
@Transactional
@Slf4j
public class BookingServiceImpl implements BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoomService roomService;

    @Autowired
    private UserService userService;

    @Autowired
    private GuestService guestService;

    @Autowired
    private CatalogService catalogService;

    @Override
    public ResponseEntity<String> saveBooking(List<Long> roomId, String username, List<Long> guestId, Booking booking, List<String> facilitiesName, List<String> mealName) {
        log.debug("Function saveBooking");
        log.debug("Room ids: " + roomId);
        log.debug("User username: " + username);
        log.debug("Guest Ids: " + guestId);
        log.debug("Booking: " + booking);
        log.debug("Facilities name: " + facilitiesName);
        log.debug("Meal plans: " + mealName);

        if (mealName.isEmpty() || facilitiesName.isEmpty() || roomId.isEmpty() || StringValidation.checkNullEmptyString(username) || guestId.isEmpty() || BookingValidation.checkBookingWhenSaving(booking)) {
            log.error("entered details are either null or empty");
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "entered details are either null or empty");
        }

        log.info("Saving booking: " + booking);

        log.debug("User: " + addUser(username));
        booking.setUser(addUser(username));

        log.debug("Rooms: " + addRooms(roomId));
        booking.setBookedRooms(addRooms(roomId));

        log.debug("Guests: " + addGuests(guestId));
        booking.setBookedRoomsGuest(addGuests(guestId));

        log.debug("Catalog: " + catalogService.saveCatalog(facilitiesName, mealName));
        booking.setCatalog(catalogService.saveCatalog(facilitiesName, mealName));

        log.debug("Booking: " + booking);
        log.info("Booking saved in db");
        bookingRepository.save(booking);
        return ResponseEntity.ok().body("Booking saved");
    }

    private User addUser(String username) {
        log.debug("Username: " + username);
        return userService.getUser(username);
    }

    private List<Room> addRooms(List<Long> roomId) {
        log.debug("Room id: " + roomId);
        List<Room> rooms = new ArrayList<Room>();

        roomId.forEach(id -> {
            Room roomById = roomService.getRoomById(id);
            roomById.setIsReserved(true);

            log.debug("Room: " + roomById);
            if (RoomValidation.checkRoom(roomById)) {
                log.error("Error in room details");
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Error in room details");
            }

            rooms.add(roomById);
        });

        log.debug("All rooms: " + rooms);
        return rooms;
    }

    private List<Guest> addGuests(List<Long> guestId) {
        log.debug("Guest Ids: " + guestId);
        List<Guest> guests = new ArrayList<Guest>();

        guestId.forEach(id -> {
            Guest guestById = guestService.getGuestById(id);
            log.debug("Guest: " + guestById);

            if (GuestValidation.checkGuest(guestById)) {
                log.error("Error in guest details");
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Error in guest details");
            }

            guests.add(guestById);
        });

        log.debug("All Guests: " + guests);
        return guests;
    }

    @Override
    public List<Booking> getAllBookings() {
        log.info("fetching all bookings");
        List<Booking> allBookings = bookingRepository.findAll();

        log.debug("All bookings: " + allBookings);

        if (allBookings.isEmpty()) {
            log.error("no bookings found");
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "no bookings found");
        }

        log.info("All bookings fetched");
        return allBookings;
    }

    @Override
    public Booking getBookingById(Long bookingId) {
        log.debug("Booking id: " + bookingId);

        if (bookingId < 0) {
            log.error("Booking Id can't be null or less than 0");
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Booking Id can't be null or less than 0");
        }

        log.info("fetching booking with id: " + bookingId);
        Booking booking = bookingRepository.getById(bookingId);

        log.debug("Booking found: " + booking);

        if (BookingValidation.checkBooking(booking)) {
            log.error("booking details incorrect");
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "booking details incorrect");
        }

        return booking;
    }

    @Override
    public ResponseEntity<String> checkRoomAvailability(Long id, LocalDate checkInDate, LocalDate checkOutDate) {
        log.debug("Function checkRoomAvailability");
        log.debug("Id: " + id);
        log.debug("Check In Date: " + checkInDate);
        log.debug("Check Out Date: " + checkOutDate);

        Room room = roomRepository.getById(id);
        log.debug("Room found: " + room);

        if (room.getBookings().isEmpty()) {
            log.info("room is available for booking");
            return ResponseEntity.ok().body("room is available for booking");
        } else {
            return ResponseEntity.ok().body(availabilityCheck(room, checkInDate, checkOutDate));
        }
    }

    private String availabilityCheck(Room room, LocalDate checkInDate, LocalDate checkOutDate) {
        log.debug("Function availabilityCheck");
        log.debug("Room : " + room);
        log.debug("Check In Date: " + checkInDate);
        log.debug("Check Out Date: " + checkOutDate);

        int numDays = Period.between(checkInDate, checkOutDate).getDays();
        log.debug("Number of days: " + numDays);

        Map<LocalDate, LocalDate> bookingDates = new TreeMap<>();

        for (Booking booking: room.getBookings()) {
            bookingDates.put(booking.getCheckInDate(), booking.getCheckOutDate());
        }

        log.debug("Booking dates: " + bookingDates);

        for (Map.Entry<LocalDate, LocalDate> date: bookingDates.entrySet()) {
            if ((checkInDate.compareTo(date.getKey()) >= 0 || checkInDate.compareTo(date.getValue()) >= 0) || (checkOutDate.compareTo(date.getKey()) >= 0 || checkOutDate.compareTo(date.getValue()) >= 0)) {
                checkInDate = date.getValue().plusDays(1);
                checkOutDate = checkInDate.plusDays(numDays);
                String hello = "hello" + 1 + 2; // hello12
            }
        }

        log.info("Room available for booking between " + checkInDate + " & " + checkOutDate);
        return "Room available for booking between " + checkInDate + " & " + checkOutDate;
    }

    @Override
    public List<Booking> getBookingsByDate(LocalDate checkInDate) {
        log.debug("Function getBookingsByDate - check in date: " + checkInDate);

        List<Booking> bookings = bookingRepository.findAll()
                .stream()
                .filter(booking -> booking.getCheckInDate().compareTo(checkInDate) == 0)
                .toList();

        List<Integer> num = new ArrayList<>();
        num.stream().map(integer -> integer+2).toList();

        log.debug("Bookings found: " + bookings);

        if (bookings.isEmpty()) {
            log.info("No bookings found on this date: " + checkInDate);
            throw new ResponseStatusException(HttpStatus.OK, "No bookings found on this date");
        }

        return bookings;
    }

    @Override
    public List<Booking> getBookingsBetweenDate(LocalDate checkInDate, LocalDate checkOutDate) {
        log.debug("Function getBookingsBetweenDate - Check in date: " + checkInDate + " | check out date: " + checkOutDate);

        List<Booking> bookingsBetweenDates = bookingRepository.findAll()
                .stream()
                .filter(booking -> (booking.getCheckInDate().isEqual(checkInDate) || booking.getCheckInDate().isAfter(checkInDate)) && (booking.getCheckOutDate().isEqual(checkOutDate) || booking.getCheckOutDate().isBefore(checkOutDate)))
                .toList();

        log.debug("Bookings found: " + bookingsBetweenDates);

        if (bookingsBetweenDates.isEmpty()) {
            log.info("No bookings found between dates: " + checkInDate + " & " + checkOutDate);
            throw new ResponseStatusException(HttpStatus.OK, "No bookings found between dates: " + checkInDate + " & " + checkOutDate);
        }

        return bookingsBetweenDates;
    }

    @Override
    public List<Booking> getBookingsMadeByUsername(String username) {
        log.debug("Function getBookingsMadeByUsername: user username: " + username);

        if (StringValidation.checkNullEmptyString(username)) {
            log.error("Username can't be null or empty");
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Username can't be null or empty");
        }

        List<Booking> bookingsByUsername = bookingRepository.findAll()
                .stream()
                .filter(booking -> booking.getUser().getUsername().equalsIgnoreCase(username))
                .toList();

        log.debug("Bookings found: " + bookingsByUsername);

        if (bookingsByUsername.isEmpty()) {
            log.info("No bookings made by username: " + username);
            throw new ResponseStatusException(HttpStatus.OK, "No bookings made by username: " + username);
        }

        return bookingsByUsername;
    }

    @Override
    public List<Booking> getAllActiveBookings() {
        List<Booking> allActiveBookings = bookingRepository.findAll()
                .stream()
                .filter(booking -> booking.getCheckInDate().isEqual(LocalDate.now()) || booking.getCheckInDate().isAfter(LocalDate.now()))
                .toList();

        log.debug("All active bookings: " + allActiveBookings);

        if (allActiveBookings.isEmpty()) {
            log.info("No active bookings found");
            throw new ResponseStatusException(HttpStatus.OK, "No active bookings found");
        }

        return allActiveBookings;
    }

    @Override
    public ResponseEntity<String> deleteBookingById(Long bookingId) {
        log.debug("Function deleteBookingById - booking id: " + bookingId);

        if (bookingId < 0) {
            log.error("incorrect booking id");
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "incorrect booking id");
        }

        log.info("Deleting booking with id: " + bookingId);

        getBookingById(bookingId).getBookedRooms().forEach(room -> room.setIsReserved(false));
        bookingRepository.deleteById(bookingId);
        return ResponseEntity.ok().body("Deleted booking with id: " + bookingId);
    }
}
