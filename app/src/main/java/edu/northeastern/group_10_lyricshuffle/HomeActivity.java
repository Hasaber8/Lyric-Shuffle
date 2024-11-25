package edu.northeastern.group_10_lyricshuffle;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;

public class HomeActivity extends AppCompatActivity {

    public static final String EXTRA_SHOW_BOTTOM_NAV = "show_bottom_nav";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setupBottomNavigation();
        setupLeaderboardCard();
        setupGameCard();
        setupAboutCard();
        setupUserButton();
    }

    private void setupBottomNavigation() {
        // Bottom Navigation setup
        BottomNavigationView bottomNavigationMenuView = findViewById(R.id.bottomNavigation);
        bottomNavigationMenuView.setSelectedItemId(R.id.nav_play);

        bottomNavigationMenuView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_play) {
                return true;
            } else if (item.getItemId() == R.id.nav_leaderboard) {
                // Launch from bottom nav
                Intent intent = new Intent(HomeActivity.this, LeaderboardActivity.class);
                intent.putExtra("FROM_BOTTOM_NAV", true);
                startActivity(intent);
                overridePendingTransition(0, 0);
                return false;
            }
            return false;
        });
    }

    private void setupLeaderboardCard() {
        // Leaderboard card setup
        MaterialCardView leaderboardCard = findViewById(R.id.leaderboardCard);
        leaderboardCard.setOnClickListener(v -> {
            // Launch from card click
            Intent intent = new Intent(HomeActivity.this, LeaderboardActivity.class);
            intent.putExtra("FROM_BOTTOM_NAV", true);
            startActivity(intent);
        });
    }

    private void setupGameCard() {
        MaterialCardView gameCard = findViewById(R.id.playNowCard);
        gameCard.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, GameActivity.class);
            startActivity(intent);
        });
    }

    private void setupAboutCard() {
        MaterialCardView aboutUsCard = findViewById(R.id.aboutCard);
        aboutUsCard.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, AboutActivity.class);
            startActivity(intent);
        });
    }

    private void setupUserButton() {
        ImageButton userButton = findViewById(R.id.userButton);
        userButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
            startActivity(intent);
        });
    }
}