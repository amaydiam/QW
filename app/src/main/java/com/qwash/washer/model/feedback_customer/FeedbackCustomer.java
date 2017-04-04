package com.qwash.washer.model.feedback_customer;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.vision.text.Text;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.qwash.washer.utils.TextUtils;

public class FeedbackCustomer implements Parcelable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("rate")
    @Expose
    private String rate;
    @SerializedName("comments")
    @Expose
    private String comments;
    @SerializedName("create_at")
    @Expose
    private String createAt;
    @SerializedName("fullName")
    @Expose
    private String fullName;

    public FeedbackCustomer(String id, String rate, String comments, String createAt,  String name) {
        this.id = id;
        this.rate = rate;
        this.comments = comments;
        this.createAt = createAt;
        this.fullName = name;
    }

    protected FeedbackCustomer(Parcel in) {
        id = in.readString();
        rate = in.readString();
        comments = in.readString();
        createAt = in.readString();
        fullName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(rate);
        dest.writeString(comments);
        dest.writeString(createAt);
        dest.writeString(fullName);
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

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getCreateAt() {
        return TextUtils.DefaultDateFormat(createAt);
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

}