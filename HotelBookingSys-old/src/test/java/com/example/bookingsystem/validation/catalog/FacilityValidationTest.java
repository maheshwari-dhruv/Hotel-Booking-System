package com.example.bookingsystem.validation.catalog;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.bookingsystem.domain.Catalog;
import com.example.bookingsystem.domain.catalog.Facility;

import java.util.*;

import org.junit.jupiter.api.Test;

class FacilityValidationTest {

    @Test
    void testCheckFacility_allValuesAreCorrect() {
        Facility facility = new Facility();
        facility.setCatalogs(new ArrayList<Catalog>());
        facility.setId(123L);
        facility.setName("Dhruv");
        facility.setPrice(30.5);
        assertFalse(FacilityValidation.checkFacility(facility));
    }

    @Test
    void testCheckFacility_nameValueIsEmptyString() {
        Facility facility = new Facility();
        facility.setCatalogs(new ArrayList<>());
        facility.setId(123L);
        facility.setName("");
        facility.setPrice(30.5);
        assertTrue(FacilityValidation.checkFacility(facility));
    }

    @Test
    void testCheckFacility_priceValueIsLessThanZero() {
        Facility facility = new Facility();
        facility.setCatalogs(new ArrayList<>());
        facility.setId(123L);
        facility.setName("Dhruv");
        facility.setPrice(-30.5);
        assertTrue(FacilityValidation.checkFacility(facility));
    }
}

