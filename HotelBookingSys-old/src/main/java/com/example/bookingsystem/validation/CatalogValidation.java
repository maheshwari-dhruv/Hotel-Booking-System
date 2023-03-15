package com.example.bookingsystem.validation;

import com.example.bookingsystem.domain.Catalog;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CatalogValidation {

    public static boolean checkCatalog(Catalog catalog) {

        if (catalog.getCatalogFacilities().isEmpty()) {
            log.debug("Catalog Facilities: " + catalog.getCatalogFacilities());
            return true;
        }

        if (catalog.getCatalogMeals().isEmpty()) {
            log.debug("Catalog Meals: " + catalog.getCatalogMeals());
            return true;
        }

        log.info("No error found in catalog details");
        return false;
    }
}
