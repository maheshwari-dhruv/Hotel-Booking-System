package com.example.bookingsystem.controller.catalog;

import com.example.bookingsystem.domain.catalog.Meal;
import com.example.bookingsystem.service.catalog.MealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/meal")
public class MealController {

    @Autowired
    private MealService mealService;

    @PreAuthorize("hasAuthority('staff')")
    @PostMapping("/create")
    public ResponseEntity<String> saveMeal(@RequestBody Meal meal) {
        return mealService.saveMeal(meal);
    }

    @PreAuthorize("hasAnyAuthority('staff', 'customer')")
    @GetMapping("/view/all")
    public ResponseEntity<List<Meal>> getAllMeal() {
        return ResponseEntity.ok().body(mealService.getAllMeal());
    }

    @PreAuthorize("hasAnyAuthority('staff', 'customer')")
    @GetMapping("/view/{mealName}")
    public ResponseEntity<Meal> getMealByName(@PathVariable String mealName) {
        return ResponseEntity.ok().body(mealService.getMealByMealName(mealName));
    }

    @PreAuthorize("hasAuthority('staff')")
    @PutMapping("/update/{mealName}")
    public ResponseEntity<String> updateMeal(@PathVariable String mealName, @RequestBody Meal meal) {
        return mealService.updateMeal(mealName, meal);
    }

    @PreAuthorize("hasAuthority('staff')")
    @DeleteMapping("/delete/{mealName}")
    public ResponseEntity<String> deleteMeal(@PathVariable String mealName) {
        return mealService.deleteMeal(mealName);
    }
}
