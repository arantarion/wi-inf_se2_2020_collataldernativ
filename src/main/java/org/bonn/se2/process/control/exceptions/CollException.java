package org.bonn.se2.process.control.exceptions;

public class CollException extends Exception {

    private String reason;

    public CollException(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

}
