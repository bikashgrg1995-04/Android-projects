package com.bikashgurung.radar;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.UUID;

import io.radar.sdk.Radar;
import io.radar.sdk.RadarTrackingOptions;

public class MainActivity extends AppCompatActivity {

    SwitchMaterial geofenceController;
    MyRadarReceiver receiver;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        receiver = new MyRadarReceiver();

        recyclerView = findViewById(R.id.recycleView);


        geofenceController = findViewById(R.id.geofenceSwitch);

        SharedPreferences sharedPrefs = getSharedPreferences("Tracking", MODE_PRIVATE);
        geofenceController.setChecked(sharedPrefs.getBoolean("state", true));

        geofenceController.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = getSharedPreferences("Tracking", MODE_PRIVATE).edit();
                if (isChecked) {
                    editor.putBoolean("state", true);
                    editor.apply();

                    checkPermissions();
                    Radar.initialize(MainActivity.this, "prj_test_pk_0b2ba74435d0b678338d3628f17e011a3ade1c7a", receiver, Radar.RadarLocationServicesProvider.GOOGLE);
                    Radar.setDescription("This is my Phone");
                } else {
                    editor.putBoolean("state", false);
                    editor.apply();

                    Radar.stopTracking();
                }
            }
        });

    }

    private void checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                }, 100);
            } else if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                        Manifest.permission.ACCESS_BACKGROUND_LOCATION
                }, 102);
            } else {
                Toast.makeText(this, "Granted", Toast.LENGTH_SHORT).show();

                RadarTrackingOptions trackingOptions = RadarTrackingOptions.CONTINUOUS;
                trackingOptions.getForegroundServiceEnabled();
                Radar.startTracking(trackingOptions);

              /*  Radar.trackOnce(RadarTrackingOptions.RadarTrackingOptionsDesiredAccuracy.HIGH, true, new Radar.RadarTrackCallback() {
                    @Override
                    public void onComplete(@NonNull Radar.RadarStatus radarStatus, @Nullable Location location, @Nullable RadarEvent[] radarEvents, @Nullable RadarUser radarUser) {
                        receiver.onEventsReceived(MainActivity.this, radarEvents, radarUser);
                    }
                });*/
            }
        } else {

            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                }, 100);
            } else {
                Toast.makeText(this, "Granted", Toast.LENGTH_SHORT).show();
                RadarTrackingOptions trackingOptions = RadarTrackingOptions.CONTINUOUS;
                trackingOptions.getForegroundServiceEnabled();
                Radar.startTracking(trackingOptions);
            }
        }

    }

}