package com.myapplicationdev.android.c347_l12_psnotifymeonwear;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lvTask;
    Button btnAddTask;
    ArrayAdapter aa;
    ArrayList<Task> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvTask = findViewById(R.id.lvTask);
        btnAddTask = findViewById(R.id.btnAddTask);

        btnAddTask.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, AddActivity.class);
            startActivity(i);
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        taskList = new ArrayList<>();

        DBHelper dbh = new DBHelper(MainActivity.this);
        taskList.addAll(dbh.getAllTasks());
        dbh.close();

        aa = new TaskAdapter(MainActivity.this, R.layout.row, taskList);
        lvTask.setAdapter(aa);
    }
}