package com.example.bookingsystem.validation;

import com.example.bookingsystem.domain.Booking;
import com.example.bookingsystem.domain.Catalog;
import com.example.bookingsystem.domain.catalog.Facility;
import com.example.bookingsystem.domain.catalog.Meal;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CatalogValidationTest {

    @Test
    void testCheckCatalog_allValuesAreCorrect() {
        Facility facility = new Facility();
        facility.setId(123L);
        facility.setName("Gym");
        facility.setPrice(12.4);

        List<Facility> facilityList = new ArrayList<>();
        facilityList.add(facility);

        Meal meal = new Meal();
        meal.setId(123L);
        meal.setName("Lunch");
        meal.setMealFromTime("9:00");
        meal.setMealToTime("10:00");

        List<Meal> mealList = new ArrayList<>();
        mealList.add(meal);

        Catalog catalog = new Catalog();
        catalog.setId(123L);
        catalog.setCatalogFacilities(facilityList);
        catalog.setCatalogMeals(mealList);
        catalog.setBooking(new Booking());

        assertFalse(CatalogValidation.checkCatalog(catalog));
    }

    @Test
    void testCheckCatalog_facilityListIsEmpty() {
        Meal meal = new Meal();
        meal.setId(123L);
        meal.setName("Lunch");
        meal.setMealFromTime("9:00");
        meal.setMealToTime("10:00");

        List<Meal> mealList = new ArrayList<>();
        mealList.add(meal);

        Catalog catalog = new Catalog();
        catalog.setId(123L);
        catalog.setCatalogFacilities(new ArrayList<>());
        catalog.setCatalogMeals(mealList);
        catalog.setBooking(new Booking());

        assertTrue(CatalogValidation.checkCatalog(catalog));
    }

    @Test
    void testCheckCatalog_mealListIsEmpty() {
        Facility facility = new Facility();
        facility.setId(123L);
        facility.setName("Gym");
        facility.setPrice(12.4);

        List<Facility> facilityList = new ArrayList<>();
        facilityList.add(facility);

        Catalog catalog = new Catalog();
        catalog.setId(123L);
        catalog.setCatalogFacilities(facilityList);
        catalog.setCatalogMeals(new ArrayList<>());
        catalog.setBooking(new Booking());

        assertTrue(CatalogValidation.checkCatalog(catalog));
    }
}
