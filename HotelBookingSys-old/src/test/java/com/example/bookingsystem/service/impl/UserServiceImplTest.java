package com.example.bookingsystem.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import com.example.bookingsystem.domain.Role;
import com.example.bookingsystem.domain.User;
import com.example.bookingsystem.repository.RoleRepository;
import com.example.bookingsystem.repository.UserRepository;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Test
    void testSaveUser_userDetailsIncorrect() {
        User user = new User();
        user.setId(123L);
        user.setFirstName("Dhruv");
        user.setLastName("Maheshwari");
        user.setEmail("dhruv@gmail.com");
        user.setPassword("dhruv");
        user.setUsername("");
        user.setBookings(new ArrayList<>());
        user.setPhone("7079763445");

        Exception ex = assertThrows(ResponseStatusException.class, () -> userServiceImpl.saveUser(user, "staff"));
        String expectedMessage = "User details incorrect";
        String actualMessage = ex.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(userRepository, times(0)).save(any());
    }

    @Test
    void testAddRoleToUser_addingRoleToUserSuccessfully() {
        User user = new User();
        user.setId(123L);
        user.setFirstName("Dhruv");
        user.setLastName("Maheshwari");
        user.setUsername("dhruvM");
        user.setEmail("dhruv@gmail.com");
        user.setPassword("dhruv");
        user.setPhone("8077394176");
        user.setBookings(new ArrayList<>());
        user.setRoles(new ArrayList<>());
        when(userRepository.findByUsername(any())).thenReturn(user);

        Role role = new Role();
        role.setId(123L);
        role.setName("staff");
        when(roleRepository.findByName(any())).thenReturn(role);

        userServiceImpl.addRoleToUser("dhruvM", "staff");

        assertEquals("staff", userServiceImpl.getUser("dhruvM").getRoles().get(0).getName());
    }

    @Test
    void testGetUser() {
        User user = new User();
        user.setId(123L);
        user.setFirstName("Dhruv");
        user.setLastName("Maheshwari");
        user.setUsername("dhruvM");
        user.setEmail("dhruv@gmail.com");
        user.setPassword("dhruv");
        user.setPhone("8077394176");
        user.setBookings(new ArrayList<>());
        user.setRoles(new ArrayList<>());
        when(userRepository.findByUsername(any())).thenReturn(user);

        assertSame(user, userServiceImpl.getUser("dhruvM"));
        verify(userRepository).findByUsername(any());
    }

    @Test
    void testDeleteUser() {
        ResponseEntity<String> actualDeleteUserResult = userServiceImpl.deleteUser("dhruvM");
        assertEquals("username: dhruvM deleted successfully", actualDeleteUserResult.getBody());
        assertEquals(HttpStatus.OK, actualDeleteUserResult.getStatusCode());
        verify(userRepository, times(1)).deleteByUsername(any());
    }
}

