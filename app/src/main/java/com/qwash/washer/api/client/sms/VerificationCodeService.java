package com.qwash.washer.api.client.sms;

/**
 * Created by Amay on 12/29/2016.
 */


import com.qwash.washer.Sample;
import com.qwash.washer.api.model.register.SendSms;
import com.qwash.washer.api.model.register.VerificationCode;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface VerificationCodeService {

    @FormUrlEncoded
    @POST("auth/codeactivation/washers")
    Call<VerificationCode> getSmsVerificationLink(@Header(Sample.AUTHORIZATION) String token, @FieldMap Map<String, String> params);


    @GET("auth/codeactivation/resendcode/{userId}")
    Call<SendSms> getResendSmsLink(@Header(Sample.AUTHORIZATION) String token, @Path("userId") String userId);

    @FormUrlEncoded
    @POST("auth/codeactivation/changenumber")
    Call<SendSms> getChangeNumberLink(@Header(Sample.AUTHORIZATION) String token, @FieldMap Map<String, String> params);
}