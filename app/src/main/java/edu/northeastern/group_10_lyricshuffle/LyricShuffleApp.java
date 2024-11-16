package edu.northeastern.group_10_lyricshuffle;

import android.util.Log;

import androidx.multidex.MultiDexApplication;

import edu.northeastern.group_10_lyricshuffle.db.DatabaseManager;

public class LyricShuffleApp extends MultiDexApplication {
    private static final String TAG = "LyricShuffleApp";

    @Override
    public void onCreate() {
        super.onCreate();

        // Register JDBC driver
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            Log.e(TAG, "JDBC driver not found", e);
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        // Clean up database connections
        DatabaseManager.getInstance().closeConnection();
    }
}
