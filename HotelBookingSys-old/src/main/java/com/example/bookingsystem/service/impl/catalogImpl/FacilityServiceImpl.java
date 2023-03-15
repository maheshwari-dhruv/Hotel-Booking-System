package com.example.bookingsystem.service.impl.catalogImpl;

import com.example.bookingsystem.domain.catalog.Facility;
import com.example.bookingsystem.repository.catalog.FacilityRepository;
import com.example.bookingsystem.service.catalog.FacilityService;
import com.example.bookingsystem.validation.StringValidation;
import com.example.bookingsystem.validation.catalog.FacilityValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
@Slf4j
public class FacilityServiceImpl implements FacilityService {
    @Autowired
    private FacilityRepository facilityRepository;

    @Override
    public ResponseEntity<String> saveFacility(Facility facility) {
        log.debug("Function saveFacility - facility: " + facility);

        if (FacilityValidation.checkFacility(facility)) {
            log.error("Facility details incorrect");
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Facility details incorrect");
        }

        log.info("saving facility to db");
        facilityRepository.save(facility);
        return ResponseEntity.ok().body("Facility saved");
    }

    @Override
    public List<Facility> getAllFacility() {
        List<Facility> allFacilities = facilityRepository.findAll();
        log.debug("Function getAllFacility - allFacilities: " + allFacilities);

        if (allFacilities.isEmpty()) {
            log.error("no facility found");
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "no facility found");
        }

        log.info("All facilities fetched");
        return allFacilities;
    }

    @Override
    public Facility getFacilityByFacilityName(String facilityName) {
        log.debug("Function getFacilityByFacilityName - facility name: " + facilityName);

        if (StringValidation.checkNullEmptyString(facilityName)) {
            log.error("Facility details incorrect");
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Facility details incorrect");
        }

        Facility byFacilityName = facilityRepository.findByName(facilityName);
        log.debug("facility found: " + byFacilityName);

        if (byFacilityName == null) {
            log.error("No facility found");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No facility found");
        }

        return byFacilityName;
    }

    @Override
    public ResponseEntity<String> updateFacility(String facilityName, Facility facility) {
        log.debug("Function updateFacility - facilityName: " + facilityName + " | facility: " + facility);

        if (StringValidation.checkNullEmptyString(facilityName) || FacilityValidation.checkFacility(facility)) {
            log.error("Facility details incorrect");
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Facility details incorrect");
        }

        Facility facilityFound = facilityRepository.findByName(facilityName);
        log.debug("Facility found: " + facilityFound);

        if (facilityFound == null) {
            log.error("No facility found with this facility name: " + facilityName);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No facility found with this facility name: " + facilityName);
        }

        log.info("Updating facility");
        log.debug("Facility name: " + facility.getName());
        facilityFound.setName(facility.getName());

        log.debug("facility price: " + facility.getPrice());
        facilityFound.setPrice(facility.getPrice());

        log.debug("facility after update: " + facilityFound);
        log.info("facility updated");
        return ResponseEntity.ok().body("Facility updated: " + facilityFound);
    }

    @Override
    public ResponseEntity<String> deleteFacility(String facilityName) {
        log.debug("Function deleteFacility - facility name: " + facilityName);

        if (StringValidation.checkNullEmptyString(facilityName)) {
            log.error("Facility details incorrect");
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Facility details incorrect");
        }

        log.info("deleting facility with facility name:" + facilityName);
        facilityRepository.deleteByName(facilityName);
        return ResponseEntity.ok().body("deleted facility with facility name: " + facilityName);
    }
}
