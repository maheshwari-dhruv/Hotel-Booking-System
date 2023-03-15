package com.example.bookingsystem.validation;

import com.example.bookingsystem.domain.Role;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RoleValidationTest {

    @Test
    void testCheckRole_allValuesAreCorrect() {
        Role role = new Role();
        role.setId(123L);
        role.setName("Customer");

        assertFalse(RoleValidation.checkRole(role));
    }

    @Test
    void testCheckRole_roleNameValueIsEmptyString() {
        Role role = new Role();
        role.setId(123L);
        role.setName("");

        assertTrue(RoleValidation.checkRole(role));
    }
}
