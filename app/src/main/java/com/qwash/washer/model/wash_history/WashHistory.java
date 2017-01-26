package com.qwash.washer.model.wash_history;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WashHistory implements Parcelable {

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
    @SerializedName("orders_id")
    @Expose
    private String ordersId;
    @SerializedName("user_id_fk")
    @Expose
    private String userIdFk;
    @SerializedName("washer_id_fk")
    @Expose
    private String washerIdFk;
    @SerializedName("v_customers_id_fk")
    @Expose
    private String vCustomersIdFk;
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
    private String perfumed;
    @SerializedName("vacuum")
    @Expose
    private String vacuum;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("orders_ref")
    @Expose
    private String ordersRef;
    @SerializedName("v_customers_id")
    @Expose
    private String vCustomersId;
    @SerializedName("v_id")
    @Expose
    private String vId;
    @SerializedName("v_brand_id")
    @Expose
    private String vBrandId;
    @SerializedName("v_model_id")
    @Expose
    private String vModelId;
    @SerializedName("v_trans_id")
    @Expose
    private String vTransId;
    @SerializedName("v_years_id")
    @Expose
    private String vYearsId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("v_id_fk")
    @Expose
    private String vIdFk;
    @SerializedName("v_brand")
    @Expose
    private String vBrand;

    public WashHistory(String ordersId,
                       String userIdFk,
                       String washerIdFk,
                       String vCustomersIdFk,
                       String createAt,
                       String pickDate,
                       String pickTime,
                       String lat,
                       String lng,
                       String nameAddress,
                       String address,
                       String price,
                       String perfumed,
                       String vacuum,
                       String status,
                       String description,
                       String ordersRef,
                       String vCustomersId,
                       String vId,
                       String vBrandId,
                       String vModelId,
                       String vTransId,
                       String vYearsId,
                       String userId,
                       String vIdFk,
                       String vBrand) {
        this.ordersId = ordersId;
        this.userIdFk = userIdFk;
        this.washerIdFk = washerIdFk;
        this.vCustomersIdFk = vCustomersIdFk;
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
        this.status = status;
        this.description = description;
        this.ordersRef = ordersRef;
        this.vCustomersId = vCustomersId;
        this.vId = vId;
        this.vBrandId = vBrandId;
        this.vModelId = vModelId;
        this.vTransId = vTransId;
        this.vYearsId = vYearsId;
        this.userId = userId;
        this.vIdFk = vIdFk;
        this.vBrand = vBrand;
    }

    protected WashHistory(Parcel in) {
        ordersId = in.readString();
        userIdFk = in.readString();
        washerIdFk = in.readString();
        vCustomersIdFk = in.readString();
        createAt = in.readString();
        pickDate = in.readString();
        pickTime = in.readString();
        lat = in.readString();
        lng = in.readString();
        nameAddress = in.readString();
        address = in.readString();
        price = in.readString();
        perfumed = in.readString();
        vacuum = in.readString();
        status = in.readString();
        description = in.readString();
        ordersRef = in.readString();
        vCustomersId = in.readString();
        vId = in.readString();
        vBrandId = in.readString();
        vModelId = in.readString();
        vTransId = in.readString();
        vYearsId = in.readString();
        userId = in.readString();
        vIdFk = in.readString();
        vBrand = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ordersId);
        dest.writeString(userIdFk);
        dest.writeString(washerIdFk);
        dest.writeString(vCustomersIdFk);
        dest.writeString(createAt);
        dest.writeString(pickDate);
        dest.writeString(pickTime);
        dest.writeString(lat);
        dest.writeString(lng);
        dest.writeString(nameAddress);
        dest.writeString(address);
        dest.writeString(price);
        dest.writeString(perfumed);
        dest.writeString(vacuum);
        dest.writeString(status);
        dest.writeString(description);
        dest.writeString(ordersRef);
        dest.writeString(vCustomersId);
        dest.writeString(vId);
        dest.writeString(vBrandId);
        dest.writeString(vModelId);
        dest.writeString(vTransId);
        dest.writeString(vYearsId);
        dest.writeString(userId);
        dest.writeString(vIdFk);
        dest.writeString(vBrand);
    }

    @Override
    public int describeContents() {
        return 0;
    }

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

    public String getVCustomersIdFk() {
        return vCustomersIdFk;
    }

    public void setVCustomersIdFk(String vCustomersIdFk) {
        this.vCustomersIdFk = vCustomersIdFk;
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

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPerfumed() {
        return perfumed;
    }

    public void setPerfumed(String perfumed) {
        this.perfumed = perfumed;
    }

    public String getVacuum() {
        return vacuum;
    }

    public void setVacuum(String vacuum) {
        this.vacuum = vacuum;
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

    public String getVCustomersId() {
        return vCustomersId;
    }

    public void setVCustomersId(String vCustomersId) {
        this.vCustomersId = vCustomersId;
    }

    public String getVId() {
        return vId;
    }

    public void setVId(String vId) {
        this.vId = vId;
    }

    public String getVBrandId() {
        return vBrandId;
    }

    public void setVBrandId(String vBrandId) {
        this.vBrandId = vBrandId;
    }

    public String getVModelId() {
        return vModelId;
    }

    public void setVModelId(String vModelId) {
        this.vModelId = vModelId;
    }

    public String getVTransId() {
        return vTransId;
    }

    public void setVTransId(String vTransId) {
        this.vTransId = vTransId;
    }

    public String getVYearsId() {
        return vYearsId;
    }

    public void setVYearsId(String vYearsId) {
        this.vYearsId = vYearsId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getVIdFk() {
        return vIdFk;
    }

    public void setVIdFk(String vIdFk) {
        this.vIdFk = vIdFk;
    }

    public String getVBrand() {
        return vBrand;
    }

    public void setVBrand(String vBrand) {
        this.vBrand = vBrand;
    }

}