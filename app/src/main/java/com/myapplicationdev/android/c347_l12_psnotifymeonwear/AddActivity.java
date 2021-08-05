package com.myapplicationdev.android.c347_l12_psnotifymeonwear;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.RemoteInput;

import java.util.Calendar;

public class AddActivity extends AppCompatActivity {
    int notificationId = 001; // A unique ID for our notification
    Button btnAddTask, btnCancel;
    EditText etName, etDescription, etRemind;
    int reqCode = 12345;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        btnCancel = findViewById(R.id.btnCancel);
        btnAddTask = findViewById(R.id.btnAddTask);
        etName = findViewById(R.id.etName);
        etDescription = findViewById(R.id.etDescription);
        etRemind = findViewById(R.id.etRemind);

        btnAddTask.setOnClickListener(view -> {
            String dataName = etName.getText().toString();
            String dataDescription = etDescription.getText().toString();
            int dataSeconds = Integer.parseInt(etRemind.getText().toString());
            DBHelper dbh = new DBHelper(AddActivity.this);
            long inserted_id = dbh.addTask(dataName, dataDescription);
            dbh.close();

            if (inserted_id != -1) {
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.SECOND, dataSeconds);

                Intent i = new Intent(AddActivity.this, ScheduledNotificationReceiver.class);
                @SuppressLint("UnspecifiedImmutableFlag") PendingIntent pendingIntent = PendingIntent.getBroadcast(AddActivity.this, reqCode, i, PendingIntent.FLAG_CANCEL_CURRENT);

                AlarmManager am = (AlarmManager)getSystemService(Activity.ALARM_SERVICE);
                am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);

                Toast.makeText(AddActivity.this, "Task added!", Toast.LENGTH_SHORT).show();
                finish();
            }


            NotificationManager nm = (NotificationManager)
                    getSystemService(Context.NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new
                        NotificationChannel("default", "Default Channel",
                        NotificationManager.IMPORTANCE_DEFAULT);

                channel.setDescription("This is for default notification");
                nm.createNotificationChannel(channel);
            }

            Intent intent = new Intent(AddActivity.this, MainActivity.class);
            @SuppressLint("UnspecifiedImmutableFlag") PendingIntent pendingIntent =
                    PendingIntent.getActivity(AddActivity.this, 0,
                            intent, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Action action = new
                    NotificationCompat.Action.Builder(
                    R.mipmap.ic_launcher,
                    "This is an Action",
                    pendingIntent).build();

            Intent intentreply = new Intent(AddActivity.this,
                    ReplyActivity.class);
            @SuppressLint("UnspecifiedImmutableFlag") PendingIntent pendingIntentReply = PendingIntent.getActivity
                    (AddActivity.this, 0, intentreply,
                            PendingIntent.FLAG_UPDATE_CURRENT);

            RemoteInput ri = new RemoteInput.Builder("status")
                    .setLabel("Status report")
                    .setChoices(new String [] {"Done", "Not yet"})
                    .build();

            NotificationCompat.Action action2 = new
                    NotificationCompat.Action.Builder(
                    R.mipmap.ic_launcher,
                    "Reply",
                    pendingIntentReply)
                    .addRemoteInput(ri)
                    .build();

            NotificationCompat.WearableExtender extender = new
                    NotificationCompat.WearableExtender();
            extender.addAction(action);
            extender.addAction(action2);

            String text = getString(R.string.basic_notify_msg);
            String title = getString(R.string.notification_title);

            NotificationCompat.Builder builder = new
                    NotificationCompat.Builder(AddActivity.this, "default");
            builder.setContentText(text);
            builder.setContentTitle(title);
            builder.setSmallIcon(android.R.drawable.btn_star_big_off);

            // Attach the action for Wear notification created above
            builder.extend(extender);

            Notification notification = builder.build();

            nm.notify(notificationId, notification);
        });

        btnCancel.setOnClickListener(view -> finish());
    }
}