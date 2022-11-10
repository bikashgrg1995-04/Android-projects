package com.bikashgurung.stocard;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bikashgurung.stocard.DB.AppDatabase;
import com.bikashgurung.stocard.DB.DBModel;
import com.bikashgurung.stocard.DB.DestinationModel;
import com.bikashgurung.stocard.DB.PassingDataModel;
import com.bikashgurung.stocard.DB.StoreModel;
import com.bikashgurung.stocard.Retrofit.ApiInterface;
import com.bikashgurung.stocard.Retrofit.Distance.DistanceResponse;
import com.bikashgurung.stocard.Retrofit.Distance.Element;
import com.bikashgurung.stocard.Retrofit.RetrofitClient;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private static final String TAG = "MainActivity";

    RecyclerView storyList, cardGrid;
    MaterialButton addCard;
    SearchView searchView;
    TextView text, latitudeTextView, longitudeTextView;

    List<ListAllModel> list = new ArrayList<>();
    List<DBModel> cardList = new ArrayList<>();

    StoryAdapter adapter1;
    ImageView imageView, transactionIcon;
    LinearLayout storyLayout, locationLayout;
    SwitchMaterial geofenceController;

    private CardAdapter cardAdapter;
    private GpsTracker gpsTracker;

    Double user_longitude, user_latitude;
    Handler handler = new Handler();
    Runnable runnable;
    int delay = 5000;

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

        locationLayout = view.
                findViewById(R.id.checking);

        geofenceController = view.findViewById(R.id.geofenceSwitch);
        geofenceController.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cardGrid.setVisibility(View.GONE);
                    locationLayout.setVisibility(View.VISIBLE);
                    //Toast.makeText(getContext(), "Foreground/Background Service starts.", Toast.LENGTH_SHORT).show();
                    /*handler.postDelayed(runnable = new Runnable() {
                        public void run() {
                            handler.postDelayed(runnable, delay);
                            getUserLocation();

                            //Toast.makeText(ForegroundService.this, "Tracking",Toast.LENGTH_SHORT).show();
                        }
                    }, delay);*/
                    startService();
                } else {
                    cardGrid.setVisibility(View.VISIBLE);
                    locationLayout.setVisibility(View.GONE);
                    //Toast.makeText(getContext(), "Foreground/Background Service stops.", Toast.LENGTH_SHORT).show();
                    //stopForegroundService();
                    stopService();
                }
            }
        });


        return view;
    }

    public void startService() {
        Intent serviceIntent = new Intent(getActivity(), ForegroundService.class);
        serviceIntent.putExtra("inputExtra", "StoCard is running in background.");
        ContextCompat.startForegroundService(getContext(), serviceIntent);
    }

    public void stopService() {
        /*Intent serviceIntent = new Intent(getActivity(), ForegroundService.class);
        stopService();*/

    }

    private void stopForegroundService() {
        //handler.removeCallbacks(runnable);
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

    private void getUserLocation() {
        gpsTracker = new GpsTracker(getContext());
        if (gpsTracker.canGetLocation()) {
            user_latitude = gpsTracker.getLatitude();
            user_longitude = gpsTracker.getLongitude();

            latitudeTextView.setText(String.valueOf(user_latitude));
            longitudeTextView.setText(String.valueOf(user_longitude));

        } else {
            gpsTracker.showSettingsAlert();
        }
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