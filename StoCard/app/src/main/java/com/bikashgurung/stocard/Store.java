package com.bikashgurung.stocard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bikashgurung.stocard.DB.AppDatabase;
import com.bikashgurung.stocard.DB.StoreModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.util.List;

public class Store extends AppCompatActivity {

    RecyclerView bankList;
    String cardName, cardID;
    NearestBankAdapter adapter;

    String bank_name, branch_name, bank_long, bank_lat, status, cardNameFromNotification;

    LinearProgressIndicator progressBar;

    TextView bankName;
    MaterialButton back;

    Double user_longitude, user_latitude;
    //int radius = 100000;
    private GpsTracker gpsTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        progressBar = findViewById(R.id.progressBar);


        cardID = getIntent().getExtras().getString("card_id");
        cardName = getIntent().getExtras().getString("card_name");
        cardNameFromNotification = getIntent().getStringExtra("bankName");

        bankName = findViewById(R.id.bank_name);
        bankName.setText(cardName);

        back = findViewById(R.id.btnBackStore);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Store.this, Show_Card_Details.class);
                //Create the bundle
                Bundle bundle = new Bundle();
//Add your data from getFactualResults method to bundle
                bundle.putString("cardId", String.valueOf(cardID));
//Add the bundle to the intent
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });


        bankList = findViewById(R.id.nearest_bank_list);
    }


    @Override
    protected void onStart() {
        super.onStart();

        progressBar.setVisibility(View.VISIBLE);
        getBankList();
    }

    private void getBankList() {

        if (cardName != null) {

            AppDatabase db = AppDatabase.getDbInstance(this.getApplicationContext());
            List<StoreModel> storeModel = db.storeDao().findById(cardName);

            if (storeModel.isEmpty()){
                progressBar.setVisibility(View.GONE);
                Toast.makeText(this, "Cannot find " + cardName + " nearby.", Toast.LENGTH_SHORT).show();

            }else{
                for (int i = 0; i < storeModel.size(); i++) {
                    bank_name = storeModel.get(i).bank_name;
                    branch_name = storeModel.get(i).getBranch_name();
                    bank_long = storeModel.get(i).store_longitute;
                    bank_lat = storeModel.get(i).getStore_latitude();
                    status = storeModel.get(i).getStore_status();

                    callAdapter(storeModel, bank_name, branch_name, bank_long, bank_lat, status);
                }
            }
        }

    }

    private void callAdapter(List<StoreModel> storeModel, String bank_name, String branch_name, String bank_long, String bank_lat, String status) {
        adapter = new NearestBankAdapter(storeModel);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        bankList.setLayoutManager(llm);
        bankList.setAdapter(adapter);

        progressBar.setVisibility(View.GONE);
    }


//    private void getDataFromAPI() {
//
//        Map<String, String> mapQuery = new HashMap<>();
//        mapQuery.put("location", user_latitude + "," + user_longitude);
//        mapQuery.put("radius", String.valueOf(radius));
//        mapQuery.put("types", "bank");
//        mapQuery.put("name", cardName);
//
//        mapQuery.put("key", "AIzaSyAJXVZvxdsuKE_pnjVW-75c2ki076skvk0");
//        ApiInterface client = RetrofitClient.getInstance().getApi();
//
//        Call<NearbyResponse> call = client.getNearby(mapQuery);
//        call.enqueue(new Callback<NearbyResponse>() {
//            @Override
//            public void onResponse(Call<NearbyResponse> call, Response<NearbyResponse> response) {
//
//                NearbyResponse result = response.body();
//
//                String name, full_address;
//                Boolean status;
//                double branch_lat, branch_long;
//
//
//                if (result != null ) {
//
//                    for (int i = 0; i < result.getResults().size(); i++) {
//
//                        name = result.getResults().get(i).getName();
//
//                        //status = result.getResults().get(i).getOpeningHours().getOpenNow();
//
//                        branch_lat = result.getResults().get(i).getGeometry().getLocation().getLat();
//                        branch_long = result.getResults().get(i).getGeometry().getLocation().getLng();
//
//                        full_address = result.getResults().get(i).getVicinity();
//
//
//
//                        callAdapter(name, false, branch_lat, branch_long, full_address);
//                    }
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<NearbyResponse> call, Throwable t) {
//
//                System.out.println("ERROR" + t);
//            }
//        });
//    }

//    private void callAdapter(String name, Boolean status, double branch_lat, double branch_long, String full_address) {
//
//        String Sbranch_long = String.valueOf(branch_long);
//        String Sbranch_lat = String.valueOf(branch_lat);
//        String Sstatus;
//
//        if (status == true) {
//            Sstatus = "Open";
//        } else {
//            Sstatus = "Closed";
//        }
//
//        adapter = new NearestBankAdapter(list);
//
//        LinearLayoutManager llm = new LinearLayoutManager(this);
//        llm.setOrientation(LinearLayoutManager.VERTICAL);
//        list.add(new StoreModel(name, full_address, Sbranch_long, Sbranch_lat, Sstatus));
//
//        bankList.setLayoutManager(llm);
//        bankList.setAdapter(adapter);
//
//        progressBar.setVisibility(View.GONE);
//
//    }


}