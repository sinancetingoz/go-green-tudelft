package util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class HashPassword {

    /**
     * Creates a hashed version of a password.
     * @param password the password to be hashed
     * @return the hashed password
     * @throws NoSuchAlgorithmException when something went wrong
     */
    public static String hashPass(String password) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.update(password.getBytes());
        String pass = new String(Base64.getEncoder().encode(digest.digest()));

        return pass;
    }
}
