package com.example.alramclock;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TimePicker;
import java.util.Calendar;


public class SetAlramActivity extends AppCompatActivity {
 private TimePicker timePicker;
    private Button setAlarmButton;
    private AlarmDatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_set_alram);

        timePicker = findViewById(R.id.time_picker);
        setAlarmButton = findViewById(R.id.set_alarm_button);
        db = new AlarmDatabaseHelper(this);

                setAlarmButton.setOnClickListener(view -> setAlarm());


    }

    @SuppressLint("ScheduleExactAlarm")
    private void setAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
        calendar.set(Calendar.MINUTE, timePicker.getMinute());
        calendar.set(Calendar.SECOND, 0);

        if (alarmManager != null) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }

        int hour = timePicker.getHour();
            int minute = timePicker.getMinute();
            // Save alarm to database
            db.addAlarm(new Alarm(hour, minute, true, "default_tone"));
            finish();
    }
}