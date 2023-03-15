package com.example.bookingsystem.controller;

import com.example.bookingsystem.domain.Booking;
import com.example.bookingsystem.service.BookingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v2/booking")
@Slf4j
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @PreAuthorize("hasAnyAuthority('staff', 'customer')")
    @PostMapping("/create/rooms={roomId}/user={username}/guests={guestId}/facilities={facilities}/meals={meals}")
    public ResponseEntity<String> createBooking(@PathVariable List<Long> roomId, @PathVariable String username, @PathVariable List<Long> guestId, @PathVariable List<String> facilities, @PathVariable List<String> meals, @RequestBody Booking booking) {
        return bookingService.saveBooking(roomId, username, guestId, booking, facilities, meals);
    }

    @PreAuthorize("hasAuthority('staff')")
    @GetMapping("/view/all")
    public ResponseEntity<List<Booking>> getAllBookings() {
        return ResponseEntity.ok().body(bookingService.getAllBookings());
    }

    @PreAuthorize("hasAnyAuthority('staff', 'customer')")
    @GetMapping("/view/{bookingId}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long bookingId) {
        return ResponseEntity.ok().body(bookingService.getBookingById(bookingId));
    }

    @PreAuthorize("hasAnyAuthority('staff', 'customer')")
    @GetMapping("/check/room={roomId}/availability/dates={checkInDate}&{checkOutDate}")
    public ResponseEntity<String> checkRoomAvailabilityBetweenDates(@PathVariable Long roomId, @PathVariable String checkInDate, @PathVariable String checkOutDate) {
        LocalDate inDate = LocalDate.parse(checkInDate);
        LocalDate outDate = LocalDate.parse(checkOutDate);
        return bookingService.checkRoomAvailability(roomId, inDate, outDate);
    }

    @PreAuthorize("hasAuthority('staff')")
    @GetMapping("/view/all/date={checkInDate}")
    public ResponseEntity<List<Booking>> getBookingsByDate(@PathVariable LocalDate checkInDate) {
        return ResponseEntity.ok().body(bookingService.getBookingsByDate(checkInDate));
    }

    @PreAuthorize("hasAuthority('staff')")
    @GetMapping("/view/all/dates={checkInDate}&{checkOutDate}")
    public ResponseEntity<List<Booking>> getBookingsBetweenDates(@PathVariable LocalDate checkInDate, @PathVariable LocalDate checkOutDate) {
        return ResponseEntity.ok().body(bookingService.getBookingsBetweenDate(checkInDate, checkOutDate));
    }

    @PreAuthorize("hasAnyAuthority('staff', 'customer')")
    @GetMapping("/view/all/{username}")
    public ResponseEntity<List<Booking>> getBookingByUsername(@PathVariable String username) {
        return ResponseEntity.ok().body(bookingService.getBookingsMadeByUsername(username));
    }

    @PreAuthorize("hasAuthority('staff')")
    @GetMapping("/view/all/active")
    public ResponseEntity<List<Booking>> getAllActiveBooking() {
        return ResponseEntity.ok().body(bookingService.getAllActiveBookings());
    }

    @PreAuthorize("hasAnyAuthority('staff', 'customer')")
    @DeleteMapping("/delete/{bookingId}")
    public ResponseEntity<String> deleteBooking(@PathVariable Long bookingId) {
        return bookingService.deleteBookingById(bookingId);
    }
}
