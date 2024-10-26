package edu.northeastern.a6_group10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FeedActivity extends AppCompatActivity {

    private TextView usernameTextView;

    StickerRecycler adapter;
    List<Sticker> stickerList = new ArrayList<>();

    private static final FirebaseDatabase db = FirebaseDatabase.getInstance();
    private String currentUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        // get the current username from the shared preferences
        currentUsername = getSharedPreferences(Constants.LOCAL_DATASTORE_STICKERS, MODE_PRIVATE)
                .getString(Constants.LOCAL_DATASTORE_USERNAME_KEY, "");

        // dummy notification
        createNotificationChannel();
        showNotification();

        usernameTextView = findViewById(R.id.userNameTextView);
        usernameTextView.setText(currentUsername);

        RecyclerView stickerRecyclerView = findViewById(R.id.stickerRecyclerView);
        stickerRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Set the adapter
        adapter = new StickerRecycler(stickerList);
        stickerRecyclerView.setAdapter(adapter);

        BottomNavigationView bottomNavigationMenuView = findViewById(R.id.bottomNavigationFeed);
        bottomNavigationMenuView.setSelectedItemId(R.id.nav_stats);

        bottomNavigationMenuView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_chats) {
                Intent intent = new Intent(FeedActivity.this, ChatActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                overridePendingTransition(0, 0);
                return false; //so that this does not remain selected when the activity comes into focus
            } else if (item.getItemId() == R.id.nav_stats) {
                // We're already on FeedActivity, so no action is needed
                return true;
            }
            return false;
        });

        fetchStickerData();
    }

    private void fetchStickerData() {
        Log.d("FeedActivity", "Fetching sticker data from Firebase");

        if (currentUsername.isEmpty()) {
            Log.d("FeedActivity", "Username is empty");
            Toast.makeText(this, "Username is empty", Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseReference stickersRef = db.getReference(Constants.FIREBASE_STICKER);
        DatabaseReference statsRef = db.getReference(Constants.FIREBASE_KEY_STATS + "/" + currentUsername);

        Log.d("FeedActivity", "Stats Reference Path: " + statsRef);

        statsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot statsSnapshot) {
                Log.d("FeedActivity", "Stats data changed. Number of stats entries: " + statsSnapshot.getChildrenCount());

                // Create a map to store counts for each sticker
                final java.util.Map<String, Integer> stickerCounts = new HashMap<>();

                for (DataSnapshot statChild : statsSnapshot.getChildren()) {
                    String stickerId = statChild.getKey();
                    Integer count = statChild.getValue(Integer.class);
                    stickerCounts.put(stickerId, count);
                    Log.d("FeedActivity", "Found count for sticker: " + stickerId + " = " + count);
                }

                stickersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot urlSnapshot) {
                        List<Sticker> finalStickerList = new ArrayList<>();

                        for (DataSnapshot urlChild : urlSnapshot.getChildren()) {
                            String stickerId = urlChild.getKey();
                            FirebaseSticker st = urlChild.getValue(FirebaseSticker.class);
                            if (st == null) {
                                Log.e("FeedActivity", "Failed to read sticker data for ID: " + stickerId);
                                Toast.makeText(FeedActivity.this, "Failed to read sticker data for ID: "
                                                + stickerId, Toast.LENGTH_SHORT).show();
                                continue;
                            }
                            String imageUrl = st.getUrl();
                            String stickerName = st.getName();

                            int count = stickerCounts.getOrDefault(stickerId, 0);

                            Log.d("FeedActivity", "Creating sticker - ID: " + stickerId +
                                    ", URL: " + imageUrl +
                                    ", Count: " + count);

                            if (imageUrl != null && !imageUrl.isEmpty()) {
                                finalStickerList.add(new Sticker(stickerName, count, imageUrl));
                            }
                        }

                        Log.d("FeedActivity", "Updating adapter with " + finalStickerList.size() + " stickers");
                        for (Sticker sticker : finalStickerList) {
                            Log.d("FeedActivity", "Final sticker - Name: " + sticker.getName() +
                                    ", Count: " + sticker.getCount() +
                                    ", URL: " + sticker.getImageUrl());
                        }

                        adapter.updateStickerList(finalStickerList);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.e("FeedActivity", "Failed to read URL data", databaseError.toException());
                        Toast.makeText(FeedActivity.this, "Failed to read sticker URLs", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FeedActivity", "Failed to read stats data", error.toException());
                Toast.makeText(FeedActivity.this, "Failed to read sticker counts", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createNotificationChannel() {
        NotificationChannel channel = new NotificationChannel(
                Constants.STICKER_SERVICE_CHANNEL_ID,
                getString(R.string.stickerChannelName),
                NotificationManager.IMPORTANCE_DEFAULT
        );
        channel.setDescription(getString(R.string.stickerChannelDescription));

        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

    private void showNotification() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, Constants.STICKER_SERVICE_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_chat)
                .setContentTitle(getString(R.string.stickerChannelName))
                .setContentText(getString(R.string.stickerChannelDescription))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        notificationManager.notify(1, notificationBuilder.build());
    }

    public void onSelectSticker(View view) {
        int stickerId = view.getId();
        Intent intent = new Intent(FeedActivity.this, UserProfileListActivity.class);
        intent.putExtra("selectedStickerId", stickerId);
        startActivity(intent); // Start the new activity
    }
}
