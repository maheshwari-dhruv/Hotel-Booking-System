package com.example.bookingsystem.repository.catalog;

import com.example.bookingsystem.domain.catalog.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {
    Meal findByName(String mealName);
    void deleteByName(String mealName);
}
