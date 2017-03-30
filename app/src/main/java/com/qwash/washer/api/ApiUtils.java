package com.qwash.washer.api;

import android.content.Context;

import com.qwash.washer.Sample;
import com.qwash.washer.api.client.RetrofitClient;
import com.qwash.washer.api.client.auth.LoginService;
import com.qwash.washer.api.client.availableforjob.AvailableForJobService;
import com.qwash.washer.api.client.feedback_customer.FeedbackCutomerService;
import com.qwash.washer.api.client.order.OrderService;
import com.qwash.washer.api.client.register.DocumentService;
import com.qwash.washer.api.client.register.RegisterService;
import com.qwash.washer.api.client.sms.VerificationCodeService;
import com.qwash.washer.api.client.wash_history.WashHistoryService;


public class ApiUtils {


    public static WashHistoryService WashHistory(Context context) {
        return RetrofitClient.getClient(context, Sample.BASE_URL_QWASH_API).create(WashHistoryService.class);

    }

    public static AvailableForJobService AvailableForJobService(Context context) {
        return RetrofitClient.getClient(context, Sample.BASE_URL_QWASH_API).create(AvailableForJobService.class);

    }


    public static LoginService LoginService(Context context) {
        return RetrofitClient.getClient(context, Sample.BASE_URL_QWASH_PUBLIC).create(LoginService.class);

    }


    public static RegisterService RegisterService(Context context) {
        return RetrofitClient.getClient(context, Sample.BASE_URL_QWASH_PUBLIC).create(RegisterService.class);

    }

    public static VerificationCodeService VerificationCodeService(Context context) {
        return RetrofitClient.getClient(context, Sample.BASE_URL_QWASH_API).create(VerificationCodeService.class);
    }

    public static DocumentService DocumentService(Context context) {
        return RetrofitClient.getClient(context, Sample.BASE_URL_QWASH_API).create(DocumentService.class);
    }


    public static OrderService OrderService(Context context) {
        return RetrofitClient.getClient(context, Sample.BASE_URL_QWASH_API).create(OrderService.class);

    }

    public static FeedbackCutomerService getFeedbackCutomer(Context context) {
        return RetrofitClient.getClient(context, Sample.BASE_URL_QWASH_API).create(FeedbackCutomerService.class);

    }

}
