package com.example.bookingsystem.validation;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StringValidationTest {

    @Test
    void testCheckNullEmptyString_strValueIsCorrect() {
        String str = "hello";
        boolean result = StringValidation.checkNullEmptyString(str);
        assertThat(result).isFalse();
    }

    @Test
    void testCheckNullEmptyString_strValueIsEmpty() {
        String str = "";
        boolean result = StringValidation.checkNullEmptyString(str);
        assertThat(result).isTrue();
    }

    @Test
    void testCheckNullEmptyString_strValueIsNull() {
        String str = "null";
        boolean result = StringValidation.checkNullEmptyString(str);
        assertThat(result).isTrue();
    }
}
