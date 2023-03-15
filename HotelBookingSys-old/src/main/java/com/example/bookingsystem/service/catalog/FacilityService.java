package com.example.bookingsystem.service.catalog;

import com.example.bookingsystem.domain.catalog.Facility;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FacilityService {
    ResponseEntity<String> saveFacility(Facility facility);

    List<Facility> getAllFacility();

    Facility getFacilityByFacilityName(String facilityName);

    ResponseEntity<String> updateFacility(String facilityName, Facility facility);
    ResponseEntity<String> deleteFacility(String facilityName);
}
