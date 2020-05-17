package org.bonn.se2.process.control.exceptions;

/**
 *
 * @version 0.1a
 * @author Coll@Aldernativ
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
