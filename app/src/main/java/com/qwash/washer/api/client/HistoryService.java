package com.qwash.washer.api.client;

/**
 * Created by Amay on 12/29/2016.
 */

import com.qwash.washer.api.model.HistoryDetailResponse;
import com.qwash.washer.api.model.HistoryListResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface HistoryService {
    //  @GET("history")
    @GET("17bpxj")
    Call<HistoryListResponse> getListHistory(@Query("page") int page, @Query("keyword") String keyword);

    // @GET("history")
    @GET("1bom2v")
    Call<HistoryDetailResponse> getDetailHistory(@Query("id") String id);
}