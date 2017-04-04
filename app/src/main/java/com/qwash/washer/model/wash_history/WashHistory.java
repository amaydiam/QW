package com.qwash.washer.model.wash_history;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.qwash.washer.utils.TextUtils;
import com.qwash.washer.utils.Utils;

public class WashHistory implements Parcelable {

    @SerializedName("ordersId")
    @Expose
    private String ordersId;
    @SerializedName("customersId")
    @Expose
    private String customersId;
    @SerializedName("washersId")
    @Expose
    private String washersId;
    @SerializedName("vehicles")
    @Expose
    private String vehicles;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("geometryLat")
    @Expose
    private Double geometryLat;
    @SerializedName("geometryLong")
    @Expose
    private Double geometryLong;
    @SerializedName("nameAddress")
    @Expose
    private String nameAddress;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("basicPrice")
    @Expose
    private Integer basicPrice;
    @SerializedName("pricePerfumed")
    @Expose
    private Integer pricePerfumed;
    @SerializedName("priceVacumed")
    @Expose
    private Integer priceVacumed;
    @SerializedName("priceWaterless")
    @Expose
    private Integer priceWaterless;
    @SerializedName("grandTotal")
    @Expose
    private Integer grandTotal;
    @SerializedName("services")
    @Expose
    private String services;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("customersName")
    @Expose
    private String customersName;
    @SerializedName("ratings")
    @Expose
    private Integer ratings;

    protected WashHistory(Parcel in) {
        ordersId = in.readString();
        customersId = in.readString();
        washersId = in.readString();
        vehicles = in.readString();
        nameAddress = in.readString();
        address = in.readString();
        services = in.readString();
        description = in.readString();
        createdAt = in.readString();
        updatedAt = in.readString();
        customersName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ordersId);
        dest.writeString(customersId);
        dest.writeString(washersId);
        dest.writeString(vehicles);
        dest.writeString(nameAddress);
        dest.writeString(address);
        dest.writeString(services);
        dest.writeString(description);
        dest.writeString(createdAt);
        dest.writeString(updatedAt);
        dest.writeString(customersName);
    }

    @Override
    public int describeContents() {
        return 0;
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

    public String getCustomersId() {
        return customersId;
    }

    public void setCustomersId(String customersId) {
        this.customersId = customersId;
    }

    public String getWashersId() {
        return washersId;
    }

    public void setWashersId(String washersId) {
        this.washersId = washersId;
    }

    public String getVehicles() {
        return vehicles;
    }

    public void setVehicles(String vehicles) {
        this.vehicles = vehicles;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Double getGeometryLat() {
        return geometryLat;
    }

    public void setGeometryLat(Double geometryLat) {
        this.geometryLat = geometryLat;
    }

    public Double getGeometryLong() {
        return geometryLong;
    }

    public void setGeometryLong(Double geometryLong) {
        this.geometryLong = geometryLong;
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

    public Integer getBasicPrice() {
        return basicPrice;
    }

    public void setBasicPrice(Integer basicPrice) {
        this.basicPrice = basicPrice;
    }

    public Integer getPricePerfumed() {
        return pricePerfumed;
    }

    public void setPricePerfumed(Integer pricePerfumed) {
        this.pricePerfumed = pricePerfumed;
    }

    public Integer getPriceVacumed() {
        return priceVacumed;
    }

    public void setPriceVacumed(Integer priceVacumed) {
        this.priceVacumed = priceVacumed;
    }

    public Integer getPriceWaterless() {
        return priceWaterless;
    }

    public void setPriceWaterless(Integer priceWaterless) {
        this.priceWaterless = priceWaterless;
    }

    public Integer getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(Integer grandTotal) {
        this.grandTotal = grandTotal;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedAt() {
        return TextUtils.DefaultDateFormat(createdAt);
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCustomersName() {
        return customersName;
    }

    public void setCustomersName(String customersName) {
        this.customersName = customersName;
    }

    public Integer getRatings() {
        return ratings;
    }

    public void setRatings(Integer ratings) {
        this.ratings = ratings;
    }

    public String getGrandTotalRupiah() {
        return Utils.Rupiah(grandTotal);
    }
}