package com.qwash.washer.api.model.login; /**
 * Created by Amay on 12/29/2016.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.qwash.washer.api.model.washer.DataWasher;

public class Login {


    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("data")
    @Expose
    private DataWasher data;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public DataWasher getDataWasher() {
        return data;
    }

    public void setDataLogin(DataWasher data) {
        this.data = data;
    }


}

