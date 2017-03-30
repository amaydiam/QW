package com.qwash.washer.api.client.register;

/**
 * Created by Amay on 12/29/2016.
 */


import com.qwash.washer.api.model.register.SendSms;
import com.qwash.washer.api.model.register.Register;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RegisterService {

    @FormUrlEncoded
    @POST("auth/registration/washers")
    Call<Register> getRegisterLink(@FieldMap Map<String, String> params);


   // @Headers(Statics.AUTHORIZATION)
    @FormUrlEncoded
    @POST("registration/send-sms")
    Call<SendSms> getSendSmsLink(@FieldMap Map<String, String> params);

}