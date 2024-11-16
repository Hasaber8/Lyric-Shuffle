package edu.northeastern.group_10_lyricshuffle.model;

import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.UUID;

public class User {
    private final UUID userId;
    private String username;
    private String email;
    private final String passwordHash;
    private final Timestamp createdAt;
    private Timestamp updatedAt;

    // Constructor for database retrieved users
    public User(UUID userId, String username, String email, String passwordHash,
                Timestamp createdAt, Timestamp updatedAt) {
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
    public Timestamp getCreatedAt() { return createdAt; }

    // Getters and setters for mutable fields
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Timestamp getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }
}
