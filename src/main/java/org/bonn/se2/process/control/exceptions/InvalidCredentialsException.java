package org.bonn.se2.process.control.exceptions;

/**
 * @author Coll@Aldernativ
 * @version 0.1a
 * @Programmer Henry Weckermann
 */

public class InvalidCredentialsException extends CollException {

    public InvalidCredentialsException() {
        super("Die angegebenen Login-Daten sind nicht korrekt. Versuchen Sie es erneut.");
    }

    public InvalidCredentialsException(String msg) {
        super(msg);
    }

}
