package dreamtree.jlog.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import dreamtree.jlog.exception.JLogException;

public class Encryptor {

    public static String randomUniqueString(int length) {
        String uuid = UUID.randomUUID().toString();
        return toSHA256(uuid).substring(0, length);
    }

    private static String toSHA256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new JLogException("Encryption Failure");
        }
    }
}
