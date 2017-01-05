package com.qwash.washer.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class History implements Parcelable {

    // Attributes
    @SerializedName("id_history")
    @Expose
    public String id_history;
    @SerializedName("history_time")
    @Expose
    public String history_time;
    @SerializedName("address")
    @Expose
    public String address;
    @SerializedName("vehicle_model")
    @Expose
    public String vehicle_model;

    // Constructor
    public History(String id_history, String history_time, String address, String vehicle_model) {
        this.id_history = id_history;
        this.history_time = history_time;
        this.address = address;
        this.vehicle_model = vehicle_model;
    }

    public History(Parcel in) {
        this.id_history = in.readString();
        this.history_time = in.readString();
        this.address = in.readString();
        this.vehicle_model = in.readString();
    }

    public String getId_history() {
        return id_history;
    }

    public void setId_history(String id_history) {
        this.id_history = id_history;
    }

    public String getHistory_time() {
        return history_time;
    }

    public void setHistory_time(String history_time) {
        this.history_time = history_time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getVehicle_model() {
        return vehicle_model;
    }

    public void setVehicle_model(String vehicle_model) {
        this.vehicle_model = vehicle_model;
    }

    // Parcelable Creator
    public static final Creator CREATOR = new Creator() {
        public History createFromParcel(Parcel in) {
            return new History(in);
        }

        public History[] newArray(int size) {
            return new History[size];
        }
    };

    // Parcelling methods
    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeString(id_history);
        out.writeString(history_time);
        out.writeString(address);
        out.writeString(vehicle_model);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
