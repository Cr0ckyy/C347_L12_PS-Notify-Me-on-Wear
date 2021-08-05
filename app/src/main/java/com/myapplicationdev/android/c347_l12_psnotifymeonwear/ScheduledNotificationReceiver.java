package com.myapplicationdev.android.c347_l12_psnotifymeonwear;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class ScheduledNotificationReceiver extends BroadcastReceiver {
    int reqCode = 1234;
    Intent intent1;
    Notification notification;

    @Override
    public void onReceive(Context context, Intent intent) {
        String taskName = intent.getStringExtra("name");
//        String desc = intent.getStringExtra("desc");
        // TODO: This method is called when the BroadcastReceiver is receiving
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("default", "Default Channel", NotificationManager.IMPORTANCE_DEFAULT);

            channel.setDescription("This is for default notification");
            notificationManager.createNotificationChannel(channel);
        }

        intent1 = new Intent(context, MainActivity.class);
        @SuppressLint("UnspecifiedImmutableFlag") PendingIntent pIntent = PendingIntent.getActivity(context, reqCode, intent1, PendingIntent.FLAG_CANCEL_CURRENT);


        NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle();


        NotificationCompat.Builder NotificationBuilder = new NotificationCompat.Builder(context, "default");
        NotificationBuilder.setContentTitle("Task");
        NotificationBuilder.setContentText(taskName);
        NotificationBuilder.setSmallIcon(android.R.drawable.ic_dialog_info);
        NotificationBuilder.setStyle(bigPictureStyle);
        NotificationBuilder.setContentIntent(pIntent);
        NotificationBuilder.setAutoCancel(true);

        notification = NotificationBuilder.build();
        notificationManager.notify(123, notification);

    }
}