package com.bikashgurung.stocard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AddCards extends AppCompatActivity {

    RecyclerView frequent, all;
    ArrayList<ListAllModel> list = new ArrayList<>();
    ArrayList<ListAllModel> list2 = new ArrayList<>();
    AllAdapter adapter, frequentAdapter;
    TextView frequentText, textAll;
    ImageView back;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cards);

        back = findViewById(R.id.btnBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddCards.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        searchView = findViewById(R.id.search_bar);
        searchView.clearFocus();
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

        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    back.setVisibility(View.GONE);
                } else {
                    back.setVisibility(View.VISIBLE);
                }
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                all.setVisibility(View.VISIBLE);
                frequent.setVisibility(View.VISIBLE);
                frequentText.setVisibility(View.VISIBLE);
                textAll.setVisibility(View.VISIBLE);

                return false;
            }
        });

        frequentText = findViewById(R.id.textFrequent);
        textAll = findViewById(R.id.all_text);

        all = findViewById(R.id.listAll);


        adapter = new AllAdapter(list);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        list.add(new ListAllModel(R.drawable.card, "NMB Bank"));
        list.add(new ListAllModel(R.drawable.card1, "NIC Bank"));
        list.add(new ListAllModel(R.drawable.card2, "Everest Bank"));
        list.add(new ListAllModel(R.drawable.card3, "Nabil Bank"));
        list.add(new ListAllModel(R.drawable.card, "Century Bank"));
        list.add(new ListAllModel(R.drawable.card5, "RBL Bank"));
        list.add(new ListAllModel(R.drawable.card6, "Bank of Kathmandu"));
        list.add(new ListAllModel(R.drawable.card7, "Swiss Bank"));
        list.add(new ListAllModel(R.drawable.card8, "Bank of America"));
        list.add(new ListAllModel(R.drawable.card10, "SBI Bank"));

        all.setLayoutManager(llm);
        all.setAdapter(adapter);


        frequent = findViewById(R.id.frequentlyAdded);
        frequentAdapter = new AllAdapter(list2);
        LinearLayoutManager llm1 = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        list2.add(new ListAllModel(R.drawable.card, "NMB Bank"));
        list2.add(new ListAllModel(R.drawable.card1, "NIC Bank"));
        list2.add(new ListAllModel(R.drawable.card2, "Everest Bank"));
        list2.add(new ListAllModel(R.drawable.card3, "Nabil Bank"));
        list2.add(new ListAllModel(R.drawable.card, "Century Bank"));

        frequent.setLayoutManager(llm1);
        frequent.setAdapter(frequentAdapter);

    }

    private void filterList(String newText) {

        // creating a new array list to filter our data.
        ArrayList<ListAllModel> filteredList = new ArrayList<>();

        // running a for loop to compare elements.
        for (ListAllModel item : list) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.getName().toLowerCase().contains(newText.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredList.add(item);
            }
        }

        if (filteredList.isEmpty()) {
            frequentText.setVisibility(View.GONE);
            textAll.setVisibility(View.GONE);
            frequent.setVisibility(View.GONE);
            frequentText.setVisibility(View.GONE);
            all.setVisibility(View.GONE);
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show();

        } else {
            frequentText.setVisibility(View.GONE);
            textAll.setVisibility(View.GONE);
            frequent.setVisibility(View.GONE);
            frequentText.setVisibility(View.GONE);

            // at last we are passing that filtered
            // list to our adapter class.
            adapter.filterList(filteredList);
            all.setAdapter(adapter);
            all.setVisibility(View.VISIBLE);
        }

    }

}