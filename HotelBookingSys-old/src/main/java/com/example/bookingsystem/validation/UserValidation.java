package com.example.bookingsystem.validation;

import com.example.bookingsystem.domain.User;
import lombok.extern.slf4j.Slf4j;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class UserValidation {
    static String regexEmail = "^(.+)@(.+)$";
    static String regexPhone = "(0/91)?[7-9][0-9]{9}";

    static Pattern pattern = Pattern.compile(regexEmail);

    public static boolean checkUserDetails(User user) {
        Matcher matcher = pattern.matcher(user.getEmail());

        if (StringValidation.checkNullEmptyString(user.getUsername())) {
            log.debug("username: " + user.getUsername());
            return true;
        }

        if (StringValidation.checkNullEmptyString(user.getPassword())) {
            log.debug("password: " + user.getPassword());
            return true;
        }

        if (StringValidation.checkNullEmptyString(user.getEmail()) || !(matcher.matches())) {
            log.debug("email: " + user.getEmail());
            return true;
        }

        if (StringValidation.checkNullEmptyString(user.getFirstName())) {
            log.debug("firstname: " + user.getFirstName());
            return true;
        }

        if (StringValidation.checkNullEmptyString(user.getPhone()) || !(user.getPhone().matches(regexPhone))) {
            log.debug("phone: " + user.getPhone());
            return true;
        }

        if (StringValidation.checkNullEmptyString(user.getLastName())) {
            log.debug("lastname: " + user.getLastName());
            return true;
        }

//        if (user.getRoles().isEmpty()) {
//            log.debug("roles: " + user.getRoles());
//            return true;
//        }

        log.info("No error found in user details");
        return false;
    }
}
