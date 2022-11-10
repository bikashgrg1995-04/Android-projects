package com.bikashgurung.stocard.Retrofit;

import com.bikashgurung.stocard.Retrofit.Distance.DistanceResponse;
import com.bikashgurung.stocard.Retrofit.Nearby.NearbyResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface ApiInterface {

    @GET("maps/api/distancematrix/json")

    Call<DistanceResponse> getDistanceInfo(
            @QueryMap Map<String, String> parameters
    );

    @GET("maps/api/place/nearbysearch/json")

    Call<NearbyResponse> getNearby(
            @QueryMap Map<String, String> parameters
    );
}