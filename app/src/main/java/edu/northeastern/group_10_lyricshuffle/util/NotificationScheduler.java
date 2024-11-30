package edu.northeastern.group_10_lyricshuffle.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.core.content.ContextCompat;
import android.content.pm.PackageManager;
import android.util.Log;
import java.util.Calendar;
import java.util.Random;

public class NotificationScheduler {
    private static final String TAG = "NotificationScheduler";
    private final Context context;
    private final AlarmManager alarmManager;
    private final Random random;

    // Time windows for notifications (in 24-hour format)
    private static final int MORNING_START = 9;    // 9 AM
    private static final int MORNING_END = 11;     // 11 AM
    private static final int AFTERNOON_START = 14; // 2 PM
    private static final int AFTERNOON_END = 16;   // 4 PM
    private static final int EVENING_START = 19;   // 7 PM
    private static final int EVENING_END = 21;     // 9 PM

    // Request codes for different time windows
    private static final int MORNING_REQUEST_CODE = 100;
    private static final int AFTERNOON_REQUEST_CODE = 101;
    private static final int EVENING_REQUEST_CODE = 102;

    public NotificationScheduler(Context context) {
        this.context = context;
        this.alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        this.random = new Random();
    }

    /**
     * Schedule all notifications for the day
     */
    public void scheduleAllNotifications() {
        Log.d(TAG, "Checking and scheduling notifications if needed");

        Calendar now = Calendar.getInstance();
        int currentHour = now.get(Calendar.HOUR_OF_DAY);

        // For each time window, we only schedule if:
        // 1. It's before that window's end time
        // 2. We don't already have a notification scheduled for that window

        if (currentHour < MORNING_END && !isNotificationScheduled(MORNING_REQUEST_CODE)) {
            scheduleNotificationForWindow(MORNING_START, MORNING_END, MORNING_REQUEST_CODE);
            Log.d(TAG, "Scheduled morning notification");
        }

        if (currentHour < AFTERNOON_END && !isNotificationScheduled(AFTERNOON_REQUEST_CODE)) {
            scheduleNotificationForWindow(AFTERNOON_START, AFTERNOON_END, AFTERNOON_REQUEST_CODE);
            Log.d(TAG, "Scheduled afternoon notification");
        }

        if (currentHour < EVENING_END && !isNotificationScheduled(EVENING_REQUEST_CODE)) {
            scheduleNotificationForWindow(EVENING_START, EVENING_END, EVENING_REQUEST_CODE);
            Log.d(TAG, "Scheduled evening notification");
        }

        // Schedule next day's notifications only if we're past all windows AND don't have tomorrow's scheduled
        if (currentHour >= EVENING_END && !areTomorrowNotificationsScheduled()) {
            scheduleNextDayNotifications();
            Log.d(TAG, "Scheduled notifications for next day");
        }
    }

    /**
     * Check if a notification is already scheduled
     */
    private boolean isNotificationScheduled(int requestCode) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        return PendingIntent.getBroadcast(
                context,
                requestCode,
                intent,
                PendingIntent.FLAG_NO_CREATE | PendingIntent.FLAG_IMMUTABLE
        ) != null;
    }

    private boolean areTomorrowNotificationsScheduled() {
        // Check if any of tomorrow's notifications are scheduled
        return isNotificationScheduled(MORNING_REQUEST_CODE) ||
                isNotificationScheduled(AFTERNOON_REQUEST_CODE) ||
                isNotificationScheduled(EVENING_REQUEST_CODE);
    }
    private void scheduleNextDayNotifications() {
        Calendar tomorrow = Calendar.getInstance();
        tomorrow.add(Calendar.DAY_OF_YEAR, 1);
        tomorrow.set(Calendar.HOUR_OF_DAY, 0);
        tomorrow.set(Calendar.MINUTE, 0);
        tomorrow.set(Calendar.SECOND, 0);
        tomorrow.set(Calendar.MILLISECOND, 0);

        scheduleNotificationForWindow(MORNING_START, MORNING_END, MORNING_REQUEST_CODE, tomorrow);
        scheduleNotificationForWindow(AFTERNOON_START, AFTERNOON_END, AFTERNOON_REQUEST_CODE, tomorrow);
        scheduleNotificationForWindow(EVENING_START, EVENING_END, EVENING_REQUEST_CODE, tomorrow);
    }

    private void scheduleNotificationForWindow(int startHour, int endHour, int requestCode) {
        scheduleNotificationForWindow(startHour, endHour, requestCode, Calendar.getInstance());
    }

    private void scheduleNotificationForWindow(int startHour, int endHour, int requestCode, Calendar baseDate) {
        Calendar notificationTime = getRandomTimeInWindow(baseDate, startHour, endHour);

        if (notificationTime.getTimeInMillis() > System.currentTimeMillis()) {
            scheduleNotification(notificationTime.getTimeInMillis(), requestCode);
        }
    }

    private Calendar getRandomTimeInWindow(Calendar baseDate, int startHour, int endHour) {
        Calendar calendar = (Calendar) baseDate.clone();

        // Add some randomness within the time window
        int windowSizeInMinutes = (endHour - startHour) * 60;
        int randomMinutesOffset = random.nextInt(windowSizeInMinutes);

        calendar.set(Calendar.HOUR_OF_DAY, startHour);
        calendar.set(Calendar.MINUTE, 0);
        calendar.add(Calendar.MINUTE, randomMinutesOffset);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar;
    }

    private void scheduleNotification(long triggerTime, int requestCode) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                requestCode,
                intent,
                PendingIntent.FLAG_IMMUTABLE
        );

        alarmManager.setAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                triggerTime,
                pendingIntent
        );

        Log.d(TAG, "Scheduled notification for: " + new java.util.Date(triggerTime).toString());
    }

    public void cancelAllNotifications() {
        cancelNotification(MORNING_REQUEST_CODE);
        cancelNotification(AFTERNOON_REQUEST_CODE);
        cancelNotification(EVENING_REQUEST_CODE);
    }

    private void cancelNotification(int requestCode) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                requestCode,
                intent,
                PendingIntent.FLAG_IMMUTABLE
        );
        alarmManager.cancel(pendingIntent);
    }

    /**
     * Check if notifications are enabled for the app
     */
    public boolean areNotificationsEnabled() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return ContextCompat.checkSelfPermission(context,
                    android.Manifest.permission.POST_NOTIFICATIONS)
                    == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }

    /**
     * Reschedule notifications after device reboot
     */
    public void rescheduleNotificationsAfterBoot() {
        if (areNotificationsEnabled()) {
            scheduleAllNotifications();
        }
    }
}