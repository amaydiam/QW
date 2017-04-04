package com.qwash.washer.api.client.dashboard;

/**
 * Created by Amay on 12/29/2016.
 */

import com.qwash.washer.Sample;
import com.qwash.washer.api.model.dashboard.Balance;
import com.qwash.washer.api.model.dashboard.Ratings;
import com.qwash.washer.api.model.wash_history.WashHistoryListResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface DashboardService {
    @GET("washers/dashboard/rate/{washersId}")
    Call<Ratings> getRatingsLink(@Header(Sample.AUTHORIZATION) String token,  @Path("washersId") String washersId);

    @GET("washers/dashboard/balance/{washersId}")
    Call<Balance> getBalanceLink(@Header(Sample.AUTHORIZATION) String token,  @Path("washersId") String washersId);
}