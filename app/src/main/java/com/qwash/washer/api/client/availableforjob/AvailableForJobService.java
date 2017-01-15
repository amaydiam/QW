package com.qwash.washer.api.client.availableforjob;

/**
 * Created by Amay on 12/29/2016.
 */


import com.qwash.washer.model.availableforjob.AvailableForJob;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AvailableForJobService {
    @FormUrlEncoded
    @POST("login/authenticate")
    Call<AvailableForJob> getAvailableForJobLink(@FieldMap Map<String, String> params);
}