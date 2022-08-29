package com.bikashgurung.demo;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface JSONPlaceholder {

    @POST("login")
    @Headers({"accept: application/json, Content-Type: application/json"})
    Call<Api> getPost(
            @Body LoginRequest request
    );

    @POST("register")
    @Headers({"accept: application/json, Content-Type: application/json"})
    Call<Api> getPost(
            @Body RegisterRequest request1
    );

    @POST("logout")
    @Headers({"accept: */*"})
    Call<Api> getPost(
            @Body LogoutRequest request2
    );
}
