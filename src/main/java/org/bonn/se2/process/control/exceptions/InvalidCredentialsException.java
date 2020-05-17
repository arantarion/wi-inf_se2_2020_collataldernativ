package org.bonn.se2.process.control.exceptions;

/**
 *
 * @version 0.1a
 * @author Coll@Aldernativ
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
