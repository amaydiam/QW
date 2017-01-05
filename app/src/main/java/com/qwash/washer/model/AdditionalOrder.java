package com.ad.sample.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdditionalOrder implements Parcelable {

    // Attributes
    @SerializedName("img_additional_order")
    @Expose
    public int img_additional_order;
    @SerializedName("additional_order")
    @Expose
    public String additional_order;

    // Constructor
    public AdditionalOrder(int img_additional_order, String additional_order) {
        this.img_additional_order = img_additional_order;
        this.additional_order = additional_order;
    }

    public AdditionalOrder(Parcel in) {
        this.img_additional_order = in.readInt();
        this.additional_order = in.readString();
    }

    public int getImg_additional_order() {
        return img_additional_order;
    }

    public void setImg_additional_order(int img_additional_order) {
        this.img_additional_order = img_additional_order;
    }

    public String getAdditional_order() {
        return additional_order;
    }

    public void setAdditional_order(String additional_order) {
        this.additional_order = additional_order;
    }

    // Parcelable Creator
    public static final Creator CREATOR = new Creator() {
        public AdditionalOrder createFromParcel(Parcel in) {
            return new AdditionalOrder(in);
        }

        public AdditionalOrder[] newArray(int size) {
            return new AdditionalOrder[size];
        }
    };

    // Parcelling methods
    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeInt(img_additional_order);
        out.writeString(additional_order);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
