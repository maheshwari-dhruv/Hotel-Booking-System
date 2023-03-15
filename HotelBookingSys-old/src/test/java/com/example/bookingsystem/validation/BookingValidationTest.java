package com.example.bookingsystem.validation;

import com.example.bookingsystem.domain.*;
import com.example.bookingsystem.domain.catalog.Facility;
import com.example.bookingsystem.domain.catalog.Meal;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BookingValidationTest {

    @Test
    void testCheckBooking_allValuesAreCorrect() {
        Room room = new Room();
        room.setId(123L);
        room.setAdults(1);
        room.setChildren(1);
        room.setCost(20.0);
        room.setType("Balcony");
        room.setBookings(new ArrayList<>());

        List<Room> rooms = new ArrayList<>();
        rooms.add(room);

        Guest guest = new Guest();
        guest.setId(123L);
        guest.setFirstName("dhruv");
        guest.setLastName("maheshwari");
        guest.setAge(21);
        guest.setBookings(new ArrayList<>());

        List<Guest> guests = new ArrayList<>();
        guests.add(guest);

        Facility facility = new Facility();
        facility.setId(123L);
        facility.setName("Gym");
        facility.setPrice(12.4);

        List<Facility> facilityList = new ArrayList<>();
        facilityList.add(facility);

        Meal meal = new Meal();
        meal.setId(123L);
        meal.setName("Lunch");
        meal.setMealFromTime("9:00");
        meal.setMealToTime("10:00");

        List<Meal> mealList = new ArrayList<>();
        mealList.add(meal);

        Catalog catalog = new Catalog();
        catalog.setId(123L);
        catalog.setCatalogFacilities(facilityList);
        catalog.setCatalogMeals(mealList);
        catalog.setBooking(new Booking());

        Booking booking = new Booking();
        booking.setId(123L);
        booking.setCheckInDate(LocalDate.parse("2022-03-20"));
        booking.setCheckOutDate(LocalDate.parse("2022-03-21"));
        booking.setBookedRooms(rooms);
        booking.setBookedRoomsGuest(guests);
        booking.setCatalog(catalog);
        booking.setPayment(new Payment());
        booking.setUser(new User());

        assertFalse(BookingValidation.checkBooking(booking));
    }

    @Test
    void testCheckBooking_roomListIsEmpty() {
        Guest guest = new Guest();
        guest.setId(123L);
        guest.setFirstName("dhruv");
        guest.setLastName("maheshwari");
        guest.setAge(21);
        guest.setBookings(new ArrayList<>());

        List<Guest> guests = new ArrayList<>();
        guests.add(guest);

        Facility facility = new Facility();
        facility.setId(123L);
        facility.setName("Gym");
        facility.setPrice(12.4);

        List<Facility> facilityList = new ArrayList<>();
        facilityList.add(facility);

        Meal meal = new Meal();
        meal.setId(123L);
        meal.setName("Lunch");
        meal.setMealFromTime("9:00");
        meal.setMealToTime("10:00");

        List<Meal> mealList = new ArrayList<>();
        mealList.add(meal);

        Catalog catalog = new Catalog();
        catalog.setId(123L);
        catalog.setCatalogFacilities(facilityList);
        catalog.setCatalogMeals(mealList);
        catalog.setBooking(new Booking());

        Booking booking = new Booking();
        booking.setId(123L);
        booking.setCheckInDate(LocalDate.parse("2022-03-20"));
        booking.setCheckOutDate(LocalDate.parse("2022-03-21"));
        booking.setBookedRooms(new ArrayList<>());
        booking.setBookedRoomsGuest(guests);
        booking.setCatalog(catalog);
        booking.setPayment(new Payment());
        booking.setUser(new User());

        assertTrue(BookingValidation.checkBooking(booking));
    }

    @Test
    void testCheckBooking_guestListIsEmpty() {
        Room room = new Room();
        room.setId(123L);
        room.setAdults(1);
        room.setChildren(1);
        room.setCost(20.0);
        room.setType("Balcony");
        room.setBookings(new ArrayList<>());

        List<Room> rooms = new ArrayList<>();
        rooms.add(room);

        Facility facility = new Facility();
        facility.setId(123L);
        facility.setName("Gym");
        facility.setPrice(12.4);

        List<Facility> facilityList = new ArrayList<>();
        facilityList.add(facility);

        Meal meal = new Meal();
        meal.setId(123L);
        meal.setName("Lunch");
        meal.setMealFromTime("9:00");
        meal.setMealToTime("10:00");

        List<Meal> mealList = new ArrayList<>();
        mealList.add(meal);

        Catalog catalog = new Catalog();
        catalog.setId(123L);
        catalog.setCatalogFacilities(facilityList);
        catalog.setCatalogMeals(mealList);
        catalog.setBooking(new Booking());

        Booking booking = new Booking();
        booking.setId(123L);
        booking.setCheckInDate(LocalDate.parse("2022-03-20"));
        booking.setCheckOutDate(LocalDate.parse("2022-03-21"));
        booking.setBookedRooms(rooms);
        booking.setBookedRoomsGuest(new ArrayList<>());
        booking.setCatalog(catalog);
        booking.setPayment(new Payment());
        booking.setUser(new User());

        assertTrue(BookingValidation.checkBooking(booking));
    }

    @Test
    void testCheckBooking_catalogIsNull() {
        Room room = new Room();
        room.setId(123L);
        room.setAdults(1);
        room.setChildren(1);
        room.setCost(20.0);
        room.setType("Balcony");
        room.setBookings(new ArrayList<>());

        List<Room> rooms = new ArrayList<>();
        rooms.add(room);

        Guest guest = new Guest();
        guest.setId(123L);
        guest.setFirstName("dhruv");
        guest.setLastName("maheshwari");
        guest.setAge(21);
        guest.setBookings(new ArrayList<>());

        List<Guest> guests = new ArrayList<>();
        guests.add(guest);

        Booking booking = new Booking();
        booking.setId(123L);
        booking.setCheckInDate(LocalDate.parse("2022-03-20"));
        booking.setCheckOutDate(LocalDate.parse("2022-03-21"));
        booking.setBookedRooms(rooms);
        booking.setBookedRoomsGuest(guests);
        booking.setCatalog(new Catalog());
        booking.setPayment(new Payment());
        booking.setUser(new User());

        assertTrue(BookingValidation.checkBooking(booking));
    }

    @Test
    void testCheckBooking_userIsNull() {
        Room room = new Room();
        room.setId(123L);
        room.setAdults(1);
        room.setChildren(1);
        room.setCost(20.0);
        room.setType("Balcony");
        room.setBookings(new ArrayList<>());

        List<Room> rooms = new ArrayList<>();
        rooms.add(room);

        Guest guest = new Guest();
        guest.setId(123L);
        guest.setFirstName("dhruv");
        guest.setLastName("maheshwari");
        guest.setAge(21);
        guest.setBookings(new ArrayList<>());

        List<Guest> guests = new ArrayList<>();
        guests.add(guest);

        Facility facility = new Facility();
        facility.setId(123L);
        facility.setName("Gym");
        facility.setPrice(12.4);

        List<Facility> facilityList = new ArrayList<>();
        facilityList.add(facility);

        Meal meal = new Meal();
        meal.setId(123L);
        meal.setName("Lunch");
        meal.setMealFromTime("9:00");
        meal.setMealToTime("10:00");

        List<Meal> mealList = new ArrayList<>();
        mealList.add(meal);

        Catalog catalog = new Catalog();
        catalog.setId(123L);
        catalog.setCatalogFacilities(facilityList);
        catalog.setCatalogMeals(mealList);
        catalog.setBooking(new Booking());

        Booking booking = new Booking();
        booking.setId(123L);
        booking.setCheckInDate(LocalDate.parse("2022-03-20"));
        booking.setCheckOutDate(LocalDate.parse("2022-03-21"));
        booking.setBookedRooms(rooms);
        booking.setBookedRoomsGuest(guests);
        booking.setCatalog(catalog);
        booking.setPayment(new Payment());
        booking.setUser(null);

        assertTrue(BookingValidation.checkBooking(booking));
    }

    @Test
    void testCheckBookingWhenSaving_allValuesAreCorrect() {
        Booking booking = new Booking();
        booking.setId(123L);
        booking.setCheckInDate(LocalDate.parse("2022-03-20"));
        booking.setCheckOutDate(LocalDate.parse("2022-03-21"));
        booking.setBookedRooms(new ArrayList<>());
        booking.setBookedRoomsGuest(new ArrayList<>());
        booking.setCatalog(new Catalog());
        booking.setPayment(new Payment());
        booking.setUser(new User());

        assertFalse(BookingValidation.checkBookingWhenSaving(booking));
    }
}
