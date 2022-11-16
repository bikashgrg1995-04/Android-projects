package com.bikashgurung.medcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment = new HomeFragment();
    ServiceFragment serviceFragment = new ServiceFragment();
    About_us_Fragment aboutUsFragment = new About_us_Fragment();
    AccountFragment accountFragment = new AccountFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, homeFragment).commit();
                        return true;

                    case R.id.med_services:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, serviceFragment).commit();
                        return true;

                    case R.id.about_us:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, aboutUsFragment).commit();
                        return true;

                    case R.id.account:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, accountFragment).commit();
                        return true;
                }
                return false;
            }
        });

    }
}