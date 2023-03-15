package com.example.bookingsystem.controller;

import static org.mockito.Mockito.when;

import com.example.bookingsystem.service.CatalogService;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class CatalogControllerTest {
    @InjectMocks
    private CatalogController catalogController;

    @Mock
    private CatalogService catalogService;

    @Test
    void testSaveCatalog() throws Exception {
        List<String> facilityName = new ArrayList<>();
        List<String> mealName = new ArrayList<>();

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v2/catalog/save/facilities={facilityNames}/meals={mealsName}", facilityName, mealName)
                .contentType(MediaType.APPLICATION_JSON);

        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(catalogController)
                .build()
                .perform(requestBuilder);

        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    void testGetAllCatalog() throws Exception {
        when(this.catalogService.getAllCatalog()).thenReturn(new ArrayList<>());

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v2/catalog/view/all");
        MockMvcBuilders.standaloneSetup(this.catalogController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }
}

