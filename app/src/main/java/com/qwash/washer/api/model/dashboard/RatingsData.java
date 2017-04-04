package com.qwash.washer.api.model.dashboard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Amay on 4/3/2017.
 */

public class RatingsData {


    @SerializedName("ratings")
    @Expose
    private Double ratings;

    public Double getRatings() {
        return ratings;
    }

    public void setRatings(Double ratings) {
        this.ratings = ratings;
    }

}
