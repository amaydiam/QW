package com.qwash.washer.model.feedback_customer;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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
    @SerializedName("name")
    @Expose
    private String name;

    public FeedbackCustomer(String id, String rate, String createAt, String comments, String ordersRef, String name) {
        this.id = id;
        this.rate = rate;
        this.createAt = createAt;
        this.comments = comments;
        this.ordersRef = ordersRef;
        this.name = name;
    }

    protected FeedbackCustomer(Parcel in) {
        id = in.readString();
        rate = in.readString();
        createAt = in.readString();
        comments = in.readString();
        ordersRef = in.readString();
        name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(rate);
        dest.writeString(createAt);
        dest.writeString(comments);
        dest.writeString(ordersRef);
        dest.writeString(name);
    }

    @Override
    public int describeContents() {
        return 0;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}