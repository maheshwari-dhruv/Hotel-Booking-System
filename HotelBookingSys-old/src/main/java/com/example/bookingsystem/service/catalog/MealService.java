package com.example.bookingsystem.service.catalog;

import com.example.bookingsystem.domain.catalog.Meal;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MealService {
    ResponseEntity<String> saveMeal(Meal meal);

    List<Meal> getAllMeal();

    Meal getMealByMealName(String mealName);

    ResponseEntity<String> updateMeal(String mealName, Meal meal);
    ResponseEntity<String> deleteMeal(String mealName);
}
