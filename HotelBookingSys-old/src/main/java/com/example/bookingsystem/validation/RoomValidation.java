package com.example.bookingsystem.validation;

import com.example.bookingsystem.domain.Room;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RoomValidation {
    public static boolean checkRoom(Room room) {
        if (room.getAdults() < 1) {
            log.debug("Adults: " + room.getAdults());
            return true;
        }

        if (room.getChildren() < 0) {
            log.debug("Children: " + room.getChildren());
            return true;
        }

        if (room.getCost() < 0.0) {
            log.debug("Cost: " + room.getCost());
            return true;
        }

        if (StringValidation.checkNullEmptyString(room.getType())) {
            log.debug("Type: " + room.getType());
            return true;
        }

        log.info("No error found in room details");
        return false;
    }
}
