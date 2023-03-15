package com.example.bookingsystem.controller.catalog;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.example.bookingsystem.domain.catalog.Facility;
import com.example.bookingsystem.service.catalog.FacilityService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class FacilityControllerTest {
    @InjectMocks
    private FacilityController facilityController;

    @Mock
    private FacilityService facilityService;

    @Test
    void testSaveFacility() throws Exception {
        Facility facility = new Facility();
        facility.setCatalogs(new ArrayList<>());
        facility.setId(123L);
        facility.setName("Gym");
        facility.setPrice(30.5);

        String content = (new ObjectMapper()).writeValueAsString(facility);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v2/facility/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(facilityController)
                .build()
                .perform(requestBuilder);

        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    void testUpdateFacility() throws Exception {
        Facility facility = new Facility();
        facility.setCatalogs(new ArrayList<>());
        facility.setId(123L);
        facility.setName("Gym");
        facility.setPrice(30.5);

        String content = (new ObjectMapper()).writeValueAsString(facility);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/api/v2/facility/update/{facilityName}", "Gym")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(facilityController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    void testDeleteFacility() throws Exception {
        when(this.facilityService.deleteFacility((String) any())).thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/api/v2/facility/delete/{facilityName}", "Facility Name");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.facilityController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(100));
    }

    @Test
    void testGetAllFacilities() throws Exception {
        when(this.facilityService.getAllFacility()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v2/facility/view/all");
        MockMvcBuilders.standaloneSetup(this.facilityController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testGetFacilityByName() throws Exception {
        Facility facility = new Facility();
        facility.setCatalogs(new ArrayList<>());
        facility.setId(123L);
        facility.setName("Gym");
        facility.setPrice(30.5);

        when(this.facilityService.getFacilityByFacilityName(any())).thenReturn(facility);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v2/facility/view/{facilityName}",
                "Gym");
        MockMvcBuilders.standaloneSetup(facilityController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"id\":123,\"name\":\"Gym\",\"price\":30.5}"));
    }
}

