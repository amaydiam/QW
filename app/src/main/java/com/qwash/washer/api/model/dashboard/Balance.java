package com.qwash.washer.api.model.dashboard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Amay on 4/3/2017.
 */

public class Balance {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private List<BalanceData> data = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<BalanceData> getData() {
        return data;
    }

    public void setData(List<BalanceData> data) {
        this.data = data;
    }
}
