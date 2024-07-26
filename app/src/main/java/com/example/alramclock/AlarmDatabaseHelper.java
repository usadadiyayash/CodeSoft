package com.example.alramclock;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class AlarmDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "alarms.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_ALARMS = "alarms";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_HOUR = "hour";
    private static final String COLUMN_MINUTE = "minute";
    private static final String COLUMN_ENABLED = "isEnabled";
    private static final String COLUMN_TONE = "tone";

    public AlarmDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ALARMS_TABLE = "CREATE TABLE " + TABLE_ALARMS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_HOUR + " INTEGER,"
                + COLUMN_MINUTE + " INTEGER,"
                + COLUMN_ENABLED + " INTEGER,"
                + COLUMN_TONE + " TEXT" + ")";
        db.execSQL(CREATE_ALARMS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ALARMS);
        onCreate(db);
    }

    public void addAlarm(Alarm alarm) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_HOUR, alarm.getHour());
        values.put(COLUMN_MINUTE, alarm.getMinute());
        values.put(COLUMN_ENABLED, alarm.isEnabled() ? 1 : 0);
        values.put(COLUMN_TONE, alarm.getTone());
        db.insert(TABLE_ALARMS, null, values);
        db.close();
    }

    public List<Alarm> getAllAlarms() {
        List<Alarm> alarms = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ALARMS, null);
        if (cursor.moveToFirst()) {
            do {
                Alarm alarm = new Alarm(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_HOUR)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MINUTE)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ENABLED)) == 1,
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TONE))
                );
                alarm.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                alarms.add(alarm);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return alarms;
    }

    public void updateAlarm(Alarm alarm) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_HOUR, alarm.getHour());
        values.put(COLUMN_MINUTE, alarm.getMinute());
        values.put(COLUMN_ENABLED, alarm.isEnabled() ? 1 : 0);
        values.put(COLUMN_TONE, alarm.getTone());
        db.update(TABLE_ALARMS, values, COLUMN_ID + " = ?", new String[]{String.valueOf(alarm.getId())});
        db.close();
    }

     public void deleteAlarm(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ALARMS, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }
}
