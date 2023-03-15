package com.example.bookingsystem.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import com.example.bookingsystem.domain.Role;
import com.example.bookingsystem.domain.User;
import com.example.bookingsystem.repository.RoleRepository;
import com.example.bookingsystem.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
class RoleServiceImplTest {
    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImpl roleServiceImpl;

    @Test
    void testSaveRole_successfullySaved() {
        Role role = new Role();
        role.setId(123L);
        role.setName("staff");

        ResponseEntity<String> actualSaveRoleResult = roleServiceImpl.saveRole(role);
        assertEquals("Role saved in db", actualSaveRoleResult.getBody());
        assertEquals(HttpStatus.OK, actualSaveRoleResult.getStatusCode());
        verify(roleRepository, times(1)).save((Role) any());
    }

    @Test
    void testSaveRole_roleDetailsIncorrect() {
        Role role = new Role();
        role.setId(123L);
        role.setName("");

        Exception ex = assertThrows(ResponseStatusException.class, () -> roleServiceImpl.saveRole(role));
        String expectedMessage = "Role details incorrect";
        String actualMessage = ex.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(roleRepository, times(0)).save(any());
    }

    @Test
    void testGetAllRoles_successfullyFetchedAllRoles() {
        Role role = new Role();
        role.setId(123L);
        role.setName("staff");

        ArrayList<Role> roleList = new ArrayList<>();
        roleList.add(role);
        when(roleRepository.findAll()).thenReturn(roleList);

        List<Role> actualAllRoles = roleServiceImpl.getAllRoles();

        assertSame(roleList, actualAllRoles);
        assertEquals(1, actualAllRoles.size());
        verify(roleRepository, times(1)).findAll();
    }

    @Test
    void testGetAllRoles_noRoleFound() {
        when(this.roleRepository.findAll()).thenReturn(new ArrayList<>());

        Exception ex = assertThrows(ResponseStatusException.class, () -> roleServiceImpl.getAllRoles());
        String expectedMessage = "No role found in db";
        String actualMessage = ex.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(roleRepository, times(0)).save(any());
    }

    @Test
    void testGetAllUsersByRole2() {
        Exception ex = assertThrows(ResponseStatusException.class, () -> roleServiceImpl.getAllUsersByRole(""));
        String expectedMessage = "role name can't be null or empty";
        String actualMessage = ex.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(roleRepository, times(0)).findAll();
    }
}

