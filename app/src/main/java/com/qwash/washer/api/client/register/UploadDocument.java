package com.qwash.washer.api.client.register;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Amay on 3/27/2017.
 */

public class UploadDocument {
    @SerializedName("status")
    @Expose
    private Boolean status;


    @SerializedName("messages")
    @Expose
    private String messages;

    @SerializedName("filename")
    @Expose
    private String filename;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    public String getFileName() {
        return filename;
    }

    public void setFileName(String filename) {
        this.filename = filename;
    }

}