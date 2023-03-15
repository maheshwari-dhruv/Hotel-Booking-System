package com.example.bookingsystem.controller;

import com.example.bookingsystem.domain.Guest;
import com.example.bookingsystem.domain.Room;
import com.example.bookingsystem.service.GuestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/guest")
public class GuestController {
    @Autowired
    private GuestService guestService;

    @PreAuthorize("hasAnyAuthority('staff', 'customer')")
    @PostMapping("/create")
    public ResponseEntity<String> saveGuest(@RequestBody Guest guest) {
        return guestService.saveGuest(guest);
    }

    @PreAuthorize("hasAnyAuthority('staff', 'customer')")
    @GetMapping("/view/all")
    public ResponseEntity<List<Guest>> getGuests() {
        return ResponseEntity.ok().body(guestService.getAllGuests());
    }

    @PreAuthorize("hasAnyAuthority('staff', 'customer')")
    @GetMapping("/view/{guestId}")
    public ResponseEntity<Guest> getGuestById(@PathVariable Long guestId) {
        return ResponseEntity.ok().body(guestService.getGuestById(guestId));
    }

    @PreAuthorize("hasAnyAuthority('staff', 'customer')")
    @PutMapping("/update/{guestId}")
    public ResponseEntity<Guest> updateGuestById(@PathVariable Long guestId, @RequestBody Guest guest) {
        return ResponseEntity.ok().body(guestService.updateGuest(guestId, guest));
    }

    @PreAuthorize("hasAnyAuthority('staff', 'customer')")
    @DeleteMapping("/delete/{guestId}")
    public ResponseEntity<String> deleteGuestById(@PathVariable Long guestId) {
        return guestService.deleteGuestById(guestId);
    }
}
