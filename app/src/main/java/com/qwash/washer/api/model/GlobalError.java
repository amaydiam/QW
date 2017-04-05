package com.qwash.washer.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Amay on 1/21/2017.
 */

public class GlobalError {
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

    public void setMessages(String message) {
        this.messages = message;
    }
}