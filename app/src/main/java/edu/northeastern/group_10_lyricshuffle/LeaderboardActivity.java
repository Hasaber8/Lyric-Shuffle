package edu.northeastern.group_10_lyricshuffle;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.List;

import edu.northeastern.group_10_lyricshuffle.adapter.LeaderboardAdapter;
import edu.northeastern.group_10_lyricshuffle.model.LeaderboardEntry;
import edu.northeastern.group_10_lyricshuffle.repository.LeaderboardRepository;
import edu.northeastern.group_10_lyricshuffle.util.IdenticonGenerator;

public class LeaderboardActivity extends AppCompatActivity {
    private RecyclerView rankingsRecyclerView;
    private BottomNavigationView bottomNavigationView;
    private LeaderboardAdapter adapter;
    private static final String TAG = "LeaderboardActivity";

    private ShapeableImageView firstPlaceImage, secondPlaceImage, thirdPlaceImage;
    private TextView firstPlaceName, secondPlaceName, thirdPlaceName;
    private TextView firstPlaceScore, secondPlaceScore, thirdPlaceScore;

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

        adapter = new LeaderboardAdapter(new ArrayList<>());
        rankingsRecyclerView.setAdapter(adapter);

        // Find views for the top three players
        setupTopPlayers();

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

        // Fetch leaderboard data
        fetchLeaderboardData();
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

    private void setupTopPlayers() {
        firstPlaceImage = findViewById(R.id.firstPlaceImage);
        secondPlaceImage = findViewById(R.id.secondPlaceImage);
        thirdPlaceImage = findViewById(R.id.thirdPlaceImage);

        firstPlaceName = findViewById(R.id.firstPlaceName);
        secondPlaceName = findViewById(R.id.secondPlaceName);
        thirdPlaceName = findViewById(R.id.thirdPlaceName);

        firstPlaceScore = findViewById(R.id.firstPlaceScore);
        secondPlaceScore = findViewById(R.id.secondPlaceScore);
        thirdPlaceScore = findViewById(R.id.thirdPlaceScore);
    }

    private void fetchLeaderboardData() {
        new Thread(() -> {
            try {
                // Fetch data using repository
                LeaderboardRepository leaderboardRepository = new LeaderboardRepository();
                List<LeaderboardEntry> leaderboardData = leaderboardRepository.getTop20Leaderboard();

                // Update UI on main thread
                new Handler(Looper.getMainLooper()).post(() -> updateUI(leaderboardData));
            } catch (Exception e) {
                Log.e(TAG, "Error fetching leaderboard data", e);
            }
        }).start();
    }

    private void updateUI(List<LeaderboardEntry> leaderboardData) {
        if (leaderboardData == null || leaderboardData.isEmpty()) {
            Log.w(TAG, "No leaderboard data available");
            return;
        }

        // Update top three players
        if (leaderboardData.size() > 0) updateTopPlayer(firstPlaceImage, firstPlaceName, firstPlaceScore, leaderboardData.get(0));
        if (leaderboardData.size() > 1) updateTopPlayer(secondPlaceImage, secondPlaceName, secondPlaceScore, leaderboardData.get(1));
        if (leaderboardData.size() > 2) updateTopPlayer(thirdPlaceImage, thirdPlaceName, thirdPlaceScore, leaderboardData.get(2));

        // Update RecyclerView with the remaining players
        List<LeaderboardEntry> remainingPlayers = leaderboardData.subList(Math.min(3, leaderboardData.size()), leaderboardData.size());
        adapter.updateData(remainingPlayers);
    }

    private void updateTopPlayer(ShapeableImageView imageView, TextView nameView, TextView scoreView, LeaderboardEntry entry) {
        Bitmap identicon = IdenticonGenerator.generate(entry.getUserName());
        imageView.setImageBitmap(identicon);

        nameView.setText(entry.getUserName());
        scoreView.setText(String.format("%,d pts", entry.getScore()));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (getIntent().getBooleanExtra("FROM_BOTTOM_NAV", false)) {
            overridePendingTransition(0, 0);
        }
    }
}

//package edu.northeastern.group_10_lyricshuffle;
//
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Looper;
//import android.util.Log;
//import android.view.View;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.google.android.material.bottomnavigation.BottomNavigationView;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import edu.northeastern.group_10_lyricshuffle.adapter.LeaderboardAdapter;
//import edu.northeastern.group_10_lyricshuffle.model.LeaderboardEntry;
//import edu.northeastern.group_10_lyricshuffle.repository.LeaderboardRepository;
//
//public class LeaderboardActivity extends AppCompatActivity {
//    private RecyclerView rankingsRecyclerView;
//    private BottomNavigationView bottomNavigationView;
//    private LeaderboardAdapter adapter;
//    private static final String TAG = "LeaderboardActivity";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_leaderboard);
//
//        // Setup back button
//        findViewById(R.id.backButton).setOnClickListener(v -> onBackPressed());
//
//        // Setup RecyclerView
//        rankingsRecyclerView = findViewById(R.id.rankingsRecyclerView);
//        rankingsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        rankingsRecyclerView.setHasFixedSize(true);
//
//        adapter = new LeaderboardAdapter(new ArrayList<>());
//        rankingsRecyclerView.setAdapter(adapter);
//
//        // Setup bottom navigation
//        bottomNavigationView = findViewById(R.id.bottomNavigation);
//        boolean fromBottomNav = getIntent().getBooleanExtra("FROM_BOTTOM_NAV", false);
//
//        if (fromBottomNav) {
//            bottomNavigationView.setVisibility(View.VISIBLE);
//            bottomNavigationView.setSelectedItemId(R.id.nav_leaderboard);
//            setupBottomNavigation();
//        } else {
//            bottomNavigationView.setVisibility(View.GONE);
//        }
//
//        // Fetch leaderboard data
//        fetchLeaderboardData();
//    }
//
//    private void setupBottomNavigation() {
//        bottomNavigationView.setOnItemSelectedListener(item -> {
//            if (item.getItemId() == R.id.nav_play) {
//                finish();
//                overridePendingTransition(0, 0);
//                return true;
//            } else if (item.getItemId() == R.id.nav_leaderboard) {
//                return true;
//            }
//            return false;
//        });
//    }
//
//    private void fetchLeaderboardData() {
//        new Thread(() -> {
//            try {
//                // Fetch data using repository
//                LeaderboardRepository leaderboardRepository = new LeaderboardRepository();
//                List<LeaderboardEntry> leaderboardData = leaderboardRepository.getTop20Leaderboard();
//
//                // Update the adapter on the main thread
//                new Handler(Looper.getMainLooper()).post(() -> {
//                    adapter.updateData(leaderboardData);
//                });
//            } catch (Exception e) {
//                Log.e(TAG, "Error fetching leaderboard data", e);
//            }
//        }).start();
//    }
//
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        if (getIntent().getBooleanExtra("FROM_BOTTOM_NAV", false)) {
//            overridePendingTransition(0, 0);
//        }
//    }
//}

