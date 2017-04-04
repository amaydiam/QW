package com.qwash.washer.api.model.dashboard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Amay on 4/3/2017.
 */

public class Ratings {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private List<RatingsData> data = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<RatingsData> getData() {
        return data;
    }

    public void setData(List<RatingsData> data) {
        this.data = data;
    }

}
