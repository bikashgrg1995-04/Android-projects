package com.bikashgurung.radar;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bikashgurung.radar.Helper.GeofenceAdapter;
import com.bikashgurung.radar.Retrofit.Model.List_Geofences.Example;
import com.bikashgurung.radar.Retrofit.Model.List_Geofences.Geofence;
import com.bikashgurung.radar.Retrofit.RetrofitClient;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.ArrayList;
import java.util.List;

import io.radar.sdk.Radar;
import io.radar.sdk.RadarTrackingOptions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    SwitchMaterial geofenceController;
    MyRadarReceiver receiver;
    RecyclerView recyclerView;
    MaterialButton create;
    SwipeRefreshLayout swipeRefreshLayout;

    List<Geofence> geofenceList = new ArrayList<>();
    GeofenceAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        receiver = new MyRadarReceiver();

        recyclerView = findViewById(R.id.recycleView);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

        // SetOnRefreshListener on SwipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (recyclerView ==null) {

                    fetchGeofenceList();
                    swipeRefreshLayout.setRefreshing(false);
                }else
                {
                    Toast.makeText(MainActivity.this, "Already updated", Toast.LENGTH_SHORT).show();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });

        adapter = new GeofenceAdapter(geofenceList);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapter);

        fetchGeofenceList();

        /*create = findViewById(R.id.create);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(MainActivity.this, "Creating Geofences", Toast.LENGTH_SHORT).show();

            }
        });*/


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

    private void fetchGeofenceList() {

        Call<Example> call = RetrofitClient.getInstance().getApi().getGeofenceList();
        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {

                for (int i = 0; i < response.body().getGeofences().size(); i++) {
                    System.out.println("Response : " + response.body().getGeofences().get(i));

                    geofenceList.add(response.body().getGeofences().get(i));
                }

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {

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