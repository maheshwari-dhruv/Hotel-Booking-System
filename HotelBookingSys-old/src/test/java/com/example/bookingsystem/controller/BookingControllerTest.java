package com.example.bookingsystem.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.example.bookingsystem.domain.Booking;
import com.example.bookingsystem.domain.Catalog;
import com.example.bookingsystem.domain.Guest;
import com.example.bookingsystem.domain.Payment;
import com.example.bookingsystem.domain.Room;
import com.example.bookingsystem.domain.User;
import com.example.bookingsystem.service.BookingService;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class BookingControllerTest {
    @InjectMocks
    private BookingController bookingController;

    @Mock
    private BookingService bookingService;

    @Test
    void testCheckRoomAvailabilityBetweenDates() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/api/v2/booking/check/room={roomId}/availability/dates={checkInDate}&{checkOutDate}", 123L,
                LocalDate.ofEpochDay(1L), LocalDate.ofEpochDay(1L));
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.bookingController)
                .build()
                .perform(requestBuilder);

        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    void testGetAllBookings() throws Exception {
        when(this.bookingService.getAllBookings()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v2/booking/view/all");
        MockMvcBuilders.standaloneSetup(this.bookingController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testGetBookingById() throws Exception {
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
        when(bookingService.getBookingById(any())).thenReturn(booking);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v2/booking/view/{bookingId}", 123L);
        MockMvcBuilders.standaloneSetup(this.bookingController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
    }

    @Test
    void testDeleteBooking() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/v2/booking/delete/{bookingId}", 123L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.bookingController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    void testGetAllActiveBooking() throws Exception {
        when(this.bookingService.getAllActiveBookings()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v2/booking/view/all/active");
        MockMvcBuilders.standaloneSetup(this.bookingController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testGetBookingByUsername() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v2/booking/view/all/{username}",
                "dhruvM");
        MockMvcBuilders.standaloneSetup(this.bookingController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testGetBookingsBetweenDates() throws Exception {
        when(this.bookingService.getBookingsMadeByUsername(any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/api/v2/booking/view/all/dates={checkInDate}&{checkOutDate}", LocalDate.ofEpochDay(1L),
                LocalDate.ofEpochDay(1L));
        MockMvcBuilders.standaloneSetup(this.bookingController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testGetBookingsByDate() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v2/booking/view/all/date={checkInDate}", LocalDate.ofEpochDay(1L));
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.bookingController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }
}

