package com.example.bookingsystem.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import com.example.bookingsystem.domain.Booking;
import com.example.bookingsystem.domain.Catalog;
import com.example.bookingsystem.domain.Payment;
import com.example.bookingsystem.domain.Room;
import com.example.bookingsystem.domain.User;
import com.example.bookingsystem.repository.BookingRepository;
import com.example.bookingsystem.repository.RoomRepository;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
class BookingServiceImplTest {
    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private BookingServiceImpl bookingServiceImpl;

    @Mock
    private RoomRepository roomRepository;

    @Test
    void testSaveBooking_enteredDetailsAreEitherNullOrEmpty() {
        Booking booking = new Booking();
        booking.setBookedRooms(new ArrayList<>());
        booking.setBookedRoomsGuest(new ArrayList<>());
        booking.setCatalog(new Catalog());
        booking.setCheckInDate(LocalDate.parse("2022-03-04"));
        booking.setCheckOutDate(LocalDate.parse("2022-03-04"));
        booking.setId(123L);
        booking.setIsPaymentMade(true);
        booking.setPayment(new Payment());
        booking.setUser(new User());

        Exception ex = assertThrows(ResponseStatusException.class, () -> bookingServiceImpl.saveBooking(new ArrayList<>(), "dhruvM", new ArrayList<>(), booking, new ArrayList<>(), new ArrayList<>()));
        String expectedMessage = "entered details are either null or empty";
        String actualMessage = ex.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testGetAllBookings_noBookingsFound() {
        when(bookingRepository.findAll()).thenReturn(new ArrayList<>());

        Exception ex = assertThrows(ResponseStatusException.class, () -> bookingServiceImpl.getAllBookings());
        String expectedMessage = "no bookings found";
        String actualMessage = ex.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(bookingRepository).findAll();
    }

    @Test
    void testCheckRoomAvailability_roomAvailableForBookingOnDesiredDates() {
        Room room = new Room();
        room.setAdults(1);
        room.setBookings(new ArrayList<>());
        room.setChildren(1);
        room.setCost(10.0d);
        room.setId(123L);
        room.setIsReserved(true);
        room.setType("Balcony");
        when(roomRepository.getById(any())).thenReturn(room);

        ResponseEntity<String> actualCheckRoomAvailabilityResult = bookingServiceImpl.checkRoomAvailability(123L, LocalDate.parse("2022-03-04"), LocalDate.parse("2022-03-04"));
        assertEquals("room is available for booking", actualCheckRoomAvailabilityResult.getBody());
        assertEquals(HttpStatus.OK, actualCheckRoomAvailabilityResult.getStatusCode());
        verify(roomRepository).getById(any());
    }

    @Test
    void testCheckRoomAvailability_nextBookingDatesSuggestion() {
        Booking booking = new Booking();
        booking.setBookedRooms(new ArrayList<>());
        booking.setBookedRoomsGuest(new ArrayList<>());
        booking.setCatalog(new Catalog());
        booking.setCheckInDate(LocalDate.ofEpochDay(1L));
        booking.setCheckOutDate(LocalDate.ofEpochDay(1L));
        booking.setId(123L);
        booking.setIsPaymentMade(true);
        booking.setPayment(new Payment());
        booking.setUser(new User());

        ArrayList<Booking> bookingList = new ArrayList<>();
        bookingList.add(booking);

        Room room = new Room();
        room.setAdults(1);
        room.setBookings(bookingList);
        room.setChildren(1);
        room.setCost(10.0d);
        room.setId(123L);
        room.setIsReserved(true);
        room.setType("Type");
        when(roomRepository.getById(any())).thenReturn(room);

        ResponseEntity<String> actualCheckRoomAvailabilityResult = bookingServiceImpl.checkRoomAvailability(123L, LocalDate.ofEpochDay(1L), LocalDate.ofEpochDay(1L));
        assertEquals("Room available for booking between 1970-01-03 & 1970-01-03", actualCheckRoomAvailabilityResult.getBody());
        assertEquals(HttpStatus.OK, actualCheckRoomAvailabilityResult.getStatusCode());
        verify(roomRepository).getById(any());
    }

    @Test
    void testGetBookingsByDate_noBookingsFoundOnThisDate() {
        when(bookingRepository.findAll()).thenReturn(new ArrayList<>());

        Exception ex = assertThrows(ResponseStatusException.class, () -> bookingServiceImpl.getBookingsByDate(LocalDate.ofEpochDay(1L)));
        String expectedMessage = "No bookings found on this date";
        String actualMessage = ex.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(bookingRepository).findAll();
    }

    @Test
    void testGetBookingsBetweenDate_noBookingsFoundBetweenTheseDate() {
        when(this.bookingRepository.findAll()).thenReturn(new ArrayList<>());

        Exception ex = assertThrows(ResponseStatusException.class, () -> bookingServiceImpl.getBookingsBetweenDate(LocalDate.ofEpochDay(1L), LocalDate.ofEpochDay(1L)));
        String expectedMessage = "No bookings found between dates: 1970-01-02 & 1970-01-02";
        String actualMessage = ex.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(bookingRepository).findAll();
    }

    @Test
    void testGetBookingsMadeByUsername() {
        when(this.bookingRepository.findAll()).thenReturn(new ArrayList<>());
        Exception ex = assertThrows(ResponseStatusException.class, () -> bookingServiceImpl.getBookingsMadeByUsername("dhruvM"));
        String expectedMessage = "No bookings made by username: dhruvM";
        String actualMessage = ex.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(bookingRepository).findAll();
    }

    @Test
    void testGetAllActiveBookings() {
        when(this.bookingRepository.findAll()).thenReturn(new ArrayList<>());
        Exception ex = assertThrows(ResponseStatusException.class, () -> bookingServiceImpl.getAllActiveBookings());
        String expectedMessage = "No active bookings found";
        String actualMessage = ex.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(bookingRepository).findAll();
    }

    @Test
    void testDeleteBookingById() {
        Booking booking = new Booking();
        booking.setBookedRooms(new ArrayList<>());
        booking.setBookedRoomsGuest(new ArrayList<>());
        booking.setCatalog(new Catalog());
        booking.setCheckInDate(LocalDate.ofEpochDay(1));
        booking.setCheckOutDate(LocalDate.ofEpochDay(1));
        booking.setId(123L);
        booking.setIsPaymentMade(true);
        booking.setPayment(new Payment());
        booking.setUser(new User());

        when(bookingRepository.getById(any())).thenReturn(booking);
        assertThrows(ResponseStatusException.class, () -> this.bookingServiceImpl.deleteBookingById(123L));
        verify(bookingRepository).getById(any());
    }
}