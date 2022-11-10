package com.bikashgurung.stocard;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bikashgurung.stocard.DB.AppDatabase;
import com.bikashgurung.stocard.DB.DestinationModel;
import com.bikashgurung.stocard.DB.StoreModel;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    BottomNavigationView navigation;
    Button geofencing, finalGeo;
    //SwitchMaterial geofenceController;
    LatLng destinationBranches;
    Double final_branch_lat, final_branch_long;


    public static int dpToPx(Context context, int dp) {
        Resources resources = context.getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics()));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        saveBankList();
        saveBankFullAddress();

        navigation = findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                return false;
            }
        });


        new Handler(this.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {

                BadgeDrawable badgeDrawable = navigation.getOrCreateBadge(R.id.offer);
                badgeDrawable.setVisible(true);
                badgeDrawable.setVerticalOffset(dpToPx(MainActivity.this, 3));
                badgeDrawable.setNumber(12);
                badgeDrawable.setBackgroundColor(getResources().getColor(R.color.red_500));
                badgeDrawable.setBadgeTextColor(getResources().getColor(R.color.white));
            }
        }, 1000);
    }

    private void saveBankFullAddress() {
        AppDatabase db = AppDatabase.getDbInstance(this.getApplicationContext());
        if (db.destinationDao().getAll().isEmpty()) {
            db.destinationDao().insert(
                    new DestinationModel("NMB Bank", "Bharatpur", "MCVF+GV4, Bharatpur 44207, Nepal"),
                    new DestinationModel("NMB Bank", "Narayangarh", "MCRF+RVX, Bharatpur 44200, Nepal"),
                    new DestinationModel("NMB Bank", "Home", "MCCM+WQ5, Bharatpur 44207, Nepal"),

                    new DestinationModel("NIC Bank", "Bharatpur NIC", "infront of IRD Office, MCJQ+RVW, Bypass Rd, भरतपुर 44200, Nepal"),
                    new DestinationModel("NIC Bank", "Narayangarh NIC", "MF72+J37, Bharatpur 44200, Nepal"),
                    new DestinationModel("NIC Bank", "Home NIC", "MF82+57V, Bharatpur 44200, Nepal"),

                    new DestinationModel("Everest Bank", "Bharatpur EVEREST", "MCJJ+R6P, Bharatpur 44200, Nepal"),
                    new DestinationModel("Everest Bank", "Narayangarh EVEREST", "MCC3+H25, Bharatpur 44207, Nepal"),
                    new DestinationModel("Everest Bank", "Home EVEREST", "MCC3+J47, Bharatpur 44207, Nepal")

            );
        }
    }


    private void saveBankList() {
        AppDatabase db = AppDatabase.getDbInstance(this.getApplicationContext());
        if (db.storeDao().getAll().isEmpty()) {
            //27.682135251488486,84.4305337449328
            db.storeDao().insert(
                    new StoreModel("NMB Bank", "Bharatpur", "84.43946103928089", "27.68230745157237", "open"),
                    new StoreModel("NMB Bank", "Narayangarh", "84.4248692126116", "27.693632496335216", "open"),
                    new StoreModel("NMB Bank", "Home", "84.4502202", "27.6641806", "Closed"),

                    new StoreModel("NIC Bank", "Bharatpur NIC", "84.43446756002714", "27.672257435411748", "open"),
                    new StoreModel("NIC Bank", "Narayangarh NIC", "84.42467477184502", "27.69217733421856", "open"),
                    new StoreModel("NIC Bank", "Home NIC", "84.45035879056799", "27.66489401994129", "Closed"),

                    new StoreModel("Everest Bank", "Bharatpur EVEREST", "84.4305337449328", "27.682135251488486", "open"),
                    new StoreModel("Everest Bank", "Narayangarh EVEREST", "84.40261085762977", "27.671397641531083", "open"),
                    new StoreModel("Everest Bank", "Home EVEREST", "84.40333516166605", "27.671671401719948", "Closed")

            );
        }
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Back Button is disabled", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Runtime permission
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION

            }, 100);
        }
    }

}