package com.qwash.washer.api.client.account;

/**
 * Created by Amay on 12/29/2016.
 */


import com.qwash.washer.Sample;
import com.qwash.washer.api.model.account.ChangePasswordRespone;
import com.qwash.washer.api.model.account.RequestNewPassword;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AccountService {

    @GET("users/request/newpassword/{userId}")
    Call<RequestNewPassword> getRequestNewPasswordLink(@Header(Sample.AUTHORIZATION) String token, @Path("userId") String userId);


    @FormUrlEncoded
    @POST("users/change/password")
    Call<ChangePasswordRespone> getChangePasswordLink(@Header(Sample.AUTHORIZATION) String token, @FieldMap Map<String, String> params);

}