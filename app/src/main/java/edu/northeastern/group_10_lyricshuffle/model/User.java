package edu.northeastern.group_10_lyricshuffle.model;

import java.time.ZonedDateTime;
import java.util.UUID;

public class User {
    private final UUID userId;
    private String username;
    private String email;
    private final String passwordHash;
    private final ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;

    // Constructor for database retrieved users
    public User(UUID userId, String username, String email, String passwordHash,
                ZonedDateTime createdAt, ZonedDateTime updatedAt) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters for immutable fields
    public UUID getUserId() { return userId; }
    public String getPasswordHash() { return passwordHash; }
    public ZonedDateTime getCreatedAt() { return createdAt; }

    // Getters and setters for mutable fields
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public ZonedDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(ZonedDateTime updatedAt) { this.updatedAt = updatedAt; }
}
