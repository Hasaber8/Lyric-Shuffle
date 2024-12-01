package edu.northeastern.group_10_lyricshuffle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.UUID;

import edu.northeastern.group_10_lyricshuffle.adapter.RecentPlaysAdapter;
import edu.northeastern.group_10_lyricshuffle.model.RecentPlay;
import edu.northeastern.group_10_lyricshuffle.repository.PlaySessionRepository;
import edu.northeastern.group_10_lyricshuffle.util.UserSession;

public class HomeActivity extends AppCompatActivity {

    public static final String EXTRA_SHOW_BOTTOM_NAV = "show_bottom_nav";

    private RecentPlaysAdapter recentPlaysAdapter;
    private PlaySessionRepository playSessionRepository;

    private static final String KEY_RECENT_PLAYS = "recent_plays";
    private ArrayList<RecentPlay> recentPlays = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setupBottomNavigation();
        setupLeaderboardCard();
        setupGameCard();
        setupAboutCard();
        setupUserButton();
        setupHowToPlayCard();

        playSessionRepository = new PlaySessionRepository(this);
        setupRecentPlays();

        // Only fetch if there's no saved state
        if (savedInstanceState == null) {
            fetchRecentPlays();
        } else {
            recentPlays = savedInstanceState.getParcelableArrayList(KEY_RECENT_PLAYS);
            if (recentPlays != null && !recentPlays.isEmpty()) {
                findViewById(R.id.recentPlaysSection).setVisibility(View.VISIBLE);
                recentPlaysAdapter.updateData(recentPlays);
            }
        }
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
            Intent intent = new Intent(HomeActivity.this, DifficultySelectionActivity.class);
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

    private void setupHowToPlayCard() {
        MaterialCardView aboutUsCard = findViewById(R.id.howToPlayCard);
        aboutUsCard.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, HowToPlayActivity.class);
            startActivity(intent);
        });
    }

    private void setupRecentPlays() {
        RecyclerView recentPlaysRecyclerView = findViewById(R.id.recentPlaysRecyclerView);
        recentPlaysRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        recentPlaysAdapter = new RecentPlaysAdapter(new ArrayList<>());
        recentPlaysRecyclerView.setAdapter(recentPlaysAdapter);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(KEY_RECENT_PLAYS, recentPlays);
    }

    private void fetchRecentPlays() {
        // Fetch recent plays in background
        new Thread(() -> {
            UUID currentUserId = UserSession.getInstance(this).getUserId();
            recentPlays = new ArrayList<>(playSessionRepository.getRecentPlays(currentUserId, 5));

            runOnUiThread(() -> {
                if (!recentPlays.isEmpty()) {
                    findViewById(R.id.recentPlaysSection).setVisibility(View.VISIBLE);
                    recentPlaysAdapter.updateData(recentPlays);
                }
            });
        }).start();
    }
}
