package com.qwash.washer.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InProgressResponse {

    @SerializedName("user")
    @Expose
    private String user;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("detail")
    @Expose
    private Detail detail;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Detail getDetail() {
        return detail;
    }

    public void setDetail(Detail detail) {
        this.detail = detail;
    }

}