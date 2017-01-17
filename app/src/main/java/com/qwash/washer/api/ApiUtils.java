package com.qwash.washer.api;

import android.content.Context;

import com.qwash.washer.api.client.history.HistoryService;
import com.qwash.washer.api.client.RetrofitClient;
import com.qwash.washer.api.client.addressmapsfromgoogleapi.AddressMapsFromGoogleApi;
import com.qwash.washer.api.client.availableforjob.AvailableForJobService;

/**
 * Created by Amay on 12/29/2016.
 */

public class ApiUtils {

    public static final String BASE_URL = "http://maps.googleapis.com/maps/api/";
    public static final String BASE_URL_QLAP = "https://api.myjson.com/bins/";
    public static final String BASE_URL_INPROGRESS = "https://api.myjson.com/bins/";

    public static AddressMapsFromGoogleApi getAddressMapsFromGoogleApi(Context context) {
        return RetrofitClient.getClient(context,BASE_URL).create(AddressMapsFromGoogleApi.class);
    }

    public static HistoryService getHistory(Context context) {
        return RetrofitClient.getClient(context,BASE_URL_QLAP).create(HistoryService.class);

    }

    public static AvailableForJobService getUpdateLocation(Context context) {
        return RetrofitClient.getClient(context,BASE_URL_QLAP).create(AvailableForJobService.class);

    }

}
