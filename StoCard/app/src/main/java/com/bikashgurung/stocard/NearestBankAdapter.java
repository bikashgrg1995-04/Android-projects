package com.bikashgurung.stocard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bikashgurung.stocard.DB.StoreModel;
import com.bikashgurung.stocard.Retrofit.ApiInterface;
import com.bikashgurung.stocard.Retrofit.Distance.DistanceResponse;
import com.bikashgurung.stocard.Retrofit.Distance.Element;
import com.bikashgurung.stocard.Retrofit.RetrofitClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class    NearestBankAdapter extends RecyclerView.Adapter<NearestBankAdapter.ViewHolder>{

    private List<StoreModel> bankList;
    StoreModel storeModel;

    private GpsTracker gpsTracker;
    Double branch_longitude, branch_latitude, user_longitude, user_latitude;
    String distance, final_distance;

    public NearestBankAdapter(List<StoreModel> list) {
        bankList = list;
    }



    @NonNull
    @Override
    public NearestBankAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.store_item, parent, false);

        getLocation(view);

        return new NearestBankAdapter.ViewHolder(view);
    }

    private void getLocation(View view) {
        gpsTracker = new GpsTracker(view.getContext());
        if(gpsTracker.canGetLocation()){
            user_latitude = gpsTracker.getLatitude();
            user_longitude = gpsTracker.getLongitude();

        }else{
            gpsTracker.showSettingsAlert();
        }
    }

    @Override
    public void onBindViewHolder(@NonNull NearestBankAdapter.ViewHolder holder, int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        storeModel = bankList.get(position);

        holder.branch_name.setText(storeModel.getBranch_name());
        holder.status.setText(storeModel.getStore_status());

        branch_longitude = Double.valueOf(storeModel.getStore_longitute());
        branch_latitude = Double.valueOf(storeModel.getStore_latitude());

        getDistanceInfo(holder);

    }

    private void getDistanceInfo(ViewHolder holder) {

        Map<String, String> mapQuery = new HashMap<>();
        mapQuery.put("origins", user_latitude + "," + user_longitude);
        mapQuery.put("destinations", branch_latitude + "," + branch_longitude);

        mapQuery.put("key", "AIzaSyAJXVZvxdsuKE_pnjVW-75c2ki076skvk0");
        ApiInterface client = RetrofitClient.getInstance().getApi();

        Call<DistanceResponse> call = client.getDistanceInfo(mapQuery);
        call.enqueue(new Callback<DistanceResponse>() {
            @Override
            public void onResponse(Call<DistanceResponse> call, Response<DistanceResponse> response) {
                if (response.body() != null &&
                        response.body().getRows() != null &&
                        response.body().getRows().size() > 0 &&
                        response.body().getRows().get(0) != null &&
                        response.body().getRows().get(0).getElements() != null &&
                        response.body().getRows().get(0).getElements().size() > 0 &&
                        response.body().getRows().get(0).getElements().get(0) != null &&
                        response.body().getRows().get(0).getElements().get(0).getDistance() != null &&
                        response.body().getRows().get(0).getElements().get(0).getDuration() != null) {

                    Element element = response.body().getRows().get(0).getElements().get(0);

                    distance = element.getDistance().getText();
                    //holder.distance.setText(distance);

                    setDistance(holder, distance);
                }
            }

            @Override
            public void onFailure(Call<DistanceResponse> call, Throwable t) {

            }
        });
    }

    private void setDistance(ViewHolder holder, String distance) {
        this.final_distance = distance;
        holder.distance.setText(final_distance);
    }


    @Override
    public int getItemCount() {
        return bankList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private  TextView branch_name;
        private  TextView status;
        private  TextView distance;

        public ViewHolder(View view) {
            super(view);

            branch_name = (TextView) view.findViewById(R.id.location_name);
            status = (TextView) view.findViewById(R.id.status);
            distance = (TextView) view.findViewById(R.id.distance);
        }

        public TextView getBranch_name() {
            return branch_name;
        }

        public TextView getStatus() {
            return status;
        }

        public TextView getDistance() {
            return distance;
        }
    }

}
