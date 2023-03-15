package com.example.bookingsystem.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import com.example.bookingsystem.domain.Guest;
import com.example.bookingsystem.repository.GuestRepository;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
class GuestServiceImplTest {
    @Mock
    private GuestRepository guestRepository;

    @InjectMocks
    private GuestServiceImpl guestServiceImpl;

    @Test
    void testSaveGuest_successfullySaved() {
        Guest guest = new Guest();
        guest.setAge(21);
        guest.setBookings(new ArrayList<>());
        guest.setFirstName("Dhruv");
        guest.setId(123L);
        guest.setLastName("Maheshwari");

        ResponseEntity<String> actualSaveGuestResult = guestServiceImpl.saveGuest(guest);
        assertEquals("Guest saved.", actualSaveGuestResult.getBody());
        assertEquals(HttpStatus.OK, actualSaveGuestResult.getStatusCode());
        verify(guestRepository, times(1)).save(any());
    }

    @Test
    void testSaveGuest_guestDetailsAreIncorrect() {
        Guest guest = new Guest();
        guest.setAge(21);
        guest.setBookings(new ArrayList<>());
        guest.setFirstName("");
        guest.setId(123L);
        guest.setLastName("Maheshwari");

        Exception ex = assertThrows(ResponseStatusException.class, () -> guestServiceImpl.saveGuest(guest));
        String expectedMessage = "Guest details incorrect";
        String actualMessage = ex.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(guestRepository, times(0)).save(any());
    }

    @Test
    void testGetAllGuests_successfullyFetchedAllGuests() {
        Guest guest = new Guest();
        guest.setAge(21);
        guest.setBookings(new ArrayList<>());
        guest.setFirstName("Dhruv");
        guest.setId(123L);
        guest.setLastName("Maheshwari");

        List<Guest> guestList = new ArrayList<>();
        guestList.add(guest);
        when(this.guestRepository.findAll()).thenReturn(guestList);

        List<Guest> actualAllGuests = guestServiceImpl.getAllGuests();
        assertSame(guestList, actualAllGuests);
        assertEquals(1, actualAllGuests.size());
        verify(guestRepository, times(1)).findAll();
    }

    @Test
    void testGetAllGuests_noGuestFound() {
        when(this.guestRepository.findAll()).thenReturn(new ArrayList<>());

        Exception ex = assertThrows(ResponseStatusException.class, () -> guestServiceImpl.getAllGuests());
        String expectedMessage = "no guest found in db";
        String actualMessage = ex.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(guestRepository, times(0)).save(any());
    }

    @Test
    void testGetGuestById_successfullyFetchedGuestByGuestId() {
        Guest guest = new Guest();
        guest.setAge(21);
        guest.setBookings(new ArrayList<>());
        guest.setFirstName("Dhruv");
        guest.setId(123L);
        guest.setLastName("Maheshwari");

        when(guestRepository.getById(any())).thenReturn(guest);

        assertSame(guest, guestServiceImpl.getGuestById(123L));
        verify(guestRepository).getById(any());
    }

    @Test
    void testGetGuestById_guestIdIsLessThanZero() {
        Exception ex = assertThrows(ResponseStatusException.class, () -> guestServiceImpl.getGuestById(-3L));
        String expectedMessage = "id can't be less than 0";
        String actualMessage = ex.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testGetGuestById_noGuestFound() {
        Exception ex = assertThrows(ResponseStatusException.class, () -> guestServiceImpl.getGuestById(3L));
        String expectedMessage = "Guest details incorrect";
        String actualMessage = ex.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testUpdateGuest_successfullyUpdateGuest() {
        Guest guest = new Guest();
        guest.setAge(21);
        guest.setBookings(new ArrayList<>());
        guest.setFirstName("Dhruv");
        guest.setId(123L);
        guest.setLastName("Maheshwari");
        when(guestRepository.getById(any())).thenReturn(guest);

        Guest updateGuest = new Guest();
        updateGuest.setAge(21);
        updateGuest.setBookings(new ArrayList<>());
        updateGuest.setFirstName("Kartik");
        updateGuest.setId(123L);
        updateGuest.setLastName("Maheshwari");
        when(guestRepository.save(any())).thenReturn(updateGuest);

        Guest actualUpdateGuestResult = guestServiceImpl.updateGuest(123L, updateGuest);
        assertSame(updateGuest, actualUpdateGuestResult);
        verify(guestRepository, times(1)).save(any());
        verify(guestRepository, times(1)).getById(any());
    }

    @Test
    void testUpdateGuest_guestIdIsLEssThanZero() {
        Guest updateGuest = new Guest();
        updateGuest.setAge(21);
        updateGuest.setBookings(new ArrayList<>());
        updateGuest.setFirstName("Kartik");
        updateGuest.setId(123L);
        updateGuest.setLastName("Maheshwari");

        Exception ex = assertThrows(ResponseStatusException.class, () -> guestServiceImpl.updateGuest(-123L, updateGuest));
        String expectedMessage = "id or guest details can't be null or empty";
        String actualMessage = ex.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testUpdateGuest_updateGuestDetailsAreIncorrect() {
        Guest updateGuest = new Guest();
        updateGuest.setAge(21);
        updateGuest.setBookings(new ArrayList<>());
        updateGuest.setFirstName("");
        updateGuest.setId(123L);
        updateGuest.setLastName("Maheshwari");

        Exception ex = assertThrows(ResponseStatusException.class, () -> guestServiceImpl.updateGuest(13L, updateGuest));
        String expectedMessage = "id or guest details can't be null or empty";
        String actualMessage = ex.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testDeleteGuestById_successfullyDeletedGuest() {
        ResponseEntity<String> actualDeleteGuestByIdResult = guestServiceImpl.deleteGuestById(123L);
        assertEquals("guest with guest id: 123 deleted successfully", actualDeleteGuestByIdResult.getBody());
        assertEquals(HttpStatus.OK, actualDeleteGuestByIdResult.getStatusCode());
    }

    @Test
    void testDeleteGuestById_guestIdLessThanZero() {
        Exception ex = assertThrows(ResponseStatusException.class, () -> guestServiceImpl.deleteGuestById(-1L));
        String expectedMessage = "id can't be null or empty";
        String actualMessage = ex.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}

