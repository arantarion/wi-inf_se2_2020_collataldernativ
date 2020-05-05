package org.bonn.se2.process.control.exceptions;

public class DatabaseException extends CollException {

    public DatabaseException() {
        super("Es gibt ein Problem mit der Datenbank");
    }

}
