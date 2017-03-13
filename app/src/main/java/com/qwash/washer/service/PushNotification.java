package com.qwash.washer.service;


import com.qwash.washer.Sample;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Amay on 1/23/2017.
 */

public class PushNotification {

    private static OkHttpClient mClient = new OkHttpClient();
    private static MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static String postToFCM(String bodyString) throws IOException {
        RequestBody body = RequestBody.create(JSON, bodyString);
        Request request = new Request.Builder()
                .url(Sample.FCM_MESSAGE_URL)
                .post(body)
                .addHeader("Authorization", "key=" + Sample.SERVER_KEY_FIREBASE)
                .build();
        okhttp3.Response response = mClient.newCall(request).execute();
        return response.body().string();
    }
}
