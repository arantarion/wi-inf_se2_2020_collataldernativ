package org.bonn.se2.services.util;

/**
 * @author Coll@Aldernativ
 * @version 0.1a
 * @Programmer Henry Weckermann
 */

public class CryptoFunctions {

    public static String hash(String plaintext) {
        return org.apache.commons.codec.digest.DigestUtils.sha256Hex(plaintext);
    }

    public static boolean checkPw(String hash, String plain) {
        return hash.equals(plain);
    }

}
