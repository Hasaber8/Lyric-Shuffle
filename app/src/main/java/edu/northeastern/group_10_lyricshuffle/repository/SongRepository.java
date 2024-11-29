package edu.northeastern.group_10_lyricshuffle.repository;

import android.content.Context;
import android.database.SQLException;
import android.util.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;

import edu.northeastern.group_10_lyricshuffle.db.DatabaseManager;
import edu.northeastern.group_10_lyricshuffle.model.Song;

public class SongRepository {
    private static final String TAG = "SongRepository";
    private final DatabaseManager dbManager;

    public SongRepository(Context context) {
        this.dbManager = DatabaseManager.getInstance(context);
    }

    // Method to get a random song by difficulty
    public Song getRandomSongByDifficulty(String difficultyLevel) {
        Song song = null;

        try (Connection conn = dbManager.getConnection()) {
            // Query to select a random song by difficulty
            String query = "SELECT * FROM songs WHERE difficulty_level = ?::difficulty_level ORDER BY RANDOM() LIMIT 1";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, difficultyLevel);
            ResultSet rs = stmt.executeQuery();

            // If we have a result, create the Song object
            if (rs.next()) {
                UUID songId = UUID.fromString(rs.getString("song_id"));
                String title = rs.getString("title");
                String artist = rs.getString("artist");
                String genre = rs.getString("genre");
                String difficulty = rs.getString("difficulty_level");

                song = new Song(songId, title, artist, genre, difficulty);
            }
        } catch (SQLException | java.sql.SQLException e) {
            Log.e(TAG, "Error fetching song for difficulty:" + difficultyLevel, e);
        }

        return song;
    }
}
