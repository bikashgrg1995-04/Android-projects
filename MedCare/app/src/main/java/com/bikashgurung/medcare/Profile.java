package com.bikashgurung.medcare;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.shrikanthravi.customnavigationdrawer2.data.MenuItem;
import com.shrikanthravi.customnavigationdrawer2.widget.SNavigationDrawer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Profile extends AppCompatActivity {

    //Global Declaration
    SNavigationDrawer sNavigationDrawer;
    Class fragmentClass;
    public static Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        String username = getIntent().getStringExtra("username");
        String email = getIntent().getStringExtra("email");
        String fullname = getIntent().getStringExtra("fullname");

        sNavigationDrawer = findViewById(R.id.navigationDrawer);

        //Creating a list of menu Items
        List<MenuItem> menuItems = new ArrayList<>();

        //Use the MenuItem given by this library and not the default one.
        //First parameter is the title of the menu item and then the second parameter is the image which will be the background of the menu item.


        menuItems.add(new MenuItem("Profile", R.mipmap.logo_foreground));
        menuItems.add(new MenuItem("Home",R.mipmap.logo_foreground));
        menuItems.add(new MenuItem("BloodBank",R.mipmap.logo_foreground));
        menuItems.add(new MenuItem("Helpline",R.mipmap.logo_foreground));
        menuItems.add(new MenuItem("AboutUs",R.mipmap.logo_foreground));

        //then add them to navigation drawer
        sNavigationDrawer.setMenuItemList(menuItems);
        fragmentClass =  User.class;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.frameLayout, fragment).commit();
        }

        //Listener to handle the menu item click. It returns the position of the menu item clicked. Based on that you can switch between the fragments.

        sNavigationDrawer.setOnMenuItemClickListener(position -> {
            System.out.println("Position "+position);

            switch (position){
                case 0:{
                    fragmentClass = User.class;
                    break;
                }
                case 1:{
                    fragmentClass = Home.class;
                    break;
                }
                case 2:{
                    fragmentClass = BloodBank.class;
                    break;
                }
                case 3:{
                    fragmentClass = HelpLine.class;
                    break;
                }
                case 4:{
                    fragmentClass = AboutUs.class;
                    break;
                }

            }

            //Listener for drawer events such as opening and closing.
            sNavigationDrawer.setDrawerListener(new SNavigationDrawer.DrawerListener() {

                @Override
                public void onDrawerOpened() {

                }

                @Override
                public void onDrawerOpening(){

                }

                @Override
                public void onDrawerClosing(){
                    System.out.println("Drawer closed");

                    try {
                        fragment = (Fragment) fragmentClass.newInstance();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (fragment != null) {
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.frameLayout, fragment).commit();

                    }
                }

                @Override
                public void onDrawerClosed() {

                }

                @Override
                public void onDrawerStateChanged(int newState) {
                    System.out.println("State "+newState);
                }
            });
        });
    }

    @Override
    public void onBackPressed() {

    }

}