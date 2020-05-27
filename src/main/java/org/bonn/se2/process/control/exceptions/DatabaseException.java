package org.bonn.se2.process.control.exceptions;

/**
 * @author Coll@Aldernativ
 * @version 0.1a
 * @Programmer Henry Weckermann
 */

public class DatabaseException extends CollException {

    public DatabaseException() {
        super("Es gibt ein Problem mit der Datenbank");
    }

    public DatabaseException(String msg) {
        super(msg);
    }

}
