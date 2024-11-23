package edu.northeastern.group_10_lyricshuffle.util;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import androidx.core.app.NotificationCompat;
import edu.northeastern.group_10_lyricshuffle.MainActivity;
import edu.northeastern.group_10_lyricshuffle.R;

public class NotificationHelper {
    private static final String CHANNEL_ID = "lyric_shuffle_reminders";
    private static final String CHANNEL_NAME = "LyricShuffle Reminders";
    private static final String CHANNEL_DESCRIPTION = "Daily reminders to play LyricShuffle";

    private static final String[] NOTIFICATION_MESSAGES = {
            "🎵 Time for some musical fun! Can you arrange these lyrics?",
            "🎮 Challenge yourself with today's lyric puzzle!",
            "🏆 Ready to climb the leaderboard? Play now!",
            "🎶 Test your music knowledge with LyricShuffle!",
            "✨ New songs waiting for you in LyricShuffle!",
            "🎯 Think you can get a perfect score today?"
    };

    private final Context context;
    private final NotificationManager notificationManager;
    private final Uri soundUri;

    public NotificationHelper(Context context) {
        this.context = context;
        this.notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        // Using a musical notification sound
        this.soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        createNotificationChannel();
    }

    private void createNotificationChannel() {
        NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
        );

        // Configure channel
        channel.setDescription(CHANNEL_DESCRIPTION);
        channel.enableVibration(true);

        // Set custom sound
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .build();
        channel.setSound(soundUri, audioAttributes);

        // Register channel
        notificationManager.createNotificationChannel(channel);
    }

    public String getRandomMessage() {
        int randomIndex = (int) (Math.random() * NOTIFICATION_MESSAGES.length);
        return NOTIFICATION_MESSAGES[randomIndex];
    }

//    public void testNotificationNow() {
//        String message = getRandomMessage();
//        NotificationCompat.Builder builder = createNotification(message);
//
//        NotificationManager notificationManager =
//                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.notify(1001, builder.build());
//    }

    public NotificationCompat.Builder createNotification(String message) {
        // Create intent for when notification is clicked
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                0,
                intent,
                PendingIntent.FLAG_IMMUTABLE
        );


        // Build notification
        return new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_music) //
                .setContentTitle("LyricShuffle")
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setSound(soundUri)
                .setVibrate(new long[]{0, 250, 250, 250}) // Vibration pattern
                .setContentIntent(pendingIntent);
    }
}