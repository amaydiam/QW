package com.qwash.washer.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Vehicle implements Parcelable {

    // Parcelable Creator
    public static final Creator CREATOR = new Creator() {
        public Vehicle createFromParcel(Parcel in) {
            return new Vehicle(in);
        }

        public Vehicle[] newArray(int size) {
            return new Vehicle[size];
        }
    };
    // Attributes
    public String id_vehicle;
    public int type;
    public String brand;
    public String model;
    public String transmission;
    public String year;

    // Constructor
    public Vehicle(String id_vehicle, int type, String brand, String model, String transmission, String year) {
        this.id_vehicle = id_vehicle;
        this.type = type;
        this.brand = brand;
        this.model = model;
        this.transmission = transmission;
        this.year = year;
    }

    public Vehicle(Parcel in) {
        this.id_vehicle = in.readString();
        this.type = in.readInt();
        this.brand = in.readString();
        this.model = in.readString();
        this.transmission = in.readString();
        this.year = in.readString();
    }

    // Parcelling methods
    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeString(id_vehicle);
        out.writeInt(type);
        out.writeString(brand);
        out.writeString(model);
        out.writeString(transmission);
        out.writeString(year);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
