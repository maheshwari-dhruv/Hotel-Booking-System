package com.example.bookingsystem.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import com.example.bookingsystem.domain.Room;
import com.example.bookingsystem.repository.RoomRepository;

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
class RoomServiceImplTest {
    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private RoomServiceImpl roomServiceImpl;

    @Test
    void testSaveRoom_roomSavedSuccessfully() {
        Room room = new Room();
        room.setId(123L);
        room.setBookings(new ArrayList<>());
        room.setType("Balcony");
        room.setCost(35.45);
        room.setChildren(1);
        room.setAdults(1);

        ResponseEntity<String> actualSaveRoomResult = roomServiceImpl.saveRoom(room);
        assertEquals("Room saved.", actualSaveRoomResult.getBody());
        assertEquals(HttpStatus.OK, actualSaveRoomResult.getStatusCode());
        verify(roomRepository, times(1)).save(any());
    }

    @Test
    void testSaveRoom_roomDetailsAreIncorrect() {
        Room room = new Room();
        room.setId(123L);
        room.setBookings(new ArrayList<>());
        room.setType("");
        room.setCost(35.45);
        room.setChildren(1);
        room.setAdults(1);

        Exception ex = assertThrows(ResponseStatusException.class, () -> roomServiceImpl.saveRoom(room));
        String expectedMessage = "room details incorrect";
        String actualMessage = ex.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(roomRepository, times(0)).save(any());
    }

    @Test
    void testGetAllRooms_noRoomFound() {
        when(roomRepository.findAll()).thenReturn(new ArrayList<>());

        Exception ex = assertThrows(ResponseStatusException.class, () -> roomServiceImpl.getAllRooms());
        String expectedMessage = "No room in db";
        String actualMessage = ex.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(roomRepository, times(0)).save(any());
    }

    @Test
    void testGetAllRooms_allRoomsFetchedSuccessfully() {
        Room room = new Room();
        room.setId(123L);
        room.setBookings(new ArrayList<>());
        room.setType("Balcony");
        room.setCost(35.45);
        room.setChildren(1);
        room.setAdults(1);

        List<Room> roomList = new ArrayList<>();
        roomList.add(room);
        when(roomRepository.findAll()).thenReturn(roomList);

        List<Room> actualAllRooms = roomServiceImpl.getAllRooms();

        assertSame(roomList, actualAllRooms);
        assertEquals(1, actualAllRooms.size());
        verify(roomRepository, times(1)).findAll();
    }

    @Test
    void testGetRoomById_roomSuccessfullyFetchedByRoomId() {
        Room room = new Room();
        room.setId(123L);
        room.setBookings(new ArrayList<>());
        room.setType("Balcony");
        room.setCost(35.45);
        room.setChildren(1);
        room.setAdults(1);

        when(roomRepository.getById(any())).thenReturn(room);

        assertSame(room, roomServiceImpl.getRoomById(123L));
        verify(roomRepository).getById(any());
    }

    @Test
    void testGetRoomById_idIsLessThanZero() {
        Exception ex = assertThrows(ResponseStatusException.class, () -> roomServiceImpl.getRoomById(-123L));
        String expectedMessage = "id can't be null or less than 0";
        String actualMessage = ex.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testGetRoomById_noRoomFound() {
        Exception ex = assertThrows(ResponseStatusException.class, () -> roomServiceImpl.getRoomById(123L));
        String expectedMessage = "no room found";
        String actualMessage = ex.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testGetRoomsBetweenPriceRange_noRoomBetweenThisPriceRange() {
        when(roomRepository.findAll()).thenReturn(new ArrayList<>());

        Exception ex = assertThrows(ResponseStatusException.class, () -> roomServiceImpl.getRoomsBetweenPriceRange(20.0, 30.0));
        String expectedMessage = "no rooms between given price";
        String actualMessage = ex.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testGetRoomsBetweenPriceRange_roomSuccessfullyFetched() {
        Room room = new Room();
        room.setId(123L);
        room.setBookings(new ArrayList<>());
        room.setType("Balcony");
        room.setCost(35.45);
        room.setChildren(1);
        room.setAdults(1);

        ArrayList<Room> roomList = new ArrayList<>();
        roomList.add(room);

        when(roomRepository.findAll()).thenReturn(roomList);
        assertEquals(1, roomServiceImpl.getRoomsBetweenPriceRange(20.0, 40.0).size());
        verify(roomRepository).findAll();
    }

    @Test
    void testGetRoomsBetweenPriceRange3() {
        Exception ex = assertThrows(ResponseStatusException.class, () -> roomServiceImpl.getRoomsBetweenPriceRange(20.0, -30.0));
        String expectedMessage = "starting & ending price cannot be less than or equal to 0";
        String actualMessage = ex.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testGetAllReservedRooms_noRoomFound() {
        when(roomRepository.findAll()).thenReturn(new ArrayList<>());

        Exception ex = assertThrows(ResponseStatusException.class, () -> roomServiceImpl.getAllReservedRooms());
        String expectedMessage = "no rooms are reserved";
        String actualMessage = ex.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testGetAllReservedRooms_roomFetchedSuccessfully() {
        Room room = new Room();
        room.setId(123L);
        room.setBookings(new ArrayList<>());
        room.setType("Balcony");
        room.setCost(35.45);
        room.setChildren(1);
        room.setAdults(1);
        room.setIsReserved(true);

        List<Room> roomList = new ArrayList<>();
        roomList.add(room);

        when(roomRepository.findAll()).thenReturn(roomList);

        assertEquals(1, roomServiceImpl.getAllReservedRooms().size());
        verify(roomRepository).findAll();
    }

    @Test
    void testGetAllAvailableRooms_noRoomsAreAvailable() {
        when(roomRepository.findAll()).thenReturn(new ArrayList<>());

        Exception ex = assertThrows(ResponseStatusException.class, () -> roomServiceImpl.getAllAvailableRooms());
        String expectedMessage = "no rooms are available";
        String actualMessage = ex.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testGetAllAvailableRooms_roomFetchedSuccessfully() {
        Room room = new Room();
        room.setId(123L);
        room.setBookings(new ArrayList<>());
        room.setType("Balcony");
        room.setCost(35.45);
        room.setChildren(1);
        room.setAdults(1);

        List<Room> roomList = new ArrayList<>();
        roomList.add(room);

        when(roomRepository.findAll()).thenReturn(roomList);

        assertEquals(1, roomServiceImpl.getAllAvailableRooms().size());
        verify(roomRepository).findAll();
    }

    @Test
    void testDeleteRoomById_roomDeletedSuccessfully() {
        ResponseEntity<String> actualDeleteRoomByIdResult = this.roomServiceImpl.deleteRoomById(123L);
        assertEquals("room with id: 123 deleted successfully", actualDeleteRoomByIdResult.getBody());
        assertEquals(HttpStatus.OK, actualDeleteRoomByIdResult.getStatusCode());
    }

    @Test
    void testDeleteRoomById_roomIdIncorrect() {
        Exception ex = assertThrows(ResponseStatusException.class, () -> roomServiceImpl.deleteRoomById(-123L));
        String expectedMessage = "id can't be null or less than 0";
        String actualMessage = ex.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}

