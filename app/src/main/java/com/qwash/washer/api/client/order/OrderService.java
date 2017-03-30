package com.qwash.washer.api.client.order;

/**
 * Created by Amay on 12/29/2016.
 */


import com.qwash.washer.Sample;
import com.qwash.washer.api.model.order.DeaclineOrder;
import com.qwash.washer.api.model.order.OnTheWayOrder;
import com.qwash.washer.api.model.order.FinishOrder;
import com.qwash.washer.api.model.order.StartOrder;
import com.qwash.washer.api.model.order.PickOrder;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface OrderService {


    @FormUrlEncoded
    @POST("orders/cancelwashers")
    Call<DeaclineOrder> getDeaclineOrderLink(@Header(Sample.AUTHORIZATION) String token, @FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("orders/pick")
    Call<PickOrder> getPickOrderLink(@Header(Sample.AUTHORIZATION) String token, @FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("orders/ontheway")
    Call<OnTheWayOrder> getOnTheWayOrderLink(@Header(Sample.AUTHORIZATION) String token, @FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("orders/start")
    Call<StartOrder> getStartOrderLink(@Header(Sample.AUTHORIZATION) String token, @FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("orders/finish")
    Call<FinishOrder> getFinishOrderLink(@Header(Sample.AUTHORIZATION) String token, @FieldMap Map<String, String> params);


}