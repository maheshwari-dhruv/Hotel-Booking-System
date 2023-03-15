package com.example.bookingsystem.service;

import com.example.bookingsystem.domain.Room;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RoomService {
    ResponseEntity<String> saveRoom(Room room);
    List<Room> getAllRooms();
    Room getRoomById(Long id);
    List<Room> getRoomsByType(String type);
    List<Room> getRoomsByOccupancy(int adults, int children);
    List<Room> getRoomsBetweenPriceRange(Double startingPrice, Double endingPrice);
    List<Room> getAllReservedRooms();
    List<Room> getAllAvailableRooms();
    Room updateRoom(Long id, Room room);
    ResponseEntity<String> deleteRoomById(Long id);
}
