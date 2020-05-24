/*
 * @version 0.1a
 * @author Coll@Aldernativ
 * @Programmer
 *
 *
 */

package org.bonn.se2.services.util;

import com.vaadin.data.ValidationResult;
import com.vaadin.data.ValueContext;
import com.vaadin.data.validator.StringLengthValidator;

public class PasswordValidator extends StringLengthValidator {

    public PasswordValidator(String errorMessage, Integer minLength, Integer maxLength) {
        super(errorMessage, minLength, maxLength);
    }

    public PasswordValidator() {
        super("", 8, 1000);
    }

    @Override
    public ValidationResult apply(String value, ValueContext context) {
        ValidationResult result = super.apply(value, context);
        if (result.isError()) {
            return ValidationResult.error("Ihr Passwort muss min. 8 Zeichen lang sein");
        } else if (!hasDigit(value) || !hasLetter(value) || !hasLowercase(value) || !hasUpperCase(value)) {
            return ValidationResult.error("Das Passwort muss mind. eine Zahl, einen großen und einen kleinen Buchstaben haben");
        }
        return result;
    }

    private boolean hasDigit(String pwd) {
        return pwd.chars().anyMatch(Character::isDigit);
    }
    private boolean hasLetter(String pwd) {
        return pwd.chars().anyMatch(Character::isLetter);
    }
    private boolean hasLowercase(String pwd) { return !pwd.equals(pwd.toLowerCase()); }
    private boolean hasUpperCase(String pwd) { return !pwd.equals(pwd.toUpperCase()); }
}
