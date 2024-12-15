package chatsystem.assets;

import java.security.SecureRandom;
import java.util.Base64;

public class RandomAccessKeyGenerator {

    private static final String CHAR_UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String CHAR_LOWER = CHAR_UPPER.toLowerCase();
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL_CHARS = "!@#$%^&*()_+-=[]{};':\"\\|,.<>/?";
    private static final String ALL_CHARS = CHAR_UPPER + CHAR_LOWER + DIGITS + SPECIAL_CHARS;

    private static final SecureRandom random = new SecureRandom();

    public String generateRandomAccessKey(int length) {
        if (length < 1) {
            throw new IllegalArgumentException("Length must be greater than 0");
        }

        byte[] bytes = new byte[length];
        random.nextBytes(bytes);

        // Use Base64 to efficiently encode the random bytes to a string
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes); 
    }
}
