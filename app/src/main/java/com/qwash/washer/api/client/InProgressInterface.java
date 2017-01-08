package com.qwash.washer.api.client;

import com.qwash.washer.api.model.InProgressResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface InProgressInterface {

    @GET("19iiff")
    Call<InProgressResponse> getInput(@Query("input") String input);

}