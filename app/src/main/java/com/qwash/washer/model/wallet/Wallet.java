package com.qwash.washer.model.wallet;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.qwash.washer.utils.TextUtils;
import com.qwash.washer.utils.Utils;

public class Wallet implements Parcelable {

    @SerializedName("topupId")
    @Expose
    private String topupId;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("kredit")
    @Expose
    private Integer kredit;
    @SerializedName("debit")
    @Expose
    private Integer debit;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("description")
    @Expose
    private String description;

    protected Wallet(Parcel in) {
        topupId = in.readString();
        userId = in.readString();
        createdAt = in.readString();
        description = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(topupId);
        dest.writeString(userId);
        dest.writeString(createdAt);
        dest.writeString(description);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Wallet> CREATOR = new Creator<Wallet>() {
        @Override
        public Wallet createFromParcel(Parcel in) {
            return new Wallet(in);
        }

        @Override
        public Wallet[] newArray(int size) {
            return new Wallet[size];
        }
    };

    public String getTopupId() {
        return topupId;
    }

    public void setTopupId(String topupId) {
        this.topupId = topupId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getKredit() {
        return kredit;
    }

    public void setKredit(Integer kredit) {
        this.kredit = kredit;
    }

    public Integer getDebit() {
        return debit;
    }

    public void setDebit(Integer debit) {
        this.debit = debit;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKreditRupiah() {
        return Utils.Rupiah(this.kredit);
    }

    public String getDebitRupiah() {
        return Utils.Rupiah(this.debit);
    }
}