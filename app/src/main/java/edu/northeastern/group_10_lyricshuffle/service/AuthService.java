package edu.northeastern.group_10_lyricshuffle.service;

import edu.northeastern.group_10_lyricshuffle.model.User;
import edu.northeastern.group_10_lyricshuffle.repository.UserRepository;
import edu.northeastern.group_10_lyricshuffle.util.Result;
import edu.northeastern.group_10_lyricshuffle.util.SecurityUtils;

public class AuthService {
    private static final String TAG = "AuthService";
    private final UserRepository userRepository;

    public AuthService() {
        this.userRepository = new UserRepository();
    }

    public Result<User> signUp(String username, String email, String password) {
        // Validate input
        if (!isValidEmail(email)) {
            return Result.failure("Invalid email format");
        }
        if (!isValidPassword(password)) {
            return Result.failure("Password must be at least 8 characters");
        }
        if (!isValidUsername(username)) {
            return Result.failure("Username must be 3-30 characters");
        }

        // Create user with hashed password
        String hashedPassword = SecurityUtils.hashPassword(password);
        return userRepository.createUser(username, email, hashedPassword);
    }

    public Result<User> login(String email, String password) {
        // Get user by email
        Result<User> userResult = userRepository.findByEmail(email);
        if (!userResult.isSuccess()) {
            return Result.failure("User not found");
        }

        User user = userResult.getData();
        // Verify password
        if (SecurityUtils.verifyPassword(password, user.getPasswordHash())) {
            return Result.success(user);
        }
        return Result.failure("Invalid password");
    }

    private boolean isValidEmail(String email) {
        return email != null &&
                email.matches("^[A-Za-z0-9+_.-]+@(.+)$") &&
                email.length() <= 255;
    }

    private boolean isValidPassword(String password) {
        return password != null && !password.isEmpty();
    }

    /**
     * Validates a username. Format for username is alphanumeric with underscores and hyphens.
     *
     * @param username The username to validate
     * @return true if the username is valid, false otherwise
     */
    private boolean isValidUsername(String username) {
        return username != null &&
                username.matches("^[a-zA-Z0-9_-]{3,30}$");
    }
}
