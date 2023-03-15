package com.example.bookingsystem.service.impl;

import com.example.bookingsystem.domain.Room;
import com.example.bookingsystem.repository.RoomRepository;
import com.example.bookingsystem.service.RoomService;
import com.example.bookingsystem.validation.RoomValidation;
import com.example.bookingsystem.validation.StringValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
@Transactional
@Slf4j
public class RoomServiceImpl implements RoomService {
    @Autowired
    private RoomRepository roomRepository;

    @Override
    public ResponseEntity<String> saveRoom(Room room) {
        log.debug("Function saveRoom - room: " + room);

        if (RoomValidation.checkRoom(room)) {
            log.error("room details incorrect");
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "room details incorrect");
        }

        log.info("saving room: " + room);
        roomRepository.save(room);
        log.info("Room saved in db");
        return ResponseEntity.ok().body("Room saved.");
    }

    @Override
    public List<Room> getAllRooms() {
        log.info("fetching all rooms");
        List<Room> allRooms = roomRepository.findAll();

        log.debug("Function - getAllRooms - allRooms: " + allRooms);

        if (allRooms.isEmpty()) {
            log.error("no room in db");
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No room in db");
        }

        log.info("All Rooms fetched");
        return allRooms;
    }

    @Override
    public Room getRoomById(Long id) {
        log.debug("Function - getRoomById - id: " + id);

        if (id < 0) {
            log.error("id can't be null or less than 0");
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "id can't be null or less than 0");
        }

        log.info("fetching room with id: " + id);
        Room roomFound = roomRepository.getById(id);

        log.debug("Function - getRoomById - roomFound: " + roomFound);

        if (roomFound == null) {
            log.error("no room found");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "no room found");
        }

        log.info("Room fetched");
        return roomFound;
    }

    @Override
    public List<Room> getRoomsByType(String type) {
        log.debug("Function getRoomsByType - type: " + type);

        if (StringValidation.checkNullEmptyString(type)) {
            log.error("type can't be null or empty");
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Type can't be null or empty");
        }

        log.info("fetching rooms with type: " + type);
        List<Room> roomsByType = roomRepository.findAll()
                .stream()
                .filter(room -> room.getType().equalsIgnoreCase(type))
                .toList();

        log.debug("Fetched rooms: " + roomsByType);

        if (roomsByType.isEmpty()) {
            log.error("no rooms by found by this type");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "no rooms by found by this type");
        }

        log.info("Rooms fetched");
        return roomsByType;
    }

    @Override
    public List<Room> getRoomsByOccupancy(int adults, int children) {
        log.debug("Function getRoomsByOccupancy - adults: " + adults + " | children: " + children);

        if (adults < 0 && children < 0) {
            log.error("room can't have no adult or children");
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "room can't have no adult or children");
        }

        log.info("fetching rooms with occupancy-> adults: " + adults + " | children: " + children);

        List<Room> roomsByOccupancy = roomRepository.findAll()
                .stream()
                .filter(room -> room.getAdults() == adults && room.getChildren() == children)
                .toList();

        log.debug("All Room by occupancy: " + roomsByOccupancy);

        if (roomsByOccupancy.isEmpty()) {
            log.error("no rooms for such occupancy");
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "no rooms for such occupancy");
        }

        log.info("Rooms fetched");
        return roomsByOccupancy;
    }

    @Override
    public List<Room> getRoomsBetweenPriceRange(Double startingPrice, Double endingPrice) {
        log.debug("Function getRoomsBetweenPriceRange - starting price: " + startingPrice + " | ending price: " + endingPrice);

        if (startingPrice < 0.0 || endingPrice <= 0.0) {
            log.error("starting & ending price cannot be less than or equal to 0");
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "starting & ending price cannot be less than or equal to 0");
        }

        log.info("fetching rooms between " + startingPrice + " & " + endingPrice + " price");

        List<Room> roomsBetweenPrice = roomRepository.findAll()
                .stream()
                .filter(room -> room.getCost() >= startingPrice && room.getCost() <= endingPrice)
                .toList();

        log.debug("Rooms between price: " + roomsBetweenPrice);

        if (roomsBetweenPrice.isEmpty()) {
            log.error("no rooms between given price");
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "no rooms between given price");
        }

        log.info("Rooms fetched");
        return roomsBetweenPrice;
    }

    @Override
    public List<Room> getAllReservedRooms() {
        log.info("fetching all reserved rooms");
        List<Room> allReservedRooms = roomRepository.findAll()
                .stream()
                .filter(Room::getIsReserved)
                .toList();

        log.debug("All reserved rooms: " + allReservedRooms);

        if (allReservedRooms.isEmpty()) {
            log.error("no rooms booked");
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "no rooms are reserved");
        }

        log.info("Rooms fetched");
        return allReservedRooms;
    }

    @Override
    public List<Room> getAllAvailableRooms() {
        log.info("fetching all reserved rooms");

        List<Room> allAvailableRooms = roomRepository.findAll()
                .stream()
                .filter(room -> !room.getIsReserved())
                .toList();

        log.debug("All available rooms: " + allAvailableRooms);

        if (allAvailableRooms.isEmpty()) {
            log.error("no rooms available");
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "no rooms are available");
        }

        log.info("Rooms fetched");
        return allAvailableRooms;
    }

    @Override
    public Room updateRoom(Long id, Room room) {
        log.debug("Function updateRoom - id: " + id + " | room: " + room);

        if (id < 0) {
            log.error("id can't be null or less than 0");
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "id can't be null or less than 0");
        }

        if (RoomValidation.checkRoom(room)) {
            log.error("room details incorrect");
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "room details incorrect");
        }

        log.info("updating room");
        Room roomToUpdate = getRoomById(id);

        log.debug("Room found: " + roomToUpdate);

        if (roomToUpdate == null) {
            log.error("no room found with this id");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "no room found with this id");
        }

        log.debug("Room cost: " + room.getCost());
        roomToUpdate.setCost(room.getCost());

        log.debug("Room Children Capacity: " + room.getChildren());
        roomToUpdate.setChildren(room.getChildren());

        log.debug("Room Adults Capacity: " + room.getAdults());
        roomToUpdate.setAdults(room.getAdults());

        log.debug("Room Type: " + room.getType());
        roomToUpdate.setType(room.getType());

        log.debug("Room after update: " + roomToUpdate);
        return roomRepository.save(roomToUpdate);
    }
    @Override
    public ResponseEntity<String> deleteRoomById(Long id) {
        log.debug("Function deleteRoomById - id: " + id);

        if (id < 0) {
            log.error("id can't be null or less than 0");
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "id can't be null or less than 0");
        }

        roomRepository.deleteById(id);
        return ResponseEntity.ok().body("room with id: " + id + " deleted successfully");
    }
}
