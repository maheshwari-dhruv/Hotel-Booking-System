package com.example.bookingsystem.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.example.bookingsystem.domain.Guest;
import com.example.bookingsystem.service.GuestService;
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
class GuestControllerTest {
    @InjectMocks
    private GuestController guestController;

    @Mock
    private GuestService guestService;

    @Test
    void testSaveGuest() throws Exception {
        Guest guest = new Guest();
        guest.setAge(21);
        guest.setBookings(new ArrayList<>());
        guest.setFirstName("Dhruv");
        guest.setId(123L);
        guest.setLastName("Maheshwari");

        String content = (new ObjectMapper()).writeValueAsString(guest);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v2/guest/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(guestController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    void testGetGuestById() throws Exception {
        Guest guest = new Guest();
        guest.setAge(21);
        guest.setBookings(new ArrayList<>());
        guest.setFirstName("Dhruv");
        guest.setId(123L);
        guest.setLastName("Maheshwari");

        when(guestService.getGuestById(any())).thenReturn(guest);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v2/guest/view/{guestId}", 123L);
        MockMvcBuilders.standaloneSetup(this.guestController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"id\":123,\"firstName\":\"Dhruv\",\"lastName\":\"Maheshwari\",\"age\":21}"));
    }

    @Test
    void testDeleteGuestById() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/v2/guest/delete/{guestId}",
                123L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(guestController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    void testGetGuests() throws Exception {
        when(guestService.getAllGuests()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v2/guest/view/all");
        MockMvcBuilders.standaloneSetup(guestController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testUpdateGuestById() throws Exception {
        Guest guest = new Guest();
        guest.setAge(21);
        guest.setBookings(new ArrayList<>());
        guest.setFirstName("Dhruv");
        guest.setId(123L);
        guest.setLastName("Maheshwari");
        when(guestService.updateGuest(any(), any())).thenReturn(guest);

        Guest guestFound = new Guest();
        guestFound.setAge(21);
        guestFound.setBookings(new ArrayList<>());
        guestFound.setFirstName("Kartik");
        guestFound.setId(123L);
        guestFound.setLastName("Maheshwari");

        String content = (new ObjectMapper()).writeValueAsString(guestFound);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/v2/guest/update/{guestId}", 123L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(this.guestController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"id\":123,\"firstName\":\"Dhruv\",\"lastName\":\"Maheshwari\",\"age\":21}"));
    }
}

