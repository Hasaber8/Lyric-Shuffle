package edu.northeastern.group_10_lyricshuffle.repository;

import android.content.Context;
import android.util.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import edu.northeastern.group_10_lyricshuffle.db.DatabaseManager;
import edu.northeastern.group_10_lyricshuffle.model.LyricLine;

public class LyricRepository {
    private static final String TAG = "LyricRepository";
    private final DatabaseManager dbManager;

    public LyricRepository(Context context) {
        this.dbManager = DatabaseManager.getInstance(context);
    }

    public List<LyricLine> getLyricsBySongId(UUID songId) {
        List<LyricLine> lyrics = new ArrayList<>();

        try (Connection conn = dbManager.getConnection()) {

            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT lyrics, lyric_order_id FROM lyrics WHERE song_id = ? ORDER BY lyric_order_id ASC"
            );
            stmt.setObject(1, songId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                // Adjust to match LyricLine constructor
                lyrics.add(new LyricLine(
                        rs.getString("lyrics"),          // Column name for lyric text
                        100,                             // Assuming points per lyric, hardcoded for now
                        rs.getInt("lyric_order_id")      // Order ID for correct position
                ));
            }
            Log.d(TAG, "Lyrics fetched successfully" + lyrics.size());
        } catch (SQLException e) {
            Log.e(TAG, "Error fetching lyrics", e);
        }

        return lyrics;
    }
}
