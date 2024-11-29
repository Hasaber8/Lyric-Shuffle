package edu.northeastern.group_10_lyricshuffle;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.imageview.ShapeableImageView;

import edu.northeastern.group_10_lyricshuffle.repository.LeaderboardRepository;
import edu.northeastern.group_10_lyricshuffle.util.IdenticonGenerator;
import edu.northeastern.group_10_lyricshuffle.util.UserSession;

public class ProfileActivity extends AppCompatActivity {
    private static final String TAG = "ProfileActivity";
    private static final String KEY_SCORE = "score";
    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Setup toolbar back button
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        // Fetch user details
        UserSession userSession = UserSession.getInstance(this);
        String username = userSession.getUsername();
        String email = userSession.getEmail();

        // Setup the user's icon
        Bitmap identicon = IdenticonGenerator.generate(username);
        ShapeableImageView imageView = findViewById(R.id.userIcon);
        imageView.setImageBitmap(identicon);

        // Set user details to the TextViews
        TextView usernameTextView = findViewById(R.id.username);
        TextView emailTextView = findViewById(R.id.email);
        usernameTextView.setText(username);
        emailTextView.setText(email);

        // Fetch user score and set to the score TextView or restore score if available
        if (savedInstanceState != null) {
            Log.d(TAG, "Restoring score from savedInstanceState");
            score = savedInstanceState.getInt(KEY_SCORE, 0);
            updateScoreTextView();
        } else {
            Log.d(TAG, "Score not in the savedInstanceState, fetching from the database");
            fetchUserScore();
        }

        // Setup logout button
        Button logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(v -> {
            // Clear user session
            UserSession.getInstance(this).clearSession();

            // Redirect to LoginActivity
            Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void fetchUserScore() {
        new Thread(() -> {
            try {
                // Fetch data using repository
                LeaderboardRepository leaderboardRepository = new LeaderboardRepository(getApplicationContext());
                score = leaderboardRepository.getUserScore(UserSession.getInstance(this).getUsername());

                // Update UI on the main thread
                new Handler(Looper.getMainLooper()).post(this::updateScoreTextView);
            } catch (Exception e) {
                Log.e(TAG, "Error fetching user score", e);
            }
        }).start();
    }

    private void updateScoreTextView() {
        TextView scoreTextView = findViewById(R.id.score);
        scoreTextView.setText(String.valueOf(score));
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_SCORE, score);
    }
}