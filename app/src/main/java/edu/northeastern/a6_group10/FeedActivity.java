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

import java.util.ArrayList;
import java.util.List;

public class FeedActivity extends AppCompatActivity {

    public static final String STICKER_SERVICE_CHANNEL_ID = "stickItToEm";
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

        // TODO: Temp data. Fetch stickers and add sticker image for each
//        List<Sticker> stickerList = new ArrayList<>();
//        stickerList.add(new Sticker( "Funny Cat", 10));
//        stickerList.add(new Sticker("Cute Dog", 5));
//        stickerList.add(new Sticker("Cool Emoji", 12));
//        stickerList.add(new Sticker( "Funny Cat", 10));
//        stickerList.add(new Sticker("Cute Dog", 5));
//        stickerList.add(new Sticker("Cool Emoji", 12));
//        stickerList.add(new Sticker( "Funny Cat", 10));
//        stickerList.add(new Sticker("Cute Dog", 5));
//        stickerList.add(new Sticker("Cool Emoji", 12));
//        stickerList.add(new Sticker( "Funny Cat", 10));
//        stickerList.add(new Sticker("Cute Dog", 5));
//        stickerList.add(new Sticker("Cool Emoji", 12));
//        stickerList.add(new Sticker( "Funny Cat", 10));
//        stickerList.add(new Sticker("Cute Dog", 5));
//        stickerList.add(new Sticker("Cool Emoji", 12));

        // Set the adapter
        adapter = new StickerRecycler(stickerList);
        stickerRecyclerView.setAdapter(adapter);

        fetchStickerListFromDatabase();
    }

    private void fetchStickerListFromDatabase() {
        Log.d("FeedActivity", "Fetching sticker list from Firebase Database");
        DatabaseReference stickerStatsRef = db.getReference(Constants.FIREBASE_KEY_STATS + "/" + currentUsername);

        if (currentUsername.isEmpty()) {
            Log.d("FeedActivity", "Username is empty");
            Toast.makeText(this, "Username is empty", Toast.LENGTH_SHORT).show();
            return;
        }

        stickerStatsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("FeedActivity", "Successfully read sticker list from Firebase Database");
                List<Sticker> updatedStickerList = new ArrayList<>();
                for (DataSnapshot child : snapshot.getChildren()) {
                    Log.d("FeedActivity", "Sticker ID: " + child.getKey() + ", Count: " + child.getValue(Integer.class));
                    String stickerId = child.getKey();
                    int stickerCount = child.getValue(Integer.class);
                    updatedStickerList.add(new Sticker(stickerId, stickerCount));
                }
                adapter.updateStickerList(updatedStickerList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("FeedActivity", "Failed to read sticker list from Firebase Database");
                Toast.makeText(FeedActivity.this, "Failed to read sticker list from the Database", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    STICKER_SERVICE_CHANNEL_ID,
                    getString(R.string.stickerChannelName),
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            channel.setDescription(getString(R.string.stickerChannelDescription));

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void showNotification() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, STICKER_SERVICE_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_chat)
                .setContentTitle(getString(R.string.stickerChannelName))
                .setContentText(getString(R.string.stickerChannelDescription))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        notificationManager.notify(1, notificationBuilder.build());
    }

    public void onSelectSticker(View view){
        int stickerId = view.getId();
        Intent intent = new Intent(FeedActivity.this, UserProfileListActivity.class);
        intent.putExtra("selectedStickerId", stickerId);
        startActivity(intent); // Start the new activity
    }
    public void onClickChatTab(View view){
        Intent intent = new Intent(FeedActivity.this, FeedActivity.class);
        startActivity(intent); // Start the new activity
    }

    public void onClickStatTab(View view){
        Intent intent = new Intent(FeedActivity.this, FeedActivity.class);
        startActivity(intent); // Start the new activity
    }

}
