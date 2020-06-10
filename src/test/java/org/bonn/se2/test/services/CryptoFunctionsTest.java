package org.bonn.se2.test.services;

import org.bonn.se2.services.util.CryptoFunctions;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @version 0.1a
 * @author Coll@Aldernativ
 * @Programmer Henry Weckermann
 */

public class CryptoFunctionsTest {

    String plain = "Login123";
    String hash;

    @Test
    public void hash() {
        hash = CryptoFunctions.hash(plain);
        assertEquals("4733b4411f34e29fe5cd20f7113fefd3b5d6e20d8d2557309db0958a7afe81c6", hash);
    }

}