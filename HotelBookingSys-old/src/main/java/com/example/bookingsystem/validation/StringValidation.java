package com.example.bookingsystem.validation;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StringValidation {
    public static boolean checkNullEmptyString(String str) {
        if (str.isEmpty() || str.equals("null")) {
            log.debug("str value: " + str);
            return true;
        }

        return false;
    }
}
