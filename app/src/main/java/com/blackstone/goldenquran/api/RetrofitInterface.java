package com.blackstone.goldenquran.api;

import com.blackstone.goldenquran.models.RecitationModel;
import com.blackstone.goldenquran.models.TheExplanationModel;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Streaming;

public interface RetrofitInterface {
    @GET("{sorrah}{ayaa}.mp3")
    @Streaming
    Call<ResponseBody> downloadFile(/*@Path("sheikh") String sheikh,*/ @Path("sorrah") String sorrah, @Path("ayaa") String ayaa);

    @GET("{sorraAyaa}.mp3")
    @Streaming
    Call<ResponseBody> downloadFile(/*@Path("sheikh") String sheikh,*/ @Path("sorraAyaa") String sorraAyaa);

    Call<ArrayList<TheExplanationModel>> getExplanationData();

    Call<ArrayList<RecitationModel>> getReaders();
}
