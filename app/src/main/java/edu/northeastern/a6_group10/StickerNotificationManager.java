package edu.northeastern.a6_group10;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Icon;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.bumptech.glide.Glide;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import android.os.Handler;
import android.os.Looper;

public class StickerNotificationManager {
    private static final String TAG = "StickerNotificationMgr";
    private static StickerNotificationManager instance;
    private final Context context;
    private final FirebaseDatabase db;
    private long lastNotificationTime = 0;

    private final ExecutorService executor;
    private final Handler mainHandler;

    private static final long NOTIFICATION_DELAY = 1000; // 1 second minimum delay between notifications

    private StickerNotificationManager(Context context) {
        this.context = context.getApplicationContext();
        this.db = FirebaseDatabase.getInstance();
        this.executor = Executors.newSingleThreadExecutor();
        this.mainHandler = new Handler(Looper.getMainLooper());
        createNotificationChannel();
    }

    public static StickerNotificationManager getInstance(Context context) {
        if (instance == null) {
            instance = new StickerNotificationManager(context);
        }
        return instance;
    }

    private void createNotificationChannel() {
        NotificationChannel channel = new NotificationChannel(
                Constants.STICKER_SERVICE_CHANNEL_ID,
                context.getString(R.string.sticker_channel_name),
                NotificationManager.IMPORTANCE_DEFAULT);
        channel.setDescription(context.getString(R.string.sticker_channel_description));

        NotificationManager notificationManager =
                context.getSystemService(NotificationManager.class);
        if (notificationManager != null) {
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void setupMessageListener(String currentUsername) {
        if (currentUsername.isEmpty()) {
            Log.d(TAG, "Username is empty, can't setup listener");
            return;
        }

        DatabaseReference messagesRef = db.getReference("messages")
                .child(currentUsername)
                .child("history");

        messagesRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, String previousChildName) {
                // Process new message
                handleNewMessage(snapshot);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, String previousChildName) {}

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {}

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, String previousChildName) {}

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "Firebase message listener cancelled: " + error.getMessage());
            }
        });
    }

    private void showStickerNotification(Sticker sticker, String senderName) {
        // Run in background thread
        executor.execute(() -> {
            try {
                // Load image in background
                Bitmap bitmap = null;
                if (sticker.getImageUrl() != null && !sticker.getImageUrl().isEmpty()) {
                    try {
                        bitmap = Glide.with(context)
                                .asBitmap()
                                .load(sticker.getImageUrl())
                                .submit()
                                .get(5, TimeUnit.SECONDS);
                    } catch (Exception e) {
                        Log.e(TAG, "Error loading image: " + e.getMessage());
                    }
                }

                // Create final reference for bitmap to use in lambda
                final Bitmap finalBitmap = bitmap;

                // Switch back to main thread to show notification
                Handler mainHandler = new Handler(Looper.getMainLooper());
                mainHandler.post(() -> {
                    try {
                        NotificationManager notificationManager =
                                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                        if (notificationManager == null) return;

                        Intent intent = new Intent(context, ChatActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent,
                                PendingIntent.FLAG_IMMUTABLE);

                        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,
                                Constants.STICKER_SERVICE_CHANNEL_ID)
                                .setSmallIcon(R.drawable.ic_chat)
                                .setContentTitle("New Sticker from " + senderName)
                                .setContentText("Sent you: " + sticker.getName())
                                .setAutoCancel(true)
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                .setContentIntent(pendingIntent);

                        if (finalBitmap != null) {
                            NotificationCompat.BigPictureStyle style = new NotificationCompat.BigPictureStyle()
                                    .bigPicture(finalBitmap)
                                    .setBigContentTitle("New Sticker from " + senderName)
                                    .setSummaryText("Sent you: " + sticker.getName());

                            style.bigLargeIcon((Bitmap) null);
                            builder.setLargeIcon(finalBitmap)
                                    .setStyle(style);
                        }

                        int notificationId = (int) (System.currentTimeMillis() / 1000);
                        notificationManager.notify(notificationId, builder.build());
                    } catch (Exception e) {
                        Log.e(TAG, "Error showing notification: " + e.getMessage());
                    }
                });
            } catch (Exception e) {
                Log.e(TAG, "Error in notification process: " + e.getMessage());
            }
        });
    }

    private void handleNewMessage(DataSnapshot messageSnapshot) {
        try {
            String senderId = messageSnapshot.child("senderId").getValue(String.class);
            String stickerId = messageSnapshot.child("stickerId").getValue(String.class);

            if (senderId != null && stickerId != null) {
                DatabaseReference stickerRef = db.getReference(Constants.FIREBASE_STICKER)
                        .child(stickerId);

                stickerRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        FirebaseSticker st = snapshot.getValue(FirebaseSticker.class);
                        if (st != null) {
                            Sticker sticker = new Sticker(st.getName(), 0, st.getUrl());
                            showStickerNotification(sticker, senderId);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e(TAG, "Error fetching sticker: " + error.getMessage());
                    }
                });
            }
        } catch (Exception e) {
            Log.e(TAG, "Error handling message: " + e.getMessage());
        }
    }

    // Don't forget to clean up the executor
    public void cleanup() {
        if (executor != null && !executor.isShutdown()) {
            executor.shutdown();
        }
    }
}