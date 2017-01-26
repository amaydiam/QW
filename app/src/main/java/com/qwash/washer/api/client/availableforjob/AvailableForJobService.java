package com.qwash.washer.api.client.availableforjob;

/**
 * Created by Amay on 12/29/2016.
 */


import com.qwash.washer.model.available_for_job.AvailableForJob;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AvailableForJobService {
    @FormUrlEncoded
    @POST("findmatch/washerOn")
    Call<AvailableForJob> getWasherOnLink(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("findmatch/washerOnUpdate")
    Call<AvailableForJob> getWasherOnUpdateLink(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("findmatch/washerOff")
    Call<AvailableForJob> getWasherOffLink(@FieldMap Map<String, String> params);
}