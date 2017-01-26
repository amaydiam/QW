package com.qwash.washer.api.client.feedback_customer;

/**
 * Created by Amay on 12/29/2016.
 */


import com.qwash.washer.api.model.feedback_customer.FeedbackCustomerListResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface FeedbackCutomerService {

    @GET("washer/getFeedback/{washer_id}/{page}/{limit}")
    Call<FeedbackCustomerListResponse> getListFeedbackCustomer(@Path("washer_id") String washer_id, @Path("page") int page, @Path("limit") String limit);

}