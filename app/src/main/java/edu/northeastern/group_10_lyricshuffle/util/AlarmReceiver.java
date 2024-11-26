package edu.northeastern.group_10_lyricshuffle.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.app.NotificationManager;
import androidx.core.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {
    private static final int NOTIFICATION_ID = 1001;

    @Override
    public void onReceive(Context context, Intent intent) {
        // Create NotificationHelper instance
        NotificationHelper notificationHelper = new NotificationHelper(context);

        // Get random message and create notification
        String message = notificationHelper.getRandomMessage();
        NotificationCompat.Builder builder = notificationHelper.createNotification(message);

        // Show notification
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, builder.build());

        // Schedule next notification
        NotificationScheduler scheduler = new NotificationScheduler(context);
        scheduler.scheduleAllNotifications();
    }
}