package com.qwash.washer.api.client.availableforjob;

/**
 * Created by Amay on 12/29/2016.
 */


import com.qwash.washer.Sample;
import com.qwash.washer.model.available_for_job.AvailableForJob;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface AvailableForJobService {

    @FormUrlEncoded
    @POST("washers/jobs/online")
    Call<AvailableForJob> getWasherOnLink(@Header(Sample.AUTHORIZATION) String token, @FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("washers/jobs/offline")
    Call<AvailableForJob> getWasherOffLink(@Header(Sample.AUTHORIZATION) String token, @FieldMap Map<String, String> params);

}