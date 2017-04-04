package com.qwash.washer.api.client.wash_history;

/**
 * Created by Amay on 12/29/2016.
 */

import com.qwash.washer.Sample;
import com.qwash.washer.api.model.wash_history.WashHistoryListResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface WashHistoryService {
    @GET("orders/history/washers/{washer_id}/{page}/{limit}")
    Call<WashHistoryListResponse> getListWashHistory(@Header(Sample.AUTHORIZATION) String token, @Path("washer_id") String washer_id, @Path("page") int page, @Path("limit") String limit);
}