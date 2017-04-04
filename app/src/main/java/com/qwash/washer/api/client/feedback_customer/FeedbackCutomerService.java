package com.qwash.washer.api.client.feedback_customer;

/**
 * Created by Amay on 12/29/2016.
 */


import com.qwash.washer.Sample;
import com.qwash.washer.api.model.feedback_customer.FeedbackCustomerListResponse;
import com.qwash.washer.api.model.wash_history.WashHistoryListResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface FeedbackCutomerService {

    @GET("orders/feedback/washers/{washer_id}/{page}/{limit}")
    Call<FeedbackCustomerListResponse> getListFeedbackCustomer(@Header(Sample.AUTHORIZATION) String token, @Path("washer_id") String washer_id, @Path("page") int page, @Path("limit") String limit);

}