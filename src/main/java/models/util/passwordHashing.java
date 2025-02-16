package models.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class passwordHashing {
    /**
     * A password hashing function is lifted from the following source
     * https://www.knowledgefactory.net/2019/10/java-secure-hashing-md5-sha-1-sha-256.html
     *
     * @param input of type String which acts as the password inputted by the user
     * @return a string value of the hashed password
     */
    public static String encryptPassword(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}