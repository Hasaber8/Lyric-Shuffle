package edu.northeastern.group_10_lyricshuffle.repository;

import android.content.Context;
import android.util.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.northeastern.group_10_lyricshuffle.db.DatabaseManager;
import edu.northeastern.group_10_lyricshuffle.model.LeaderboardEntry;

public class LeaderboardRepository {
    private static final String TAG = "LeaderboardRepository";
    private final DatabaseManager dbManager;

    public LeaderboardRepository(Context context) {
        this.dbManager = DatabaseManager.getInstance(context);
    }

    /**
     * Fetches the top 20 users based on their average score over the last 10 plays
     *
     * @return List of leaderboard entries
     */
    public List<LeaderboardEntry> getTop20Leaderboard() {
        List<LeaderboardEntry> leaderboardEntries = new ArrayList<>();

        try (Connection conn = dbManager.getConnection()) {
            // Query to calculate leaderboard
            String query =
                    "SELECT username, AVG(score) AS average_score " +
                            "FROM ( " +
                            "    SELECT ps.user_id, u.username, ps.score " +
                            "    FROM play_session ps " +
                            "    INNER JOIN users u ON ps.user_id = u.user_id " +
                            "    WHERE ps.play_session_id IN ( " +
                            "        SELECT play_session_id " +
                            "        FROM play_session sub_ps " +
                            "        WHERE sub_ps.user_id = ps.user_id " +
                            "        ORDER BY sub_ps.date_played DESC " +
                            "    ) " +
                            ") subquery " +
                            "GROUP BY username " +
                            "ORDER BY average_score DESC " +
                            "LIMIT 20";

            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            int rank = 1;

            // Iterate through the result set and build leaderboard entries
            while (rs.next()) {
                String username = rs.getString("username");
                double averageScore = rs.getDouble("average_score");

                leaderboardEntries.add(new LeaderboardEntry(rank++, username, (int) averageScore));
            }
        } catch (SQLException e) {
            Log.e(TAG, "Error fetching leaderboard data", e);
        }

        return leaderboardEntries;
    }

    /**
     * Fetches the given user's score based on their average score over the last 10 plays
     *
     * @param username The username of the user
     * @return The user's score
     */
    public int getUserScore(String username) {
        try (Connection conn = dbManager.getConnection()) {
            // Query to calculate user's score
            String query =
                    "SELECT AVG(score) AS average_score " +
                            "FROM ( " +
                            "    SELECT ps.user_id, u.username, ps.score " +
                            "    FROM play_session ps " +
                            "    INNER JOIN users u ON ps.user_id = u.user_id " +
                            "    WHERE ps.play_session_id IN ( " +
                            "        SELECT play_session_id " +
                            "        FROM play_session sub_ps " +
                            "        WHERE sub_ps.user_id = ps.user_id " +
                            "        ORDER BY sub_ps.date_played DESC " +
                            "    ) " +
                            ") subquery " +
                            "WHERE username = ?";

            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return (int) rs.getDouble("average_score");
            }
        } catch (SQLException e) {
            Log.e(TAG, "Error fetching user score data", e);
        }

        return 0;
    }
}
