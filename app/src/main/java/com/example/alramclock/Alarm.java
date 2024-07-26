package com.example.alramclock;

public class Alarm {
    private int id;
    private int hour;
    private int minute;
    private boolean isEnabled;
    private String tone;

    public Alarm(int hour, int minute, boolean isEnabled, String tone) {
        this.hour = hour;
        this.minute = minute;
        this.isEnabled = isEnabled;
        this.tone = tone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public String getTone() {
        return tone;
    }

    public void setTone(String tone) {
        this.tone = tone;
    }
}
