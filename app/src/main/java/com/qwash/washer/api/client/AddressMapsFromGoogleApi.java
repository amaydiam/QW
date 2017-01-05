package com.ad.sample.api.client;

/**
 * Created by Amay on 12/29/2016.
 */

import com.ad.sample.api.model.AddressFromMapsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AddressMapsFromGoogleApi {
    @GET("geocode/json?sensor=true")
    Call<AddressFromMapsResponse> getAddress(@Query("latlng") String tags);
}