package edu.northeastern.a6_group10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FeedActivity extends AppCompatActivity {

    private TextView usernameTextView;

    StickerRecycler adapter;
    List<Sticker> stickerList = new ArrayList<>();

    private static final FirebaseDatabase db = FirebaseDatabase.getInstance();
    private String currentUsername;

    private StickerNotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        // get the current username from the shared preferences
        currentUsername = getSharedPreferences(Constants.LOCAL_DATASTORE_STICKERS, MODE_PRIVATE)
                .getString(Constants.LOCAL_DATASTORE_USERNAME_KEY, "");

        notificationManager = StickerNotificationManager.getInstance(this);

       if (!currentUsername.isEmpty()) {
           notificationManager.setupMessageListener(currentUsername);
       }

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

    public void onSelectSticker(View view) {
        int stickerId = view.getId();
        Intent intent = new Intent(FeedActivity.this, UserProfileListActivity.class);
        intent.putExtra("selectedStickerId", stickerId);
        startActivity(intent); // Start the new activity
    }

    // Add this method to test notifications
    private void testSendNotification() {
        if (currentUsername.isEmpty()) {
            Toast.makeText(this, "No username set", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a reference to the messages path
        DatabaseReference messagesRef = db.getReference("messages")
                .child(currentUsername)
                .child("history");

        // Create a new message with a push key
        DatabaseReference newMessageRef = messagesRef.push();

        // Create the message data
        Map<String, Object> messageData = new HashMap<>();
        messageData.put("senderId", "testUser");
        messageData.put("stickerId", "sad_cat");  // Use an existing sticker ID from your database
        messageData.put("timestamp", ServerValue.TIMESTAMP);

        // Add the test message to Firebase
        newMessageRef.setValue(messageData)
                .addOnSuccessListener(aVoid -> {
                    Log.d("FeedActivity", "Test message added successfully");
                    Toast.makeText(FeedActivity.this, "Test notification sent", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Log.e("FeedActivity", "Error adding test message", e);
                    Toast.makeText(FeedActivity.this, "Failed to send test notification", Toast.LENGTH_SHORT).show();
                });
    }

    // Add this method to handle the button click
    public void onTestNotificationClick(View view) {
        testMultipleStickers();
    }

    // Optional: Add this method to test different stickers
    private void testMultipleStickers() {
        String[] stickerIds = {"facepalm", "huh_cat", "huh_man"};

        // Use Handler for delayed execution
        Handler handler = new Handler(Looper.getMainLooper());

        for (int i = 0; i < stickerIds.length; i++) {
            final String stickerId = stickerIds[i];
            handler.postDelayed(() -> {
                DatabaseReference messagesRef = db.getReference("messages")
                        .child(currentUsername)
                        .child("history");

                DatabaseReference newMessageRef = messagesRef.push();

                Map<String, Object> messageData = new HashMap<>();
                messageData.put("senderId", "testUser" + stickerId);
                messageData.put("stickerId", stickerId);
                messageData.put("timestamp", ServerValue.TIMESTAMP);

                newMessageRef.setValue(messageData)
                        .addOnSuccessListener(aVoid ->
                                Log.d("FeedActivity", "Test message added: " + stickerId))
                        .addOnFailureListener(e ->
                                Log.e("FeedActivity", "Error adding message: " + stickerId, e));

            }, i * 2000); // 2 second delay between each notification
        }
    }
}
