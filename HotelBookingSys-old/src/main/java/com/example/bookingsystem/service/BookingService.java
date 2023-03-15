package com.example.bookingsystem.service;

import com.example.bookingsystem.domain.Booking;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

public interface BookingService {
    ResponseEntity<String> saveBooking(List<Long> roomId, String username, List<Long> guestId, Booking booking, List<String> facilitiesName, List<String> mealName);

    Booking getBookingById(Long bookingId);

    List<Booking> getAllBookings();
    ResponseEntity<String> checkRoomAvailability(Long id, LocalDate checkInDate, LocalDate checkOutDate);
    List<Booking> getBookingsByDate(LocalDate checkInDate);
    List<Booking> getBookingsBetweenDate(LocalDate checkInDate, LocalDate checkOutDate);
    List<Booking> getBookingsMadeByUsername(String username);
    List<Booking> getAllActiveBookings();

    ResponseEntity<String> deleteBookingById(Long bookingId);
}
