package com.example.bookingsystem.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.example.bookingsystem.domain.Role;
import com.example.bookingsystem.service.RoleService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

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
class RoleControllerTest {
    @InjectMocks
    private RoleController roleController;

    @Mock
    private RoleService roleService;

    @Test
    void testSaveRole() throws Exception {
        Role role = new Role();
        role.setId(123L);
        role.setName("staff");

        String content = (new ObjectMapper()).writeValueAsString(role);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v2/role/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.roleController)
                .build()
                .perform(requestBuilder);

        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    void testGetAllUsersByRole() throws Exception {
        when(roleService.getAllUsersByRole(any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v2/role/view/users/{roleName}",
                "staff");
        MockMvcBuilders.standaloneSetup(roleController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testGetRoles() throws Exception {
        when(roleService.getAllRoles()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v2/role/view/all");
        MockMvcBuilders.standaloneSetup(roleController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }
}

