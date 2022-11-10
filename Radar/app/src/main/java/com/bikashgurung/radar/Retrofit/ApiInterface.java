package com.bikashgurung.radar.Retrofit;


import com.bikashgurung.radar.Retrofit.Model.List_Geofences.Example;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PUT;
import retrofit2.http.QueryMap;

public interface ApiInterface {

    @GET("geofences")
    @Headers({"accept: application/json", "Authorization: prj_test_sk_b083c91662f60a6a61f52229559fc69a16a60c1f"})
    Call<Example> getGeofenceList(
    );

}