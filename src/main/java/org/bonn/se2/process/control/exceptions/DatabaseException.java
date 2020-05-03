package org.bonn.se2.process.control.exceptions;

public class DatabaseException extends Exception {

    private String reason = null;

    public DatabaseException() {
        super("There was an error with the database!");
    }

    public DatabaseException(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

}
