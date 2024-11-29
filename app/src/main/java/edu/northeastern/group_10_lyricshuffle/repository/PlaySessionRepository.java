package edu.northeastern.group_10_lyricshuffle.repository;

import android.content.Context;
import android.util.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

import edu.northeastern.group_10_lyricshuffle.db.DatabaseManager;

public class PlaySessionRepository {

    private static final String TAG = "PlaySessionRepository";
    private final DatabaseManager dbManager;

    public PlaySessionRepository(Context context) {
        this.dbManager = DatabaseManager.getInstance(context);
    }

    public boolean saveScore(UUID songId, UUID userId, double score) {
        boolean isSuccess = false;

        // Query to insert a new record into the play_session table
        String query = "INSERT INTO play_session (song_id, user_id, score) VALUES (?, ?, ?)";

        try (Connection conn = dbManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setObject(1, songId); // Set song_id as UUID
            stmt.setObject(2, userId); // Set user_id as UUID
            stmt.setDouble(3, score); // Set score as decimal value

            // Execute the insert query
            int rowsAffected = stmt.executeUpdate();

            // Check if the insertion was successful
            if (rowsAffected > 0) {
                isSuccess = true;
            }

        } catch (SQLException e) {
            Log.e(TAG, "Error saving score", e);
        }

        return isSuccess;
    }
}
