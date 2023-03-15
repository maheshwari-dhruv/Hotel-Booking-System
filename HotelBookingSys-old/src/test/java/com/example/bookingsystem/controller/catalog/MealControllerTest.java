package com.example.bookingsystem.controller.catalog;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.example.bookingsystem.domain.catalog.Meal;
import com.example.bookingsystem.service.catalog.MealService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class MealControllerTest {
    @InjectMocks
    private MealController mealController;

    @Mock
    private MealService mealService;

    @Test
    void testSaveMeal() throws Exception {
        Meal meal = new Meal();
        meal.setCatalogs(new ArrayList<>());
        meal.setId(123L);
        meal.setMealFromTime("9:00");
        meal.setMealToTime("10:00");
        meal.setName("Breakfast");

        String content = (new ObjectMapper()).writeValueAsString(meal);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v2/meal/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(mealController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    void testGetAllMeal() throws Exception {
        when(mealService.getAllMeal()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v2/meal/view/all");
        MockMvcBuilders.standaloneSetup(this.mealController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testUpdateMeal() throws Exception {
        Meal meal = new Meal();
        meal.setCatalogs(new ArrayList<>());
        meal.setId(123L);
        meal.setMealFromTime("9:00");
        meal.setMealToTime("10:00");
        meal.setName("Breakfast");

        String content = (new ObjectMapper()).writeValueAsString(meal);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/api/v2/meal/update/{mealName}", "Breakfast")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(mealController)
                .build()
                .perform(requestBuilder);

        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    void testDeleteMeal() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/v2/meal/delete/{mealName}",
                "Breakfast");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(mealController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    void testGetMealByName() throws Exception {
        Meal meal = new Meal();
        meal.setCatalogs(new ArrayList<>());
        meal.setId(123L);
        meal.setMealFromTime("9:00");
        meal.setMealToTime("10:00");
        meal.setName("Breakfast");

        when(mealService.getMealByMealName(any())).thenReturn(meal);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v2/meal/view/{mealName}",
                "Breakfast");
        MockMvcBuilders.standaloneSetup(mealController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":123,\"name\":\"Breakfast\",\"mealFromTime\":\"9:00\",\"mealToTime\":\"10:00\"}"));
    }
}

