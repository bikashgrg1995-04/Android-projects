package com.bikashgurung.task2;

import android.annotation.SuppressLint;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ListUserFragment extends Fragment {

    View view;
    RecyclerView recyclerView;
    ProgressBar progressBar;

    private NestedScrollView nestedSV;

    int page1 = 1;
    int per_page1 = 8;
    int limit = 2;

    List<Users> usersList = new ArrayList<>();
    CustomAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_list_users, container, false);
        recyclerView = view.findViewById(R.id.recycleView);
        progressBar = view.findViewById(R.id.progress_circular);
        nestedSV = view.findViewById(R.id.idNestedSV);

        adapter = new CustomAdapter(usersList);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDataFromAPI(page1, per_page1);
        progressBar.setVisibility(View.VISIBLE);

        // adding on scroll change listener method for our nested scroll view.
        nestedSV.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(@NonNull NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                // on scroll change we are checking when users scroll as bottom.
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    // in this method we are incrementing page number,
                    // making progress bar visible and calling get data method.

                    page1++;
                    progressBar.setVisibility(View.VISIBLE);
                    getDataFromAPI(page1, per_page1);
                }
            }
        });
    }

    private void getDataFromAPI(int page1, int per_page1) {

        if (page1 > limit) {
            // checking if the page number is greater than limit.
            // displaying toast message in this case when page>limit.
            Toast.makeText(getContext(), "That's all the data..", Toast.LENGTH_SHORT).show();

            // hiding our progress bar.
            progressBar.setVisibility(View.GONE);
            return;
        }

        Call<ResponseData<Users>> call = RetrofitClient.getInstance().getApi().getUsers(page1, per_page1);
        call.enqueue(new Callback<ResponseData<Users>>() {
            @Override
            public void onResponse(Call<ResponseData<Users>> call, Response<ResponseData<Users>> response) {

                ResponseData<Users> responseData = response.body();

                if (responseData != null) {

                    progressBar.setVisibility(View.GONE);

                    usersList.addAll(responseData.getUsers());

                    adapter.notifyDataSetChanged();


                }
            }

            @Override
            public void onFailure(Call<ResponseData<Users>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

}