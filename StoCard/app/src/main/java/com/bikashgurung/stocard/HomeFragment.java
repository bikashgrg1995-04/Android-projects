package com.bikashgurung.stocard;

import static android.content.Context.MODE_PRIVATE;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bikashgurung.stocard.DB.AppDatabase;
import com.bikashgurung.stocard.DB.DBModel;
import com.bikashgurung.stocard.Receiver.MyRadarReceiver;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.ArrayList;
import java.util.List;

import io.radar.sdk.Radar;
import io.radar.sdk.RadarTrackingOptions;

public class HomeFragment extends Fragment {

    RecyclerView storyList, cardGrid;
    MaterialButton addCard;
    SearchView searchView;
    TextView text, latitudeTextView, longitudeTextView;

    List<ListAllModel> list = new ArrayList<>();
    List<DBModel> cardList = new ArrayList<>();

    StoryAdapter adapter1;
    ImageView imageView, transactionIcon;
    LinearLayout storyLayout;
    SwitchMaterial geofenceController;
    MyRadarReceiver receiver;
    private CardAdapter cardAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        receiver = new MyRadarReceiver();

        storyLayout = view.findViewById(R.id.ll);

        imageView = view.findViewById(R.id.titleImage);
        transactionIcon = view.findViewById(R.id.transaction);


        text = view.findViewById(R.id.textHome);
        searchView = view.findViewById(R.id.home_search_bar);
        searchView.clearFocus();

        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    imageView.setVisibility(View.GONE);
                    transactionIcon.setVisibility(View.GONE);
                    storyLayout.setVisibility(View.GONE);
                    text.setVisibility(View.GONE);
                } else {
                    imageView.setVisibility(View.VISIBLE);
                    transactionIcon.setVisibility(View.VISIBLE);
                    storyLayout.setVisibility(View.VISIBLE);
                    text.setVisibility(View.VISIBLE);
                }
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return false;
            }

        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                //list all added card back to initial stage before search

                return false;
            }
        });

        addCard = view.findViewById(R.id.btnAdd);
        addCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddCards.class);
                startActivity(intent);
            }
        });

        cardGrid = view.findViewById(R.id.card_grid);
        listAddedCards();

        storyList = view.findViewById(R.id.storyList);
        listStory();

        loadUserList();

        latitudeTextView = view.findViewById(R.id.latTextView);
        longitudeTextView = view.findViewById(R.id.lonTextView);


        geofenceController = view.findViewById(R.id.geofenceSwitch);

        SharedPreferences sharedPrefs = requireActivity().getSharedPreferences("Tracking", MODE_PRIVATE);
        geofenceController.setChecked(sharedPrefs.getBoolean("state", false));

        geofenceController.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = requireActivity().getSharedPreferences("Tracking", MODE_PRIVATE).edit();
                if (isChecked) {
                    editor.putBoolean("state", true);
                    editor.apply();

                    startService();
                    Radar.initialize(getContext(), "prj_test_pk_0b2ba74435d0b678338d3628f17e011a3ade1c7a", receiver, Radar.RadarLocationServicesProvider.GOOGLE);
                    Radar.setDescription("This is my Phone");

                } else {
                    editor.putBoolean("state", false);
                    editor.apply();

                    stopService();
                }
            }
        });


        return view;
    }

    private void startService() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(requireActivity(), new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                }, 100);
            } else if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(requireActivity(), new String[]{
                        Manifest.permission.ACCESS_BACKGROUND_LOCATION
                }, 102);
            } else {
                Toast.makeText(getContext(), "Granted", Toast.LENGTH_SHORT).show();


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

            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(requireActivity(), new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                }, 100);
            } else {
                Toast.makeText(getContext(), "Granted", Toast.LENGTH_SHORT).show();


                RadarTrackingOptions trackingOptions = RadarTrackingOptions.CONTINUOUS;
                trackingOptions.getForegroundServiceEnabled();
                Radar.startTracking(trackingOptions);
            }
        }

    }

    public void stopService() {
        Radar.stopTracking();
    }

    private void listAddedCards() {
        // setting grid layout manager to implement grid view.
        // in this method '2' represents number of columns to be displayed in grid view.
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);

        cardAdapter = new CardAdapter(getContext());

        // at last set adapter to recycler view.
        cardGrid.setLayoutManager(gridLayoutManager);
        cardGrid.setAdapter(cardAdapter);
    }

    private void listStory() {
        adapter1 = new StoryAdapter(list);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);

        list.add(new ListAllModel(R.drawable.card2, "NMB Bank"));
        list.add(new ListAllModel(R.drawable.card10, "NIC Bank"));
        list.add(new ListAllModel(R.drawable.card5, "Everest Bank"));
        list.add(new ListAllModel(R.drawable.card, "Nabil Bank"));
        list.add(new ListAllModel(R.drawable.card3, "Century Bank"));
        list.add(new ListAllModel(R.drawable.card1, "RBL Bank"));
        list.add(new ListAllModel(R.drawable.card8, "Bank of Kathmandu"));
        list.add(new ListAllModel(R.drawable.card6, "Swiss Bank"));
        list.add(new ListAllModel(R.drawable.card, "Bank of Kathmandu"));
        list.add(new ListAllModel(R.drawable.card7, "Swiss Bank"));

        storyList.setLayoutManager(llm);
        storyList.setAdapter(adapter1);
    }

    private void loadUserList() {
        AppDatabase db = AppDatabase.getDbInstance(getActivity().getApplicationContext());
        cardList = db.cardDao().getAll();

        if (cardList == null) {

        } else {
            cardAdapter.setCardList(cardList);
        }
    }

    private void filterList(String newText) {

        // creating a new array list to filter our data.
        ArrayList<DBModel> filteredList = new ArrayList<>();

        // running a for loop to compare elements.
        for (DBModel item : cardList) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.getCardName().toLowerCase().contains(newText.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredList.add(item);
            }
        }

        if (filteredList.isEmpty()) {
            imageView.setVisibility(View.GONE);
            transactionIcon.setVisibility(View.GONE);
            storyLayout.setVisibility(View.GONE);
            text.setVisibility(View.GONE);
            cardGrid.setVisibility(View.GONE);
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(getContext(), "No Data Found..", Toast.LENGTH_SHORT).show();

        } else {
            imageView.setVisibility(View.GONE);
            transactionIcon.setVisibility(View.GONE);
            storyLayout.setVisibility(View.GONE);
            text.setVisibility(View.GONE);

            // at last we are passing that filtered
            // list to our adapter class.
            cardAdapter.filterList(filteredList);
            cardGrid.setAdapter(cardAdapter);
            cardGrid.setVisibility(View.VISIBLE);
        }

    }
}