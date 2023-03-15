package com.example.bookingsystem.service.impl;

import com.example.bookingsystem.domain.Catalog;
import com.example.bookingsystem.domain.catalog.Facility;
import com.example.bookingsystem.domain.catalog.Meal;
import com.example.bookingsystem.repository.CatalogRepository;
import com.example.bookingsystem.service.CatalogService;
import com.example.bookingsystem.service.catalog.FacilityService;
import com.example.bookingsystem.service.catalog.MealService;
import com.example.bookingsystem.validation.catalog.FacilityValidation;
import com.example.bookingsystem.validation.catalog.MealValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
@Transactional
@Slf4j
public class CatalogServiceImpl implements CatalogService {
    @Autowired
    private CatalogRepository catalogRepository;

    @Autowired
    private FacilityService facilityService;

    @Autowired
    private MealService mealService;

    @Override
    public Catalog saveCatalog(List<String> facilitiesName, List<String> mealNames) {
        log.debug("Function saveCatalog - facilitiesName: " + facilitiesName + " | mealName: " + mealNames);

        Catalog catalog = new Catalog();

        if (facilitiesName.isEmpty() || mealNames.isEmpty()) {
            log.error("entered details are either null or empty");
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "entered details are either null or empty");
        }

        log.info("saving catalog in db");

        log.debug("Calling addFacilitiesToCatalog - facilities: " + addFacilitiesToCatalog(facilitiesName));
        catalog.setCatalogFacilities(addFacilitiesToCatalog(facilitiesName));

        log.debug("Calling addMealsToCatalog - meal plan: " + addMealsToCatalog(mealNames));
        catalog.setCatalogMeals(addMealsToCatalog(mealNames));

        log.debug("Catalog: " + catalog);
        log.info("Catalog saved in db");
        return catalogRepository.save(catalog);
    }

    private List<Meal> addMealsToCatalog(List<String> mealNames) {
        List<Meal> meals = new ArrayList<Meal>();

        mealNames.forEach(mealName -> {
            Meal meal = mealService.getMealByMealName(mealName);

            if (MealValidation.checkMeal(meal)) {
                log.error("Error in meal details");
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "error in meal details: " + meal);
            }

            meals.add(meal);
        });

        return meals;
    }

    private List<Facility> addFacilitiesToCatalog(List<String> facilitiesName) {
        List<Facility> facilities = new ArrayList<Facility>();

        facilitiesName.forEach(facilityName -> {
            Facility facility = facilityService.getFacilityByFacilityName(facilityName);

            if (FacilityValidation.checkFacility(facility)) {
                log.error("Error in facility details");
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "error in facility details: " + facility);
            }

            facilities.add(facility);
        });

        return facilities;
    }

    @Override
    public List<Catalog> getAllCatalog() {
        List<Catalog> catalogs = catalogRepository.findAll();
        log.debug("Function getAllCatalogs - catalogs: " + catalogs);

        if (catalogs.isEmpty()) {
            log.error("No catalog found");
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No catalog found");
        }

        log.info("All catalogs fetched");
        return catalogs;
    }
}
