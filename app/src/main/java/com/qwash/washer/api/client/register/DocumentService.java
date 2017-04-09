package com.qwash.washer.api.client.register;

/**
 * Created by Amay on 12/29/2016.
 */


import com.qwash.washer.Sample;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;

public interface DocumentService {

    @Multipart
    @POST("uploads/avatar/{user_id}")
    Call<UploadDocument> getUploadDocumentAvatarLink(@Header(Sample.AUTHORIZATION) String token, @Part MultipartBody.Part file, @Path("user_id") String user_id);

    @Multipart
    @POST("uploads/ktp/{user_id}")
    Call<UploadDocument> getUploadDocumentKtpLink(@Header(Sample.AUTHORIZATION) String token, @Part MultipartBody.Part file,  @Path("user_id") String user_id);

    @Multipart
    @POST("uploads/skck/{user_id}")
    Call<UploadDocument> getUploadDocumentSkckLink(@Header(Sample.AUTHORIZATION) String token, @Part MultipartBody.Part file, @Path("user_id") String user_id);


}