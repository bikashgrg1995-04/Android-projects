package com.bikashgurung.facedetection;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface Api {

    @Multipart
    @POST("generate")
    //@POST("file.php")
    @Headers({"X-Token: a4e08a2abf5f4e03ab257f53cec3ca36", "accept: application/json, Content-Type: multipart/form-data"})
    Call<Result> getImage(
            @Query("style") String style,
            @Part MultipartBody.Part photo
    );

    @GET("sticker")
    @Headers({"X-Token: a4e08a2abf5f4e03ab257f53cec3ca36", "accept: application/json"})
    Call<StickerResult> createSticker(
            @Query("face_id") String face_id,
            @Query("sticker") String sticker,
            @Query("loc") String loc
    );

}
