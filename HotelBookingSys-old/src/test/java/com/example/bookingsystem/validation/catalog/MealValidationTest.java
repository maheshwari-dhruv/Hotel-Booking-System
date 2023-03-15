package com.example.bookingsystem.validation.catalog;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.bookingsystem.domain.catalog.Meal;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class MealValidationTest {

    @Test
    void testCheckMeal_allValuesAreCorrect() {
        Meal meal = new Meal();
        meal.setCatalogs(new ArrayList<>());
        meal.setId(123L);
        meal.setMealFromTime("9:00");
        meal.setMealToTime("10:00");
        meal.setName("Breakfast");
        assertFalse(MealValidation.checkMeal(meal));
    }

    @Test
    void testCheckMeal_nameValueIsEmptyString() {
        Meal meal = new Meal();
        meal.setCatalogs(new ArrayList<>());
        meal.setId(123L);
        meal.setMealFromTime("9:00");
        meal.setMealToTime("10:00");
        meal.setName("");
        assertTrue(MealValidation.checkMeal(meal));
    }

    @Test
    void testCheckMeal_mealToTimeValueIsEmptyString() {
        Meal meal = new Meal();
        meal.setCatalogs(new ArrayList<>());
        meal.setId(123L);
        meal.setMealFromTime("9:00");
        meal.setMealToTime("");
        meal.setName("Breakfast");
        assertTrue(MealValidation.checkMeal(meal));
    }

    @Test
    void testCheckMeal_mealFromTimeValueIsEmptyString() {
        Meal meal = new Meal();
        meal.setCatalogs(new ArrayList<>());
        meal.setId(123L);
        meal.setMealFromTime("");
        meal.setMealToTime("10:00");
        meal.setName("Breakfast");
        assertTrue(MealValidation.checkMeal(meal));
    }
}

