package com.example.bookingsystem.validation;

import com.example.bookingsystem.domain.Room;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RoomValidationTest {

    @Test
    void testCheckRoom_allValuesAreCorrect() {
        Room room = new Room();
        room.setId(123L);
        room.setAdults(1);
        room.setChildren(1);
        room.setCost(30.5);
        room.setType("balcony");
        room.setBookings(new ArrayList<>());

        assertFalse(RoomValidation.checkRoom(room));
    }

    @Test
    void testCheckRoom_adultValueIsLessThanZero() {
        Room room = new Room();
        room.setId(123L);
        room.setAdults(-1);
        room.setChildren(1);
        room.setCost(30.5);
        room.setType("balcony");
        room.setBookings(new ArrayList<>());

        assertTrue(RoomValidation.checkRoom(room));
    }

    @Test
    void testCheckRoom_childrenValueIsLessThanZero() {
        Room room = new Room();
        room.setId(123L);
        room.setAdults(1);
        room.setChildren(-1);
        room.setCost(30.5);
        room.setType("balcony");
        room.setBookings(new ArrayList<>());

        assertTrue(RoomValidation.checkRoom(room));
    }

    @Test
    void testCheckRoom_costValueIsLessThanZero() {
        Room room = new Room();
        room.setId(123L);
        room.setAdults(1);
        room.setChildren(1);
        room.setCost(-30.5);
        room.setType("balcony");
        room.setBookings(new ArrayList<>());

        assertTrue(RoomValidation.checkRoom(room));
    }

    @Test
    void testCheckRoom_roomTypeValueIsEmptyString() {
        Room room = new Room();
        room.setId(123L);
        room.setAdults(1);
        room.setChildren(-1);
        room.setCost(30.5);
        room.setType("");
        room.setBookings(new ArrayList<>());

        assertTrue(RoomValidation.checkRoom(room));
    }
}
