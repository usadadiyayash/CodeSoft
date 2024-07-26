package com.example.alramclock;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.List;

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder> {
    private List<Alarm> alarms;
    private AlarmDatabaseHelper db;

    public AlarmAdapter(List<Alarm> alarms, AlarmDatabaseHelper db) {
        this.alarms = alarms;
        this.db = db;
    }

    @Override
    public AlarmViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.alarm_item, parent, false);
        return new AlarmViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AlarmViewHolder holder, int position) {
        Alarm alarm = alarms.get(position);
        holder.alarmTime.setText(String.format("%02d:%02d", alarm.getHour(), alarm.getMinute()));
        holder.alarmSwitch.setChecked(alarm.isEnabled());

        holder.alarmSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            alarm.setEnabled(isChecked);
            db.updateAlarm(alarm);
        });



        holder.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                db.deleteAlarm(alarm.getId());

                alarms.remove(alarm);
                notifyDataSetChanged();

                Toast.makeText(view.getContext(), "Alarm Deleted!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return alarms.size();
    }

    public void updateAlarms(List<Alarm> alarms) {
        this.alarms = alarms;
        notifyDataSetChanged();
    }

    static class AlarmViewHolder extends RecyclerView.ViewHolder {
        TextView alarmTime;
        SwitchMaterial alarmSwitch;
        ImageView close;

        AlarmViewHolder(View itemView) {
            super(itemView);
            alarmTime = itemView.findViewById(R.id.alarm_time);
            alarmSwitch = itemView.findViewById(R.id.alarm_switch);
            close = itemView.findViewById(R.id.close);
        }
    }
}
