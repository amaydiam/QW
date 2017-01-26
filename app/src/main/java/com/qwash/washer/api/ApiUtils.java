package com.qwash.washer.api;

import android.content.Context;

import com.qwash.washer.Sample;
import com.qwash.washer.api.client.RetrofitClient;
import com.qwash.washer.api.client.auth.LoginService;
import com.qwash.washer.api.client.availableforjob.AvailableForJobService;
import com.qwash.washer.api.client.feedback_customer.FeedbackCutomerService;
import com.qwash.washer.api.client.order.OrderService;
import com.qwash.washer.api.client.register.RegisterService;
import com.qwash.washer.api.client.wash_history.WashHistoryService;


public class ApiUtils {


    public static WashHistoryService WashHistory(Context context) {
        return RetrofitClient.getClient(context, Sample.BASE_URL_QWASH).create(WashHistoryService.class);

    }

    public static AvailableForJobService AvailableForJobService(Context context) {
        return RetrofitClient.getClient(context, Sample.BASE_URL_QWASH).create(AvailableForJobService.class);

    }


    public static LoginService LoginService(Context context) {
        return RetrofitClient.getClient(context, Sample.BASE_URL_QWASH).create(LoginService.class);

    }


    public static RegisterService RegisterService(Context context) {
        return RetrofitClient.getClient(context, Sample.BASE_URL_QWASH).create(RegisterService.class);

    }


    public static OrderService OrderService(Context context) {
        return RetrofitClient.getClient(context, Sample.BASE_URL_QWASH).create(OrderService.class);

    }


    public static FeedbackCutomerService getFeedbackCutomer(Context context) {
        return RetrofitClient.getClient(context, Sample.BASE_URL_QWASH).create(FeedbackCutomerService.class);

    }

}
