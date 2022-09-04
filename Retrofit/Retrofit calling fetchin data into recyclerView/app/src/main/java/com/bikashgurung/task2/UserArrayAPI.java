package com.bikashgurung.task2;

import com.google.android.material.textview.MaterialTextView;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface UserArrayAPI {

    /* @GET url is after Base_URL.
       We are going to get List of country as response.
    */
    @GET("users")
    @Headers("accept: application/json")
    Call<ResponseData<Users>> getUsers(
            @Query("page") int page,
            @Query("per_page") int per_page
    );


}
