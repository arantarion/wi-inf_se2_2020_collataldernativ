package org.bonn.se2.process.control.exceptions;

/**
 * @author Coll@Aldernativ
 * @version 0.1a
 * @Programmer Henry Weckermann
 */

public class DontUseException extends CollException {

    public DontUseException() {
        super("Please dont use this method. It is not ready or does not make sense in the context");
    }

    public DontUseException(String msg) {
        super(msg);
    }

}
