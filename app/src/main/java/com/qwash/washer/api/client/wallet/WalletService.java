package com.qwash.washer.api.client.wallet;

/**
 * Created by Amay on 12/29/2016.
 */

import com.qwash.washer.Sample;
import com.qwash.washer.api.model.wallet.WalletListResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface WalletService {
    @GET("washers/history/topup/{washer_id}/{page}/{limit}")
    Call<WalletListResponse> getListWallet(@Header(Sample.AUTHORIZATION) String token, @Path("washer_id") String washer_id, @Path("page") int page, @Path("limit") String limit);
}