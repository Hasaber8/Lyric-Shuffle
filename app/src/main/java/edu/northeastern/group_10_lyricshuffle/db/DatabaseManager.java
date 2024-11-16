package edu.northeastern.group_10_lyricshuffle.db;

import android.util.Log;

import androidx.annotation.NonNull;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseManager {
    private static final String TAG = "DatabaseManager";

    // Database credentials
    private static final String DB_URL = "jdbc:postgresql://aws-0-us-east-1.pooler.supabase.com:5432/postgres";
    private static final String DB_USER = "postgres.hchslsklkmewlniwedxi";
    private static final String DB_PASSWORD = "lyricshuffle123";

    private static DatabaseManager instance;
    private Connection connection;

    private DatabaseManager() {
        // Private constructor for singleton pattern
    }

    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    public synchronized Connection getConnection() throws SQLException {
        try {
            if (connection == null || connection.isClosed()) {
                Properties props = getProperties();

                // Create the connection
                connection = DriverManager.getConnection(DB_URL, props);

                // Configure connection
                connection.setAutoCommit(true);
            }
            return connection;
        } catch (SQLException e) {
            Log.e(TAG, "Database connection error: " + e.getMessage(), e);
            throw new SQLException("Could not connect to database. Please check your internet connection.", e);
        }
    }

    @NonNull
    private static Properties getProperties() {
        Properties props = new Properties();
        props.setProperty("user", DB_USER);
        props.setProperty("password", DB_PASSWORD);
        props.setProperty("ssl", "true");
        props.setProperty("sslmode", "require");

        // Disable features that cause problems on Android
        props.setProperty("ApplicationName", "LyricShuffle");
        props.setProperty("assumeMinServerVersion", "9.0");
        props.setProperty("MaxResultBuffer", "2");  // Smaller buffer size
        props.setProperty("binaryTransfer", "false");

        // Set timeouts
        props.setProperty("loginTimeout", "10");
        props.setProperty("connectTimeout", "10");
        props.setProperty("socketTimeout", "10");
        return props;
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                Log.e(TAG, "Error closing connection", e);
            } finally {
                connection = null;
            }
        }
    }

    // Call this in your Application's onDestroy or when you're done with the database
    public static void cleanup() {
        if (instance != null) {
            instance.closeConnection();
            instance = null;
        }
    }
}
