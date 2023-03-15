package com.example.bookingsystem.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.example.bookingsystem.domain.User;
import com.example.bookingsystem.domain.jwt.JwtAuthRequest;
import com.example.bookingsystem.service.UserService;
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
class UserControllerTest {
    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Test
    void testSaveUser_successfullySaved() throws Exception {
        User user = new User();
        user.setBookings(new ArrayList<>());
        user.setEmail("dhruv@gmail.com");
        user.setFirstName("dhruv");
        user.setId(123L);
        user.setLastName("maheshwari");
        user.setPassword("helo123");
        user.setPhone("8077394176");
        user.setRoles(new ArrayList<>());
        user.setUsername("dhruvM");

        String content = (new ObjectMapper()).writeValueAsString(user);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v2/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder);

        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    void testDeleteUserByUsername_successfullyDeletedUserByUsername() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/v2/user/delete/{username}",
                "dhruvM");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    void testGetUsers_emptyUserList() throws Exception {
        when(userService.getUsers()).thenReturn(new ArrayList<>());

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v2/user/view/all");
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testGetUserByUsername() throws Exception {
        User user = new User();
        user.setBookings(new ArrayList<>());
        user.setEmail("dhruv@gmail.com");
        user.setFirstName("dhruv");
        user.setId(123L);
        user.setLastName("maheshwari");
        user.setPassword("helo123");
        user.setPhone("8077394176");
        user.setRoles(new ArrayList<>());
        user.setUsername("dhruvM");

        when(userService.getUser(any())).thenReturn(user);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v2/user/view/{username}",
                "dhruvM");
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":123,\"firstName\":\"dhruv\",\"lastName\":\"maheshwari\",\"username\":\"dhruvM\",\"password\":\"helo123\",\"email\":\"dhruv@gmail.com\",\"phone\":\"8077394176\",\"roles\":[]}"));
    }

    @Test
    void testLoginUser() throws Exception {
        JwtAuthRequest jwtAuthRequest = new JwtAuthRequest();
        jwtAuthRequest.setPassword("helo123");
        jwtAuthRequest.setUsername("dhruvM");
        String content = (new ObjectMapper()).writeValueAsString(jwtAuthRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v2/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    void testSaveStaffUser() throws Exception {
        User user = new User();
        user.setBookings(new ArrayList<>());
        user.setEmail("dhruv@gmail.com");
        user.setFirstName("dhruv");
        user.setId(123L);
        user.setLastName("maheshwari");
        user.setPassword("helo123");
        user.setPhone("8077394176");
        user.setRoles(new ArrayList<>());
        user.setUsername("dhruvM");

        String content = (new ObjectMapper()).writeValueAsString(user);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v2/user/register/staff")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    void testUpdateUserByUsername() throws Exception {
        User user = new User();
        user.setBookings(new ArrayList<>());
        user.setEmail("dhruv@gmail.com");
        user.setFirstName("dhruv");
        user.setId(123L);
        user.setLastName("maheshwari");
        user.setPassword("helo123");
        user.setPhone("8077394176");
        user.setRoles(new ArrayList<>());
        user.setUsername("dhruvM");
        when(userService.updateUser(any(), any())).thenReturn(user);

        User userToUpdate = new User();
        userToUpdate.setBookings(new ArrayList<>());
        userToUpdate.setEmail("dhruv@gmail.com");
        userToUpdate.setFirstName("dhruv");
        userToUpdate.setId(123L);
        userToUpdate.setLastName("maheshwari");
        userToUpdate.setPassword("helo123");
        userToUpdate.setPhone("9098979699");
        userToUpdate.setRoles(new ArrayList<>());
        userToUpdate.setUsername("dhruvM");
        String content = (new ObjectMapper()).writeValueAsString(userToUpdate);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/api/v2/user/update/{username}", "dhruvM")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":123,\"firstName\":\"dhruv\",\"lastName\":\"maheshwari\",\"username\":\"dhruvM\",\"password\":\"helo123\",\"email\":\"dhruv@gmail.com\",\"phone\":\"8077394176\",\"roles\":[]}"));
    }
}

