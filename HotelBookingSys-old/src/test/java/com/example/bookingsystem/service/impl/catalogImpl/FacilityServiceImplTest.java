package com.example.bookingsystem.service.impl.catalogImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import com.example.bookingsystem.domain.catalog.Facility;
import com.example.bookingsystem.repository.catalog.FacilityRepository;

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
class FacilityServiceImplTest {
    @Mock
    private FacilityRepository facilityRepository;

    @InjectMocks
    private FacilityServiceImpl facilityServiceImpl;

    @Test
    void testSaveFacility_successfullySaved() {
        Facility facility = new Facility();
        facility.setCatalogs(new ArrayList<>());
        facility.setId(123L);
        facility.setName("Gym");
        facility.setPrice(20.35);

        ResponseEntity<String> actualSaveFacilityResult = facilityServiceImpl.saveFacility(facility);
        assertEquals("Facility saved", actualSaveFacilityResult.getBody());
        assertEquals(HttpStatus.OK, actualSaveFacilityResult.getStatusCode());
        verify(facilityRepository, times(1)).save(any());
    }

    @Test
    void testSaveFacility_facilityDetailsAreIncorrect() {
        Facility facility = new Facility();
        facility.setCatalogs(new ArrayList<>());
        facility.setId(123L);
        facility.setName("");
        facility.setPrice(20.35);

        Exception ex = assertThrows(ResponseStatusException.class, () -> facilityServiceImpl.saveFacility(facility));
        String expectedMessage = "Facility details incorrect";
        String actualMessage = ex.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(facilityRepository, times(0)).save(any());
    }

    @Test
    void testGetAllFacility_successfullyFetchedAllFacilities() {
        Facility facility = new Facility();
        facility.setCatalogs(new ArrayList<>());
        facility.setId(123L);
        facility.setName("Gym");
        facility.setPrice(20.35);

        List<Facility> facilityList = new ArrayList<>();
        facilityList.add(facility);
        when(facilityRepository.findAll()).thenReturn(facilityList);

        List<Facility> actualAllFacility = facilityServiceImpl.getAllFacility();

        assertSame(facilityList, actualAllFacility);
        assertEquals(1, actualAllFacility.size());
        verify(facilityRepository, times(1)).findAll();
    }

    @Test
    void testGetAllFacility_facilityIsEmptyList() {
        when(facilityRepository.findAll()).thenReturn(new ArrayList<>());

        Exception ex = assertThrows(ResponseStatusException.class, () -> facilityServiceImpl.getAllFacility());
        String expectedMessage = "no facility found";
        String actualMessage = ex.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(facilityRepository, times(0)).save(any());
    }

    @Test
    void testGetFacilityByFacilityName_successfullyFetchedFacilityByFacilityName() {
        Facility facility = new Facility();
        facility.setCatalogs(new ArrayList<>());
        facility.setId(123L);
        facility.setName("Gym");
        facility.setPrice(20.35);

        when(facilityRepository.findByName(any())).thenReturn(facility);

        assertSame(facility, facilityServiceImpl.getFacilityByFacilityName("Gym"));
        verify(facilityRepository).findByName(any());
    }

    @Test
    void testGetFacilityByFacilityName_facilityNameValueIsEmptyString() {
        Exception ex = assertThrows(ResponseStatusException.class, () -> facilityServiceImpl.getFacilityByFacilityName(""));
        String expectedMessage = "Facility details incorrect";
        String actualMessage = ex.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testGetFacilityByFacilityName_noFacilityFound() {
        Exception ex = assertThrows(ResponseStatusException.class, () -> facilityServiceImpl.getFacilityByFacilityName("Water"));
        String expectedMessage = "No facility found";
        String actualMessage = ex.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testUpdateFacility_successfullyUpdateFacility() {
        Facility facility = new Facility();
        facility.setCatalogs(new ArrayList<>());
        facility.setId(123L);
        facility.setName("Gym");
        facility.setPrice(20.35);
        when(facilityRepository.findByName(any())).thenReturn(facility);

        Facility updateFacility = new Facility();
        updateFacility.setCatalogs(new ArrayList<>());
        updateFacility.setId(123L);
        updateFacility.setName("Water");
        updateFacility.setPrice(34.5);

        ResponseEntity<String> actualUpdateFacilityResult = facilityServiceImpl.updateFacility("Gym", updateFacility);
        assertEquals("Facility updated: Facility(id=123, name=Water, price=34.5)", actualUpdateFacilityResult.getBody());
        assertEquals(HttpStatus.OK, actualUpdateFacilityResult.getStatusCode());
        verify(this.facilityRepository).findByName(any());
    }

    @Test
    void testUpdateFacility_facilityNameIsEmptyString() {
        Facility updateFacility = new Facility();
        updateFacility.setCatalogs(new ArrayList<>());
        updateFacility.setId(123L);
        updateFacility.setName("Water");
        updateFacility.setPrice(34.5);

        Exception ex = assertThrows(ResponseStatusException.class, () -> facilityServiceImpl.updateFacility("", updateFacility));
        String expectedMessage = "Facility details incorrect";
        String actualMessage = ex.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testUpdateFacility_facilityDetailsAreIncorrect() {
        Facility updateFacility = new Facility();
        updateFacility.setCatalogs(new ArrayList<>());
        updateFacility.setId(123L);
        updateFacility.setName("");
        updateFacility.setPrice(34.5);

        Exception ex = assertThrows(ResponseStatusException.class, () -> facilityServiceImpl.updateFacility("Water", updateFacility));
        String expectedMessage = "Facility details incorrect";
        String actualMessage = ex.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testUpdateFacility_noFacilityFound() {
        Facility updateFacility = new Facility();
        updateFacility.setCatalogs(new ArrayList<>());
        updateFacility.setId(123L);
        updateFacility.setName("Water");
        updateFacility.setPrice(34.5);

        Exception ex = assertThrows(ResponseStatusException.class, () -> facilityServiceImpl.updateFacility("Gym", updateFacility));
        String expectedMessage = "No facility found with this facility name: Gym";
        String actualMessage = ex.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testDeleteFacility_successfullyDeletedFacility() {
        ResponseEntity<String> actualDeleteFacilityResult = facilityServiceImpl.deleteFacility("Gym");
        assertEquals("deleted facility with facility name: Gym", actualDeleteFacilityResult.getBody());
        assertEquals(HttpStatus.OK, actualDeleteFacilityResult.getStatusCode());
    }

    @Test
    void testDeleteFacility_facilityNameIsEmptyString() {
        Exception ex = assertThrows(ResponseStatusException.class, () -> facilityServiceImpl.deleteFacility(""));
        String expectedMessage = "Facility details incorrect";
        String actualMessage = ex.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}