package com.qwash.washer.model.register;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.qwash.washer.utils.Utils;

public class VerifyTools implements Parcelable {

    int resource;
    String des;
    String price;

    public VerifyTools(int resource, String des, String price) {
        this.resource = resource;
        this.des = des;
        this.price = price;
    }

    protected VerifyTools(Parcel in) {
        resource = in.readInt();
        des = in.readString();
        price = in.readString();
    }

    public static final Creator<VerifyTools> CREATOR = new Creator<VerifyTools>() {
        @Override
        public VerifyTools createFromParcel(Parcel in) {
            return new VerifyTools(in);
        }

        @Override
        public VerifyTools[] newArray(int size) {
            return new VerifyTools[size];
        }
    };

    public int getResource() {
        return resource;
    }

    public void setResource(int resource) {
        this.resource = resource;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(resource);
        dest.writeString(des);
        dest.writeString(price);
    }
}