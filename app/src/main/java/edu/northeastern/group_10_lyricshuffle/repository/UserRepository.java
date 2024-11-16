package edu.northeastern.group_10_lyricshuffle.repository;

import android.util.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.UUID;

import edu.northeastern.group_10_lyricshuffle.db.DatabaseManager;
import edu.northeastern.group_10_lyricshuffle.model.User;
import edu.northeastern.group_10_lyricshuffle.util.Result;

public class UserRepository {
    private static final String TAG = "UserRepository";
    private final DatabaseManager dbManager;

    public UserRepository() {
        this.dbManager = DatabaseManager.getInstance();
    }

    // Create user with pre-hashed password
    public Result<User> createUser(String username, String email, String hashedPassword) {
        try (Connection conn = dbManager.getConnection()) {
            conn.setAutoCommit(false);
            try {
                // Check if email exists
                if (emailExists(conn, email)) {
                    return Result.failure("Email already registered");
                }

                PreparedStatement stmt = conn.prepareStatement(
                        "INSERT INTO users (username, email, password_hash) VALUES (?, ?, ?) " +
                                "RETURNING *"
                );
                stmt.setString(1, username);
                stmt.setString(2, email);
                stmt.setString(3, hashedPassword);

                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    User user = mapResultSetToUser(rs);
                    conn.commit();
                    return Result.success(user);
                }

                conn.rollback();
                return Result.failure("Failed to create user");

            } catch (SQLException e) {
                conn.rollback();
                Log.e(TAG, "Error creating user", e);
                return Result.failure("Database error: " + e.getMessage());
            }
        } catch (SQLException e) {
            Log.e(TAG, "Connection error", e);
            return Result.failure("Database connection error");
        }
    }

    public Result<User> findByEmail(String email) {
        try (Connection conn = dbManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT * FROM users WHERE email = ?"
            );
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Result.success(mapResultSetToUser(rs));
            }
            return Result.failure("User not found");

        } catch (SQLException e) {
            Log.e(TAG, "Database error finding user", e);
            return Result.failure("Database error");
        }
    }

//    public Result<User> findById(UUID userId) {
//        try (Connection conn = dbManager.getConnection()) {
//            PreparedStatement stmt = conn.prepareStatement(
//                    "SELECT * FROM users WHERE user_id = ?"
//            );
//            stmt.setObject(1, userId);
//            ResultSet rs = stmt.executeQuery();
//
//            if (rs.next()) {
//                return Result.success(mapResultSetToUser(rs));
//            }
//            return Result.failure("User not found");
//
//        } catch (SQLException e) {
//            Log.e(TAG, "Database error finding user", e);
//            return Result.failure("Database error");
//        }
//    }

    private boolean emailExists(Connection conn, String email) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(
                "SELECT 1 FROM users WHERE email = ?"
        );
        stmt.setString(1, email);
        ResultSet rs = stmt.executeQuery();
        return rs.next();
    }

    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        return new User(
                UUID.fromString(rs.getString("user_id")),
                rs.getString("username"),
                rs.getString("email"),
                rs.getString("password_hash"),
                (ZonedDateTime) rs.getObject("created_at"),
                (ZonedDateTime) rs.getObject("updated_at")
        );
    }
}
