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
    public void casePositive() {
        assertTrue(pwv.hasLowercase("Test12345"));
        assertTrue(pwv.hasUpperCase("Test12345"));
    }

    @Test
    public void caseNegative() {
        assertFalse(pwv.hasUpperCase("test"));
        assertFalse(pwv.hasLowercase("TEST"));
    }


}