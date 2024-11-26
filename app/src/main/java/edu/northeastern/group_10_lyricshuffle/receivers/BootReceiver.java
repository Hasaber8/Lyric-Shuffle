package edu.northeastern.group_10_lyricshuffle.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import edu.northeastern.group_10_lyricshuffle.util.NotificationScheduler;

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null &&
                intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            NotificationScheduler scheduler = new NotificationScheduler(context);
            scheduler.rescheduleNotificationsAfterBoot();
        }
    }
}
