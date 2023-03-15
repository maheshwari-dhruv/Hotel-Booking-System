package com.example.bookingsystem.validation;

import com.example.bookingsystem.domain.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserValidationTest {

    @Test
    void testCheckUser_allValuesAreCorrect() {
        User user = new User();
        user.setId(123L);
        user.setFirstName("Dhruv");
        user.setLastName("Maheshwari");
        user.setUsername("dhruvM");
        user.setPhone("8077394176");
        user.setEmail("dhruv@gmail.com");
        user.setPassword("dhruv43");
        user.setBookings(new ArrayList<>());
        user.setRoles(new ArrayList<>());

        assertFalse(UserValidation.checkUserDetails(user));
    }

    @Test
    void testCheckUser_firstNameIsEmptyString() {
        User user = new User();
        user.setId(123L);
        user.setFirstName("");
        user.setLastName("Maheshwari");
        user.setUsername("dhruvM");
        user.setPhone("8077394176");
        user.setEmail("dhruv@gmail.com");
        user.setPassword("dhruv43");
        user.setBookings(new ArrayList<>());
        user.setRoles(new ArrayList<>());

        assertTrue(UserValidation.checkUserDetails(user));
    }

    @Test
    void testCheckUser_lastNameIsEmptyString() {
        User user = new User();
        user.setId(123L);
        user.setFirstName("Dhruv");
        user.setLastName("");
        user.setUsername("dhruvM");
        user.setPhone("8077394176");
        user.setEmail("dhruv@gmail.com");
        user.setPassword("dhruv43");
        user.setBookings(new ArrayList<>());
        user.setRoles(new ArrayList<>());

        assertTrue(UserValidation.checkUserDetails(user));
    }

    @Test
    void testCheckUser_usernameIsEmptyString() {
        User user = new User();
        user.setId(123L);
        user.setFirstName("Dhruv");
        user.setLastName("Maheshwari");
        user.setUsername("");
        user.setPhone("8077394176");
        user.setEmail("dhruv@gmail.com");
        user.setPassword("dhruv43");
        user.setBookings(new ArrayList<>());
        user.setRoles(new ArrayList<>());

        assertTrue(UserValidation.checkUserDetails(user));
    }

    @Test
    void testCheckUser_phoneNumberIsEmptyString() {
        User user = new User();
        user.setId(123L);
        user.setFirstName("Dhruv");
        user.setLastName("Maheshwari");
        user.setUsername("dhruvM");
        user.setPhone("");
        user.setEmail("dhruv@gmail.com");
        user.setPassword("dhruv43");
        user.setBookings(new ArrayList<>());
        user.setRoles(new ArrayList<>());

        assertTrue(UserValidation.checkUserDetails(user));
    }

    @Test
    void testCheckUser_phoneNumberNotMatchPattern() {
        User user = new User();
        user.setId(123L);
        user.setFirstName("Dhruv");
        user.setLastName("Maheshwari");
        user.setUsername("dhruvM");
        user.setPhone("0123456789");
        user.setEmail("dhruv@gmail.com");
        user.setPassword("dhruv43");
        user.setBookings(new ArrayList<>());
        user.setRoles(new ArrayList<>());

        assertTrue(UserValidation.checkUserDetails(user));
    }

    @Test
    void testCheckUser_emailValueIsEmptyString() {
        User user = new User();
        user.setId(123L);
        user.setFirstName("Dhruv");
        user.setLastName("Maheshwari");
        user.setUsername("dhruvM");
        user.setPhone("8077394176");
        user.setEmail("");
        user.setPassword("dhruv43");
        user.setBookings(new ArrayList<>());
        user.setRoles(new ArrayList<>());

        assertTrue(UserValidation.checkUserDetails(user));
    }

    @Test
    void testCheckUser_emailValueNotMatchPattern() {
        User user = new User();
        user.setId(123L);
        user.setFirstName("Dhruv");
        user.setLastName("Maheshwari");
        user.setUsername("dhruvM");
        user.setPhone("8077394176");
        user.setEmail("dhruv.com");
        user.setPassword("dhruv43");
        user.setBookings(new ArrayList<>());
        user.setRoles(new ArrayList<>());

        assertTrue(UserValidation.checkUserDetails(user));
    }

    @Test
    void testCheckUser_passwordValueIsEmptyString() {
        User user = new User();
        user.setId(123L);
        user.setFirstName("Dhruv");
        user.setLastName("Maheshwari");
        user.setUsername("dhruvM");
        user.setPhone("8077394176");
        user.setEmail("dhruv@gmail.com");
        user.setPassword("");
        user.setBookings(new ArrayList<>());
        user.setRoles(new ArrayList<>());

        assertTrue(UserValidation.checkUserDetails(user));
    }
}
