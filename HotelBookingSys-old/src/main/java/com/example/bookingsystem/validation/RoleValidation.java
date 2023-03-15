package com.example.bookingsystem.validation;

import com.example.bookingsystem.domain.Role;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RoleValidation {

    public static boolean checkRole(Role role) {

        if (StringValidation.checkNullEmptyString(role.getName())) {
            log.debug("role name: " + role.getName());
            return true;
        }

        log.info("No error found in role details");
        return false;
    }
}
