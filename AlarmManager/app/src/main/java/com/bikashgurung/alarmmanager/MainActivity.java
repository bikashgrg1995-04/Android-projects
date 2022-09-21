package com.bikashgurung.alarmmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bikashgurung.alarmmanager.databinding.ActivityMainBinding;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MaterialTimePicker picker;
    private Calendar calendar;
    private AlarmManager alarmmanager;
    private PendingIntent pendingIntent;

    MaterialButton set, delete;
    TextView showTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        createNotificationChannel();

        showTime = findViewById(R.id.time);
        set = findViewById(R.id.btnSet);
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePicker();
            }
        });

        delete = findViewById(R.id.btnDelete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteAlarm();
            }
        });
    }

    private void createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "Bikash";
            String description = "Channel for set Alarm";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("Bikash", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void showTimePicker() {
        picker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(12)
                .setMinute(53)
                .setTitleText("Select Alarm Time.")
                .build();

        picker.show(getSupportFragmentManager(), "Bikash");

        picker.addOnPositiveButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String msg;

                if (picker.getHour()>12){
                     msg = picker.getHour()-12 + " : " + picker.getMinute() + " PM";
                    showTime.setText(msg);
                }else if (picker.getHour() == 12){
                    msg = picker.getHour()+" : "+picker.getMinute() + " PM";
                    showTime.setText(msg);
                }else if (picker.getHour() == 00){
                    msg = "12"+" : "+picker.getMinute() + " AM";
                    showTime.setText(msg);
                }else{
                    msg = picker.getHour()+" : "+picker.getMinute() + " AM";
                    showTime.setText(msg);
                }

                calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, picker.getHour());
                calendar.set(Calendar.MINUTE, picker.getMinute());
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);

                setAlarm(msg);
            }
        });
    }

    private void setAlarm(String msg) {
        alarmmanager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, pendingIntent.FLAG_IMMUTABLE);
        alarmmanager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                /*AlarmManager.INTERVAL_DAY,*/ pendingIntent);
        Toast.makeText(this, "Alarm Set successfully at " + msg , Toast.LENGTH_SHORT).show();
    }

    private void deleteAlarm() {
    }
}