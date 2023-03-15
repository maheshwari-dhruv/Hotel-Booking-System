package com.example.bookingsystem.validation.catalog;

import com.example.bookingsystem.domain.catalog.Meal;
import com.example.bookingsystem.validation.StringValidation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MealValidation {
    public static boolean checkMeal(Meal meal) {

        if (StringValidation.checkNullEmptyString(meal.getName())) {
            log.debug("Name: " + meal.getName());
            return true;
        }

        if (StringValidation.checkNullEmptyString(meal.getMealToTime())) {
            log.debug("Meal to time: " + meal.getMealToTime());
            return true;
        }

        if (StringValidation.checkNullEmptyString(meal.getMealFromTime())) {
            log.debug("Meal from time: " + meal.getMealFromTime());
            return true;
        }

        return false;
    }
}
