package com.myapplicationdev.android.c347_l12_psnotifymeonwear;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.RemoteInput;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnAdd;
    ListView listView;
    ArrayAdapter arrayAdapter;
    ArrayList<Task> tasks;
    Intent intent , intentReply;
    PendingIntent pendingIntentReply;

    RemoteInput remoteInput;
    @SuppressLint("UnspecifiedImmutableFlag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.btnAdd);
        listView = findViewById(R.id.lv);

        DBHelper dbHelper = new DBHelper(MainActivity.this);
        tasks = dbHelper.getAllTask();
        dbHelper.close();

        arrayAdapter = new ListAdapter(this, R.layout.row, tasks);
        listView.setAdapter(arrayAdapter);

        btnAdd.setOnClickListener(view -> {
            intent = new Intent(MainActivity.this, AddActivity.class);
            startActivity(intent);
        });

         intentReply = new Intent(MainActivity.this,
                ReplyActivity.class);
         pendingIntentReply = PendingIntent.getActivity
                (MainActivity.this, 0, intentReply,
                        PendingIntent.FLAG_UPDATE_CURRENT);

         remoteInput = new RemoteInput.Builder("status")
                .setLabel("Status report")
                .setChoices(new String[]{"Done", "Not yet"})
                .build();

        NotificationCompat.Action action2 = new
                NotificationCompat.Action.Builder(
                R.mipmap.ic_launcher,
                "Reply",
                pendingIntentReply)
                .addRemoteInput(remoteInput)
                .build();

        NotificationCompat.WearableExtender extender = new
                NotificationCompat.WearableExtender();
        extender.addAction(action2);
    }
}