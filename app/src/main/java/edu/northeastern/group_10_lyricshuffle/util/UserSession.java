package edu.northeastern.group_10_lyricshuffle.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.UUID;

import edu.northeastern.group_10_lyricshuffle.model.User;

public class UserSession {
    private static final String PREF_NAME = "UserSession";
    private static final String KEY_USER_ID = "userId";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_EMAIL = "email";

    private static UserSession instance;
    private SharedPreferences sharedPreferences;

    // Private constructor
    private UserSession(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    // Singleton instance
    public static synchronized UserSession getInstance(Context context) {
        if (instance == null) {
            instance = new UserSession(context.getApplicationContext());
        }
        return instance;
    }

    // Save user session
    public void saveUser(User user) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USER_ID, user.getUserId().toString()); // Save UUID as String
        editor.putString(KEY_USERNAME, user.getUsername());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.apply();
    }

    // Get stored userId as UUID
    public UUID getUserId() {
        String userIdString = sharedPreferences.getString(KEY_USER_ID, null);
        if (userIdString != null) {
            try {
                return UUID.fromString(userIdString);
            } catch (IllegalArgumentException e) {
                Log.e("UserSession", "Invalid UUID string: " + userIdString);
            }
        }
        return null;
    }

    // Get stored username
    public String getUsername() {
        return sharedPreferences.getString(KEY_USERNAME, null);
    }

    // Get stored email
    public String getEmail() {
        return sharedPreferences.getString(KEY_EMAIL, null);
    }

    // Check if user is logged in
    public boolean isLoggedIn() {
        return sharedPreferences.contains(KEY_USER_ID);
    }

    // Clear user session
    public void clearSession() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
