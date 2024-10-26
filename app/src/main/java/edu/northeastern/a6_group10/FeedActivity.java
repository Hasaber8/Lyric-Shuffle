package edu.northeastern.a6_group10;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class FeedActivity extends AppCompatActivity {

    public static final String STICKER_SERVICE_CHANNEL_ID = "stickItToEm";
    private TextView usernameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        //dummy notification
        createNotificationChannel();
        showNotification();
        //dummy username
        usernameTextView = findViewById(R.id.userNameTextView);
        usernameTextView.setText("Username");

        RecyclerView stickerRecyclerView = findViewById(R.id.stickerRecyclerView);
        stickerRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // TODO: Temp data. Fetch stickers and add sticker image for each
        List<Sticker> stickerList = new ArrayList<>();
        stickerList.add(new Sticker( "Funny Cat", 10));
        stickerList.add(new Sticker("Cute Dog", 5));
        stickerList.add(new Sticker("Cool Emoji", 12));
        stickerList.add(new Sticker( "Funny Cat", 10));
        stickerList.add(new Sticker("Cute Dog", 5));
        stickerList.add(new Sticker("Cool Emoji", 12));
        stickerList.add(new Sticker( "Funny Cat", 10));
        stickerList.add(new Sticker("Cute Dog", 5));
        stickerList.add(new Sticker("Cool Emoji", 12));
        stickerList.add(new Sticker( "Funny Cat", 10));
        stickerList.add(new Sticker("Cute Dog", 5));
        stickerList.add(new Sticker("Cool Emoji", 12));
        stickerList.add(new Sticker( "Funny Cat", 10));
        stickerList.add(new Sticker("Cute Dog", 5));
        stickerList.add(new Sticker("Cool Emoji", 12));

        // Set the adapter
        StickerRecycler adapter = new StickerRecycler(stickerList);
        stickerRecyclerView.setAdapter(adapter);

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
