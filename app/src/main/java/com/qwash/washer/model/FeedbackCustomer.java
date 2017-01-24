package com.qwash.washer.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FeedbackCustomer implements Parcelable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("rate")
    @Expose
    private String rate;
    @SerializedName("create_at")
    @Expose
    private String createAt;
    @SerializedName("comments")
    @Expose
    private String comments;
    @SerializedName("orders_ref")
    @Expose
    private String ordersRef;


    public FeedbackCustomer(String id, String rate, String createAt, String comments, String ordersRef) {
        this.id = id;
        this.rate = rate;
        this.createAt = createAt;
        this.comments = comments;
        this.ordersRef = ordersRef;
    }

    protected FeedbackCustomer(Parcel in) {
        id = in.readString();
        rate = in.readString();
        createAt = in.readString();
        comments = in.readString();
        ordersRef = in.readString();
    }

    public static final Creator<FeedbackCustomer> CREATOR = new Creator<FeedbackCustomer>() {
        @Override
        public FeedbackCustomer createFromParcel(Parcel in) {
            return new FeedbackCustomer(in);
        }

        @Override
        public FeedbackCustomer[] newArray(int size) {
            return new FeedbackCustomer[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getOrdersRef() {
        return ordersRef;
    }

    public void setOrdersRef(String ordersRef) {
        this.ordersRef = ordersRef;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(rate);
        parcel.writeString(createAt);
        parcel.writeString(comments);
        parcel.writeString(ordersRef);
    }
}