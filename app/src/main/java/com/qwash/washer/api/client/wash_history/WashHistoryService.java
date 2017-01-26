package com.qwash.washer.api.client.wash_history;

/**
 * Created by Amay on 12/29/2016.
 */

import com.qwash.washer.api.model.wash_history.WashHistoryListResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface WashHistoryService {
    @GET("washer/getHistoryJobs/{type}/{washer_id}/{page}/{limit}")
    Call<WashHistoryListResponse> getListWashHistory(@Path("type") int type, @Path("washer_id") String washer_id, @Path("page") int page, @Path("limit") String limit);

}