package com.qwash.washer.api.model.feedback_customer;

/**
 * Created by Amay on 12/29/2016.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.qwash.washer.model.feedback_customer.FeedbackCustomer;

import java.util.List;

public class FeedbackCustomerListResponse {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("messages")
    @Expose
    private String messages;
    @SerializedName("data")
    @Expose
    private List<FeedbackCustomer> data = null;
    @SerializedName("total")
    @Expose
    private Integer total;

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

    public List<FeedbackCustomer> getData() {
        return data;
    }

    public void setData(List<FeedbackCustomer> data) {
        this.data = data;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

}