package com.example.bookingsystem.service.impl.catalogImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import com.example.bookingsystem.domain.catalog.Meal;
import com.example.bookingsystem.repository.catalog.MealRepository;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
class MealServiceImplTest {
    @Mock
    private MealRepository mealRepository;

    @InjectMocks
    private MealServiceImpl mealServiceImpl;

    @Test
    void testSaveMeal_successfullySaved() {
        Meal meal = new Meal();
        meal.setCatalogs(new ArrayList<>());
        meal.setId(123L);
        meal.setMealFromTime("9:00");
        meal.setMealToTime("10:00");
        meal.setName("Breakfast");

        ResponseEntity<String> actualSaveMealResult = mealServiceImpl.saveMeal(meal);
        assertEquals("Meal saved", actualSaveMealResult.getBody());
        assertEquals(HttpStatus.OK, actualSaveMealResult.getStatusCode());

        verify(mealRepository, times(1)).save((Meal) any());
    }

    @Test
    void testSaveMeal_mealDetailsIncorrect() {
        Meal meal = new Meal();
        meal.setCatalogs(new ArrayList<>());
        meal.setId(123L);
        meal.setMealFromTime("9:00");
        meal.setMealToTime("10:00");
        meal.setName("");

        Exception ex = assertThrows(ResponseStatusException.class, () -> mealServiceImpl.saveMeal(meal));
        String expectedMessage = "Meal details incorrect";
        String actualMessage = ex.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(mealRepository, times(0)).save(any());
    }

    @Test
    void testGetAllMeal_successfullyFetchedAllMeals() {
        Meal meal = new Meal();
        meal.setCatalogs(new ArrayList<>());
        meal.setId(123L);
        meal.setMealFromTime("9:00");
        meal.setMealToTime("10:00");
        meal.setName("Breakfast");

        ArrayList<Meal> mealList = new ArrayList<>();
        mealList.add(meal);
        when(this.mealRepository.findAll()).thenReturn(mealList);

        List<Meal> actualAllMeal = this.mealServiceImpl.getAllMeal();

        assertSame(mealList, actualAllMeal);
        assertEquals(1, actualAllMeal.size());
        verify(mealRepository, times(1)).findAll();
    }

    @Test
    void testGetAllMeal_mealListIsEmpty() {
        when(mealRepository.findAll()).thenReturn(new ArrayList<>());

        Exception ex = assertThrows(ResponseStatusException.class, () -> mealServiceImpl.getAllMeal());
        String expectedMessage = "no meal found";
        String actualMessage = ex.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(mealRepository, times(0)).save(any());
    }

    @Test
    void testGetMealByMealName_successfullyFetchedMealByMealName() {
        Meal meal = new Meal();
        meal.setCatalogs(new ArrayList<>());
        meal.setId(123L);
        meal.setMealFromTime("9:00");
        meal.setMealToTime("10:00");
        meal.setName("Breakfast");

        when(this.mealRepository.findByName(any())).thenReturn(meal);

        assertSame(meal, mealServiceImpl.getMealByMealName("Breakfast"));
        verify(this.mealRepository).findByName(any());
    }

    @Test
    void testGetMealByMealName_mealNameValueIsEmptyString() {
        Exception ex = assertThrows(ResponseStatusException.class, () -> mealServiceImpl.getMealByMealName(""));
        String expectedMessage = "Meal details incorrect";
        String actualMessage = ex.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testGetMealByMealName_noMealFound() {
        Exception ex = assertThrows(ResponseStatusException.class, () -> mealServiceImpl.getMealByMealName("Lunch"));
        String expectedMessage = "No meal found";
        String actualMessage = ex.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testUpdateMeal() {
        Meal meal = new Meal();
        meal.setCatalogs(new ArrayList<>());
        meal.setId(123L);
        meal.setMealFromTime("9:00");
        meal.setMealToTime("10:00");
        meal.setName("Breakfast");
        when(mealRepository.findByName(any())).thenReturn(meal);

        Meal updateMeal = new Meal();
        updateMeal.setCatalogs(new ArrayList<>());
        updateMeal.setId(123L);
        updateMeal.setMealFromTime("8:30");
        updateMeal.setMealToTime("10:30");
        updateMeal.setName("Breakfast");

        ResponseEntity<String> actualUpdateMealResult = mealServiceImpl.updateMeal("Breakfast", updateMeal);
        assertEquals("Meal updated: Meal(id=123, name=Breakfast, mealFromTime=8:30, mealToTime=10:30)", actualUpdateMealResult.getBody());
        assertEquals(HttpStatus.OK, actualUpdateMealResult.getStatusCode());
        verify(this.mealRepository).findByName((String) any());
    }

    @Test
    void testUpdateMeal_mealNameIsEmptyString() {
        Meal updateMeal = new Meal();
        updateMeal.setCatalogs(new ArrayList<>());
        updateMeal.setId(123L);
        updateMeal.setMealFromTime("8:30");
        updateMeal.setMealToTime("10:30");
        updateMeal.setName("Breakfast");

        Exception ex = assertThrows(ResponseStatusException.class, () -> mealServiceImpl.updateMeal("", updateMeal));
        String expectedMessage = "Meal details incorrect";
        String actualMessage = ex.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testUpdateMeal_mealDetailsAreIncorrect() {
        Meal updateMeal = new Meal();
        updateMeal.setCatalogs(new ArrayList<>());
        updateMeal.setId(123L);
        updateMeal.setMealFromTime("8:30");
        updateMeal.setMealToTime("10:30");
        updateMeal.setName("");

        Exception ex = assertThrows(ResponseStatusException.class, () -> mealServiceImpl.updateMeal("Breakfast", updateMeal));
        String expectedMessage = "Meal details incorrect";
        String actualMessage = ex.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testUpdateMeal_noMealFound() {
        Meal updateMeal = new Meal();
        updateMeal.setCatalogs(new ArrayList<>());
        updateMeal.setId(123L);
        updateMeal.setMealFromTime("8:30");
        updateMeal.setMealToTime("10:30");
        updateMeal.setName("Lunch");

        Exception ex = assertThrows(ResponseStatusException.class, () -> mealServiceImpl.updateMeal("Breakfast", updateMeal));
        String expectedMessage = "No meal found with this meal name: Breakfast";
        String actualMessage = ex.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testDeleteMeal_successfullyDeletedMeal() {
        ResponseEntity<String> actualDeleteMealResult = mealServiceImpl.deleteMeal("Breakfast");
        assertEquals("deleted meal with meal name: Breakfast", actualDeleteMealResult.getBody());
        assertEquals(HttpStatus.OK, actualDeleteMealResult.getStatusCode());
    }

    @Test
    void testDeleteMeal_mealNameIsEmptyString() {
        Exception ex = assertThrows(ResponseStatusException.class, () -> mealServiceImpl.deleteMeal(""));
        String expectedMessage = "Meal details incorrect";
        String actualMessage = ex.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}