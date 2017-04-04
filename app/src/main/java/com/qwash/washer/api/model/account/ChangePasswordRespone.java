package com.qwash.washer.api.model.account;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.qwash.washer.api.model.dashboard.BalanceData;

import java.util.List;

/**
 * Created by Amay on 4/3/2017.
 */

public class ChangePasswordRespone {


    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("messages")
    @Expose
    private String messages;

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }
}
