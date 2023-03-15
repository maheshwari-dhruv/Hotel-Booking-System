package com.example.bookingsystem.controller.catalog;

import com.example.bookingsystem.domain.catalog.Facility;
import com.example.bookingsystem.service.catalog.FacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/facility")
public class FacilityController {

    @Autowired
    private FacilityService facilityService;

    @PreAuthorize("hasAuthority('staff')")
    @PostMapping("/create")
    public ResponseEntity<String> saveFacility(@RequestBody Facility facility) {
        return facilityService.saveFacility(facility);
    }

    @PreAuthorize("hasAnyAuthority('staff', 'customer')")
    @GetMapping("/view/all")
    public ResponseEntity<List<Facility>> getAllFacilities() {
        return ResponseEntity.ok().body(facilityService.getAllFacility());
    }

    @PreAuthorize("hasAnyAuthority('staff', 'customer')")
    @GetMapping("/view/{facilityName}")
    public ResponseEntity<Facility> getFacilityByName(@PathVariable String facilityName) {
        return ResponseEntity.ok().body(facilityService.getFacilityByFacilityName(facilityName));
    }

    @PreAuthorize("hasAuthority('staff')")
    @PutMapping("/update/{facilityName}")
    public ResponseEntity<String> updateFacility(@PathVariable String facilityName, @RequestBody Facility facility) {
        return facilityService.updateFacility(facilityName, facility);
    }

    @PreAuthorize("hasAuthority('staff')")
    @DeleteMapping("/delete/{facilityName}")
    public ResponseEntity<String> deleteFacility(@PathVariable String facilityName) {
        return facilityService.deleteFacility(facilityName);
    }
}
