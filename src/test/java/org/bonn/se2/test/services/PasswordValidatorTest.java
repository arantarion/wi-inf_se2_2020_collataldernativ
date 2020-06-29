package org.bonn.se2.test.services;

import org.bonn.se2.services.util.PasswordValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordValidatorTest {

    PasswordValidator pwv = new PasswordValidator("Fehler", 3, 10);

    @Test
    public void hasDigitPositive(){
        assertTrue(pwv.hasDigit("Test1234"));
    }

    @Test
    public void hasDigitNegative(){
        assertFalse(pwv.hasDigit("hallo"));
    }

    @Test
    public void hasLetterPositive() {
        assertTrue(pwv.hasLetter("Test1234"));
    }

    @Test
    public void hasLetterNegative() {
        assertFalse(pwv.hasLetter("123456"));
    }

    @Test
    public void isPasswordPositive() {
        assertTrue(pwv.isPassword("Test12345"));
    }

    @Test
    public void isPasswordNegative() {
        assertFalse(pwv.isPassword("test1234"));
        assertFalse(pwv.isPassword("TEST1234"));
        assertFalse(pwv.isPassword("123456"));
        assertFalse(pwv.isPassword("Test"));
    }


}