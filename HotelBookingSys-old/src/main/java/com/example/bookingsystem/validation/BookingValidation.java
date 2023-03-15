package com.example.bookingsystem.validation;

import com.example.bookingsystem.domain.Booking;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
@Slf4j
public class BookingValidation {
    static String dateRegrex = "\\d{4}-\\d{2}-\\d{2}";

    public static boolean checkBooking(Booking booking) {
        if (booking.getBookedRooms().isEmpty()) {
            log.debug("Booked Rooms: " + booking.getBookedRooms());
            return true;
        }

        if (booking.getBookedRoomsGuest().isEmpty()) {
            log.debug("Booked Rooms Guest: " + booking.getBookedRoomsGuest());
            return true;
        }

        if (CatalogValidation.checkCatalog(booking.getCatalog())) {
            log.debug("Catalog: " + booking.getCatalog());
            return true;
        }

        if (checkBookingCheckInDate(booking.getCheckInDate())) {
            log.debug("Check In Date: " + booking.getCheckInDate());
            return true;
        }

        if (checkBookingCheckOutDate(booking.getCheckOutDate())) {
            log.debug("Check In Date: " + booking.getCheckInDate());
            return true;
        }

        if (booking.getUser() == null) {
            log.debug("User: " + booking.getUser());
            return true;
        }

        log.info("No error found in booking details");
        return false;
    }

    public static boolean checkBookingWhenSaving(Booking booking) {
        if (checkBookingCheckInDate(booking.getCheckInDate())) {
            log.debug("Check In Date: " + booking.getCheckInDate());
            return true;
        }

        if (checkBookingCheckOutDate(booking.getCheckOutDate())) {
            log.debug("Check In Date: " + booking.getCheckInDate());
            return true;
        }

        log.info("No error found in booking details");
        return false;
    }

    private static boolean checkBookingCheckInDate(LocalDate checkInDate) {
        if (!(checkInDate.toString().matches(dateRegrex))) {
            return true;
        }

        return false;
    }

    private static boolean checkBookingCheckOutDate(LocalDate checkOutDate) {
        if (!(checkOutDate.toString().matches(dateRegrex))) {
            return true;
        }

        return false;
    }
}
