package com.example.bookingsystem.validation.catalog;

import com.example.bookingsystem.domain.catalog.Facility;
import com.example.bookingsystem.validation.StringValidation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FacilityValidation {
    public static boolean checkFacility(Facility facility) {

        if (StringValidation.checkNullEmptyString(facility.getName())) {
            log.debug("Name: " + facility.getName());
            return true;
        }

        if (facility.getPrice() < 0.0) {
            log.debug("Price: " + facility.getPrice());
            return true;
        }

        return false;
    }
}
