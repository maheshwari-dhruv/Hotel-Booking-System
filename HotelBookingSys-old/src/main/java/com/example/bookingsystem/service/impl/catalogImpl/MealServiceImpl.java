package com.example.bookingsystem.service.impl.catalogImpl;

import com.example.bookingsystem.domain.catalog.Meal;
import com.example.bookingsystem.repository.catalog.MealRepository;
import com.example.bookingsystem.service.catalog.MealService;
import com.example.bookingsystem.validation.StringValidation;
import com.example.bookingsystem.validation.catalog.MealValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
@Slf4j
public class MealServiceImpl implements MealService {
    @Autowired
    private MealRepository mealRepository;

    @Override
    public ResponseEntity<String> saveMeal(Meal meal) {
        log.debug("Function saveMeal - meal: " + meal);

        if (MealValidation.checkMeal(meal)) {
            log.error("Meal details incorrect");
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Meal details incorrect");
        }

        log.info("saving meal to db");
        mealRepository.save(meal);
        return ResponseEntity.ok().body("Meal saved");
    }

    @Override
    public List<Meal> getAllMeal() {
        List<Meal> allMeal = mealRepository.findAll();
        log.debug("Function getAllMeal - allMeal: " + allMeal);

        if (allMeal.isEmpty()) {
            log.error("no meal found");
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "no meal found");
        }

        log.info("All meals fetched");
        return allMeal;
    }

    @Override
    public Meal getMealByMealName(String mealName) {
        log.debug("Function getMealByMealName - meal name: " + mealName);

        if (StringValidation.checkNullEmptyString(mealName)) {
            log.error("Meal details incorrect");
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Meal details incorrect");
        }

        Meal mealFound = mealRepository.findByName(mealName);
        log.debug("Meal found: " + mealFound);

        if (mealFound == null) {
            log.error("No meal found");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No meal found");
        }

        return mealFound;
    }

    @Override
    public ResponseEntity<String> updateMeal(String mealName, Meal meal) {
        log.debug("Function updateMeal - mealName: " + mealName + " | meal: " + meal);

        if (MealValidation.checkMeal(meal) || StringValidation.checkNullEmptyString(mealName)) {
            log.error("Meal details incorrect");
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Meal details incorrect");
        }

        Meal mealFound = mealRepository.findByName(mealName);
        log.debug("Meal found: " + mealFound);

        if (mealFound == null) {
            log.error("No meal found with this meal name: " + mealName);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No meal found with this meal name: " + mealName);
        }

        log.info("Updating meal");

        log.debug("Meal name: " + meal.getName());
        mealFound.setName(meal.getName());

        log.debug("Meal from time: " + meal.getMealFromTime());
        mealFound.setMealFromTime(meal.getMealFromTime());

        log.debug("Meal to time: " + meal.getMealToTime());
        mealFound.setMealToTime(meal.getMealToTime());

        mealRepository.save(mealFound);
        log.info("meal updated");
        return ResponseEntity.ok().body("Meal updated: " + mealFound);
    }

    @Override
    public ResponseEntity<String> deleteMeal(String mealName) {
        log.debug("Function deleteMeal - meal name: " + mealName);

        if (StringValidation.checkNullEmptyString(mealName)) {
            log.error("Meal details incorrect");
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Meal details incorrect");
        }

        log.info("deleting meal with meal name:" + mealName);
        mealRepository.deleteByName(mealName);
        return ResponseEntity.ok().body("deleted meal with meal name: " + mealName);
    }
}
