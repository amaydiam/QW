package com.qwash.washer.model.wash_history;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.qwash.washer.utils.Utils;

public class WashHistory implements Parcelable {



    @SerializedName("orders_id")
    @Expose
    private String ordersId;
    @SerializedName("user_id_fk")
    @Expose
    private String userIdFk;
    @SerializedName("washer_id_fk")
    @Expose
    private String washerIdFk;
    @SerializedName("vehicles")
    @Expose
    private String vehicles;
    @SerializedName("create_at")
    @Expose
    private String createAt;
    @SerializedName("pick_date")
    @Expose
    private String pickDate;
    @SerializedName("pick_time")
    @Expose
    private String pickTime;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lng")
    @Expose
    private String lng;
    @SerializedName("name_address")
    @Expose
    private String nameAddress;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("perfumed")
    @Expose
    private Object perfumed;
    @SerializedName("vacuum")
    @Expose
    private String vacuum;
    @SerializedName("waterless")
    @Expose
    private String waterless;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("orders_ref")
    @Expose
    private String ordersRef;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("rate")
    @Expose
    private String rate;


    public WashHistory(String ordersId, String userIdFk, String washerIdFk, String vehicles, String createAt, String pickDate, String pickTime, String lat, String lng, String nameAddress, String address, String price, Object perfumed, String vacuum, String waterless, String status, String description, String ordersRef, String name, String rate) {
        this.ordersId = ordersId;
        this.userIdFk = userIdFk;
        this.washerIdFk = washerIdFk;
        this.vehicles = vehicles;
        this.createAt = createAt;
        this.pickDate = pickDate;
        this.pickTime = pickTime;
        this.lat = lat;
        this.lng = lng;
        this.nameAddress = nameAddress;
        this.address = address;
        this.price = price;
        this.perfumed = perfumed;
        this.vacuum = vacuum;
        this.waterless = waterless;
        this.status = status;
        this.description = description;
        this.ordersRef = ordersRef;
        this.name = name;
        this.rate = rate;
    }

    protected WashHistory(Parcel in) {
        ordersId = in.readString();
        userIdFk = in.readString();
        washerIdFk = in.readString();
        vehicles = in.readString();
        createAt = in.readString();
        pickDate = in.readString();
        pickTime = in.readString();
        lat = in.readString();
        lng = in.readString();
        nameAddress = in.readString();
        address = in.readString();
        price = in.readString();
        vacuum = in.readString();
        waterless = in.readString();
        status = in.readString();
        description = in.readString();
        ordersRef = in.readString();
        name = in.readString();
        rate = in.readString();
    }

    public static final Creator<WashHistory> CREATOR = new Creator<WashHistory>() {
        @Override
        public WashHistory createFromParcel(Parcel in) {
            return new WashHistory(in);
        }

        @Override
        public WashHistory[] newArray(int size) {
            return new WashHistory[size];
        }
    };

    public String getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(String ordersId) {
        this.ordersId = ordersId;
    }

    public String getUserIdFk() {
        return userIdFk;
    }

    public void setUserIdFk(String userIdFk) {
        this.userIdFk = userIdFk;
    }

    public String getWasherIdFk() {
        return washerIdFk;
    }

    public void setWasherIdFk(String washerIdFk) {
        this.washerIdFk = washerIdFk;
    }

    public String getVehicles() {
        return vehicles;
    }

    public void setVehicles(String vehicles) {
        this.vehicles = vehicles;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getPickDate() {
        return pickDate;
    }

    public void setPickDate(String pickDate) {
        this.pickDate = pickDate;
    }

    public String getPickTime() {
        return pickTime;
    }

    public void setPickTime(String pickTime) {
        this.pickTime = pickTime;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getNameAddress() {
        return nameAddress;
    }

    public void setNameAddress(String nameAddress) {
        this.nameAddress = nameAddress;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPrice() {
        return price;
    }

    public String getPriceOnRupiah() {
        return Utils.Rupiah(price);
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Object getPerfumed() {
        return perfumed;
    }

    public void setPerfumed(Object perfumed) {
        this.perfumed = perfumed;
    }

    public String getVacuum() {
        return vacuum;
    }

    public void setVacuum(String vacuum) {
        this.vacuum = vacuum;
    }

    public String getWaterless() {
        return waterless;
    }

    public void setWaterless(String waterless) {
        this.waterless = waterless;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(ordersId);
        parcel.writeString(userIdFk);
        parcel.writeString(washerIdFk);
        parcel.writeString(vehicles);
        parcel.writeString(createAt);
        parcel.writeString(pickDate);
        parcel.writeString(pickTime);
        parcel.writeString(lat);
        parcel.writeString(lng);
        parcel.writeString(nameAddress);
        parcel.writeString(address);
        parcel.writeString(price);
        parcel.writeString(vacuum);
        parcel.writeString(waterless);
        parcel.writeString(status);
        parcel.writeString(description);
        parcel.writeString(ordersRef);
        parcel.writeString(name);
        parcel.writeString(rate);
    }
}