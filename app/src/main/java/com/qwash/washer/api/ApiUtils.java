package com.qwash.washer.api;

import com.qwash.washer.api.client.HistoryService;
import com.qwash.washer.api.client.RetrofitClient;
import com.qwash.washer.api.client.AddressMapsFromGoogleApi;

/**
 * Created by Amay on 12/29/2016.
 */

public class ApiUtils {

    public static final String BASE_URL = "http://maps.googleapis.com/maps/api/";
    public static final String BASE_URL_QLAP = "https://api.myjson.com/bins/";

    public static AddressMapsFromGoogleApi getAddressMapsFromGoogleApi() {
        return RetrofitClient.getClient(BASE_URL).create(AddressMapsFromGoogleApi.class);
    }

    public static HistoryService getHistory() {
        return RetrofitClient.getClient(BASE_URL_QLAP).create(HistoryService.class);

    }





}
