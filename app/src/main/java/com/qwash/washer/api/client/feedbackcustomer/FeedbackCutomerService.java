package com.qwash.washer.api.client.feedbackcustomer;

/**
 * Created by Amay on 12/29/2016.
 */


import com.qwash.washer.api.model.FeedbackCustomer.FeedbackCustomerListResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface FeedbackCutomerService {
    //  @GET("history")
    @GET("washer/getFeedback/{washer_id}/{page}/{limit}")
    Call<FeedbackCustomerListResponse> getListFeedbackCustomer(@Path("washer_id") String washer_id, @Path("page") int page, @Path("limit") String limit);

    // @GET("history")
    @GET("1bom2v")
    Call<FeedbackCustomerListResponse> getDetailFeedbackCustomer(@Path("id") String id);
}