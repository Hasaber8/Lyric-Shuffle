package edu.northeastern.group_10_lyricshuffle.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:postgresql://aws-0-us-east-1.pooler.supabase.com:6543/postgres";
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
        if (connection == null || connection.isClosed()) {
            connection = createConnection();
        }
        return connection;
    }

    private Connection createConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("PostgreSQL JDBC Driver not found", e);
        }
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                // Log error
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
