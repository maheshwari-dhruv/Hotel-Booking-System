package com.example.bookingsystem.controller;


import com.example.bookingsystem.domain.Room;
import com.example.bookingsystem.service.RoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/room")
public class RoomController {
    @Autowired
    private RoomService roomService;

    @PreAuthorize("hasAuthority('staff')")
    @PostMapping("/create")
    public ResponseEntity<String> createRoom(@RequestBody Room room) {
        return roomService.saveRoom(room);
    }

    @PreAuthorize("hasAnyAuthority('staff', 'customer')")
    @GetMapping("/view/all")
    public ResponseEntity<List<Room>> getAllRooms() {
        return ResponseEntity.ok().body(roomService.getAllRooms());
    }

    @PreAuthorize("hasAnyAuthority('staff', 'customer')")
    @GetMapping("/view/id/{roomId}")
    public ResponseEntity<Room> getRoomByRoomId(@PathVariable Long roomId) {
        return ResponseEntity.ok().body(roomService.getRoomById(roomId));
    }

    @PreAuthorize("hasAnyAuthority('staff', 'customer')")
    @GetMapping("/view/all/type/{roomType}")
    public ResponseEntity<List<Room>> getRoomByRoomType(@PathVariable String roomType) {
        return ResponseEntity.ok().body(roomService.getRoomsByType(roomType.toLowerCase()));
    }

    @PreAuthorize("hasAnyAuthority('staff', 'customer')")
    @GetMapping("/view/all/occupancy/adults/{adults}/children/{children}")
    public ResponseEntity<List<Room>> getRoomByRoomOccupancy(@PathVariable int adults, @PathVariable int children) {
        return ResponseEntity.ok().body(roomService.getRoomsByOccupancy(adults, children));
    }

    @PreAuthorize("hasAnyAuthority('staff', 'customer')")
    @GetMapping("/view/all/price/{startingPrice}&{endingPrice}")
    public ResponseEntity<List<Room>> getRoomsBetweenPrice(@PathVariable Double startingPrice, @PathVariable Double endingPrice) {
        return ResponseEntity.ok().body(roomService.getRoomsBetweenPriceRange(startingPrice, endingPrice));
    }

    @PreAuthorize("hasAnyAuthority('staff', 'customer')")
    @GetMapping("/view/all/available")
    public ResponseEntity<List<Room>> getAllAvailable() {
        return ResponseEntity.ok().body(roomService.getAllAvailableRooms());
    }

    @PreAuthorize("hasAuthority('staff')")
    @GetMapping("/view/all/reserved")
    public ResponseEntity<List<Room>> getAllReserved() {
        return ResponseEntity.ok().body(roomService.getAllReservedRooms());
    }

    @PreAuthorize("hasAuthority('staff')")
    @PutMapping("/update/{roomId}")
    public ResponseEntity<Room> updateRoom(@PathVariable Long roomId, @RequestBody Room room) {
        return ResponseEntity.ok().body(roomService.updateRoom(roomId, room));
    }

    @PreAuthorize("hasAuthority('staff')")
    @DeleteMapping("/delete/{roomId}")
    public ResponseEntity<String> deleteRoom(@PathVariable Long roomId) {
        return roomService.deleteRoomById(roomId);
    }
}
