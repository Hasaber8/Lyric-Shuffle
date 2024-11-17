package edu.northeastern.group_10_lyricshuffle;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import edu.northeastern.group_10_lyricshuffle.adapter.LeaderboardAdapter;
import edu.northeastern.group_10_lyricshuffle.model.LeaderboardEntry;

public class LeaderboardActivity extends AppCompatActivity {
    private RecyclerView rankingsRecyclerView;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        // Setup back button
        findViewById(R.id.backButton).setOnClickListener(v -> onBackPressed());

        // Setup RecyclerView
        rankingsRecyclerView = findViewById(R.id.rankingsRecyclerView);
        rankingsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        rankingsRecyclerView.setHasFixedSize(true);

        // Create and set adapter
        List<LeaderboardEntry> dummyData = createDummyData();
        LeaderboardAdapter adapter = new LeaderboardAdapter(dummyData);
        rankingsRecyclerView.setAdapter(adapter);

        // Setup bottom navigation
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        boolean fromBottomNav = getIntent().getBooleanExtra("FROM_BOTTOM_NAV", false);

        if (fromBottomNav) {
            bottomNavigationView.setVisibility(View.VISIBLE);
            bottomNavigationView.setSelectedItemId(R.id.nav_leaderboard);
            setupBottomNavigation();
        } else {
            bottomNavigationView.setVisibility(View.GONE);
        }
    }

    private void setupBottomNavigation() {
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_play) {
                finish();
                overridePendingTransition(0, 0);
                return true;
            } else if (item.getItemId() == R.id.nav_leaderboard) {
                return true;
            }
            return false;
        });
    }

    // Create dummy data for leaderboard
    private List<LeaderboardEntry> createDummyData() {
        List<LeaderboardEntry> entries = new ArrayList<>();
        String[] names = {
                "Emily Wilson", "Alex Turner", "Sarah Parker", "James Lee",
                "Maria Garcia", "David Kim", "Lisa Chen", "Ryan Taylor",
                "Anna Brown", "Chris Wong", "Sophie Martin", "Tom Anderson",
                "Rachel Lewis", "Kevin Park", "Michelle Ng", "Brian White",
                "Laura Hill", "Steve Rodriguez"
        };

        Random random = new Random();
        for (int i = 0; i < names.length; i++) {
            int rank = i + 4;
            int score = 2500 - (rank * 50) + random.nextInt(100);
            entries.add(new LeaderboardEntry(
                    rank,
                    names[i],
                    score
            ));
        }

        return entries;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (getIntent().getBooleanExtra("FROM_BOTTOM_NAV", false)) {
            overridePendingTransition(0, 0);
        }
    }
}