package com.example.bookingsystem.validation;

import com.example.bookingsystem.domain.Guest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GuestValidation {
    public static boolean checkGuest(Guest guest) {

        if (StringValidation.checkNullEmptyString(guest.getFirstName())) {
            log.debug("FirstName: " + guest.getFirstName());
            return true;
        }

        if (StringValidation.checkNullEmptyString(guest.getLastName())) {
            log.debug("LastName: " + guest.getLastName());
            return true;
        }

        if (guest.getAge() < 1) {
            log.debug("Age: " + guest.getAge());
            return true;
        }

        log.info("No error in guest details");
        return false;
    }
}
