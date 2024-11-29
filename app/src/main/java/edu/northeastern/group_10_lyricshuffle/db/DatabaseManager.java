package edu.northeastern.group_10_lyricshuffle.db;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseManager {
    private static final String TAG = "DatabaseManager";

    // Keys for stored credentials
    private static final String ENCRYPTED_PREFS_FILE = "secure_database_credentials";
    private static final String KEY_DB_URL = "db_url";
    private static final String KEY_DB_USER = "db_user";
    private static final String KEY_DB_PASSWORD = "db_password";

    private static DatabaseManager instance;
    private Connection connection;

    private final Context context;
    private EncryptedSharedPreferences encryptedPrefs;

    private DatabaseManager(Context context) {
        // Private constructor for singleton pattern
        this.context = context.getApplicationContext();
        initializeEncryptedPrefs();
        // Initialize credentials if not already set
        if (!areCredentialsStored()) {
            storeInitialCredentials();
        }
    }

    private void initializeEncryptedPrefs() {
        try {
            MasterKey masterKey = new MasterKey.Builder(context)
                    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                    .build();

            encryptedPrefs = (EncryptedSharedPreferences) EncryptedSharedPreferences.create(
                    context,
                    ENCRYPTED_PREFS_FILE,
                    masterKey,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
        } catch (GeneralSecurityException | IOException e) {
            Log.e(TAG, "Error initializing EncryptedSharedPreferences", e);
            throw new RuntimeException("Error initializing EncryptedSharedPreferences", e);
        }
    }

    private void storeInitialCredentials() {
        // Store initial credentials securely
        // In a real app, get these from a secure source
        encryptedPrefs.edit()
                .putString(KEY_DB_URL, "jdbc:postgresql://aws-0-us-east-1.pooler.supabase.com:5432/postgres")
                .putString(KEY_DB_USER, "postgres.hchslsklkmewlniwedxi")
                .putString(KEY_DB_PASSWORD, "lyricshuffle123")
                .apply();
    }

    private boolean areCredentialsStored() {
        return encryptedPrefs.contains(KEY_DB_URL) &&
                encryptedPrefs.contains(KEY_DB_USER) &&
                encryptedPrefs.contains(KEY_DB_PASSWORD);
    }

    public static synchronized DatabaseManager getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseManager(context);
        }
        return instance;
    }

    public synchronized Connection getConnection() throws SQLException {
        try {
            if (connection == null || connection.isClosed()) {
                String dbUrl = encryptedPrefs.getString(KEY_DB_URL, "");
                if (dbUrl.isEmpty()) {
                    throw new SQLException("Database URL not found in secure storage");
                }

                Properties props = getProperties();

                // Create the connection
                connection = DriverManager.getConnection(dbUrl, props);

                // Configure connection with auto-commit so we don't have to manually commit for
                // every query
                connection.setAutoCommit(true);
            }
            return connection;
        } catch (SQLException e) {
            Log.e(TAG, "Database connection error: " + e.getMessage(), e);
            throw new SQLException("Could not connect to database. Please check your internet connection.", e);
        }
    }

    @NonNull
    private Properties getProperties() {
        Properties props = new Properties();
        props.setProperty("user", encryptedPrefs.getString(KEY_DB_USER, ""));
        props.setProperty("password", encryptedPrefs.getString(KEY_DB_PASSWORD, ""));
        props.setProperty("ssl", "true");
        props.setProperty("sslmode", "require");

        // Disable features that cause problems on Android
        props.setProperty("ApplicationName", "LyricShuffle");
        props.setProperty("assumeMinServerVersion", "9.0");
        props.setProperty("binaryTransfer", "false");
        props.setProperty("preferQueryMode", "simple");

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

    public static void cleanup() {
        if (instance != null) {
            instance.closeConnection();
            instance = null;
        }
    }
}
