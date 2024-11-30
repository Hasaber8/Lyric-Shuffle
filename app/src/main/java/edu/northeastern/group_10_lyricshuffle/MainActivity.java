package edu.northeastern.group_10_lyricshuffle;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import edu.northeastern.group_10_lyricshuffle.util.NotificationScheduler;
import edu.northeastern.group_10_lyricshuffle.util.UserSession;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize notifications on every app launch
        NotificationScheduler scheduler = new NotificationScheduler(this);
        if (scheduler.areNotificationsEnabled()) {
            scheduler.scheduleAllNotifications();
        }

        Intent intent;
        if (UserSession.getInstance(this).isLoggedIn()) {
            // User is logged in, redirect to Home page
            intent = new Intent(MainActivity.this, HomeActivity.class);
        } else {
            // User is not logged in, redirect to Login page
            intent = new Intent(MainActivity.this, LoginActivity.class);
        }
        startActivity(intent);
        finish();
    }
}
