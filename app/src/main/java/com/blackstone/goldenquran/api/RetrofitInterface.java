package com.blackstone.goldenquran.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Streaming;

/**
 * Created by SamerGigaByte on 11/18/2016.
 */

public interface RetrofitInterface {
        @GET("quran/abdullaah_3awwaad_al-juhaynee/003.mp3")
        @Streaming
        Call<ResponseBody> downloadFile();
}
