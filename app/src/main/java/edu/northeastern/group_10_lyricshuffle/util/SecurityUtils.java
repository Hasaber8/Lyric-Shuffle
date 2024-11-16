package edu.northeastern.group_10_lyricshuffle.util;

import android.util.Log;

import org.mindrot.jbcrypt.BCrypt;

public class SecurityUtils {
    private static final String TAG = "SecurityUtils";

    // BCrypt workload factor (cost parameter)
    // Higher value = more secure but slower. 12 is a good default in 2024.
    private static final int WORKLOAD = 12;

    /**
     * Hashes a password using BCrypt.
     * The salt is automatically generated and included in the hash.
     *
     * @param password The plain text password to hash
     * @return The BCrypt hash string which includes the salt and cost factor
     */
    public static String hashPassword(String password) {
        try {
            // BCrypt will generate a random salt and include it in the hash
            return BCrypt.hashpw(password, BCrypt.gensalt(WORKLOAD));
        } catch (IllegalArgumentException e) {
            Log.e(TAG, "Error hashing password", e);
            throw new RuntimeException("Error hashing password", e);
        }
    }

    /**
     * Verifies a password against a stored hash.
     *
     * @param password The plain text password to check
     * @param storedHash The stored BCrypt hash
     * @return true if the password matches, false otherwise
     */
    public static boolean verifyPassword(String password, String storedHash) {
        try {
            return BCrypt.checkpw(password, storedHash);
        } catch (IllegalArgumentException e) {
            Log.e(TAG, "Error verifying password", e);
            return false;
        }
    }
}
