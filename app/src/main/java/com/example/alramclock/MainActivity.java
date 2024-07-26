package com.example.alramclock;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {



    private RecyclerView alarmList;
    private AlarmAdapter alarmAdapter;
    private AlarmDatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        alarmList = findViewById(R.id.alarm_list);
        alarmList.setLayoutManager(new LinearLayoutManager(this));

        db = new AlarmDatabaseHelper(this);
        alarmAdapter = new AlarmAdapter(db.getAllAlarms(),db);
        alarmList.setAdapter(alarmAdapter);


        findViewById(R.id.set_alarm_button).setOnClickListener(v -> {
            startActivity(new Intent(this, SetAlramActivity.class));
        });

        //This is a temp Message

    }



    @Override
    protected void onResume() {
        super.onResume();
        alarmAdapter.updateAlarms(db.getAllAlarms());
    }
}