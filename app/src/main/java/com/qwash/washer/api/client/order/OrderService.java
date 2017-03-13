package com.qwash.washer.api.client.order;

/**
 * Created by Amay on 12/29/2016.
 */


import com.qwash.washer.api.model.order.DeaclineOrder;
import com.qwash.washer.api.model.order.OrderFinish;
import com.qwash.washer.api.model.order.OrderStartWash;
import com.qwash.washer.api.model.order.PickOrder;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface OrderService {


    @FormUrlEncoded
    @POST("findmatch/cancelOrderWasher")
    Call<DeaclineOrder> getDeaclineOrderLink(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("findmatch/pickOrder")
    Call<PickOrder> getPickOrderLink(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("findmatch/orderStartWash")
    Call<OrderStartWash> getOrderStartWashLink(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("findmatch/orderFinish")
    Call<OrderFinish> getOrderFinishLink(@FieldMap Map<String, String> params);

}