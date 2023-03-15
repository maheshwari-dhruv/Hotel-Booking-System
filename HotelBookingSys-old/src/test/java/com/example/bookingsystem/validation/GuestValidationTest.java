package com.example.bookingsystem.validation;

import com.example.bookingsystem.domain.Guest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GuestValidationTest {

    @Test
    void testCheckGuest_allValuesAreCorrect() {
        Guest guest = new Guest();
        guest.setId(123L);
        guest.setFirstName("Dhruv");
        guest.setLastName("Maheshwari");
        guest.setAge(21);
        guest.setBookings(new ArrayList<>());

        assertFalse(GuestValidation.checkGuest(guest));
    }

    @Test
    void testCheckGuest_firstNameValueIsEmptyString() {
        Guest guest = new Guest();
        guest.setId(123L);
        guest.setFirstName("");
        guest.setLastName("Maheshwari");
        guest.setAge(21);
        guest.setBookings(new ArrayList<>());

        assertTrue(GuestValidation.checkGuest(guest));
    }

    @Test
    void testCheckGuest_lastNameValueIsEmptyString() {
        Guest guest = new Guest();
        guest.setId(123L);
        guest.setFirstName("Dhruv");
        guest.setLastName("");
        guest.setAge(21);
        guest.setBookings(new ArrayList<>());

        assertTrue(GuestValidation.checkGuest(guest));
    }

    @Test
    void testCheckGuest_ageValueIsLessThanZero() {
        Guest guest = new Guest();
        guest.setId(123L);
        guest.setFirstName("Dhruv");
        guest.setLastName("Maheshwari");
        guest.setAge(-21);
        guest.setBookings(new ArrayList<>());

        assertTrue(GuestValidation.checkGuest(guest));
    }
}
