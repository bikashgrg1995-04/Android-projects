package com.bikashgurung.medcare;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;

import com.bikashgurung.medcare.Adapters.ShortcutAdapter;
import com.bikashgurung.medcare.Models.Shortcut;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

   SearchView search;
   ImageView notification, settings;
   GridView shortcut;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.home_fragment, container, false);

        shortcut = view.findViewById(R.id.shortcut_view);
        ArrayList<Shortcut> shortcutsModelArrayList = new ArrayList<Shortcut>();

        //this data must be added to db and fetch on start
        //and check status, if true then add to gridview and if false, ignore (do not list) them

        shortcutsModelArrayList.add(new Shortcut("Blood Bank", R.drawable.user_icon, true));
        shortcutsModelArrayList.add(new Shortcut("Doctors", R.drawable.user_icon, true));
        shortcutsModelArrayList.add(new Shortcut("Hospitals", R.drawable.user_icon, false));
        shortcutsModelArrayList.add(new Shortcut("Track", R.drawable.user_icon, false));

        ShortcutAdapter shortcutAdapter = new ShortcutAdapter(view.getContext(), shortcutsModelArrayList);
        shortcut.setAdapter(shortcutAdapter);

        search = view.findViewById(R.id.search);

        notification = view.findViewById(R.id.notification);
        settings = view.findViewById(R.id.settings);

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NotificationActivity.class);
                startActivity(intent);
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

}