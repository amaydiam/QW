package com.qwash.washer.model;

import com.qwash.washer.utils.TextUtils;
import com.qwash.washer.utils.Utils;

import org.json.JSONObject;

import java.io.Serializable;

public class PrepareOrder implements Serializable {

    //
    private String washersId;

    // customer order
    private String customersId;
    private String username;
    private String email;
    private String name;
    private String firebase_id;

    // address order
    private String usersDetailsId;
    private String userIdFk;
    private String nameAddress;
    private String address;
    private String lat;
    private String Long;
    private String type;

    //vehicle order
    private int vehicles_type;
    private int vehicles;

    //order detail
    private int price;
    private int perfum_price;
    private int perfum_status;

    private int interior_vaccum_price;
    private int interior_vaccum_status;

    private int waterless_price;
    private int waterless_status;

    private int estimated_price;


    public PrepareOrder(String washersId, String customersId, String username, String email, String name, String firebase_id, String usersDetailsId, String userIdFk, String nameAddress, String address, String lat, String Long, String type, int vehicles_type, int vehicles, int price, int perfum_price, int perfum_status, int interior_vaccum_price, int interior_vaccum_status, int waterless_price, int waterless_status, int estimated_price) {
        this.washersId = washersId;
        this.customersId = customersId;
        this.username = username;
        this.email = email;
        this.name = name;
        this.firebase_id = firebase_id;
        this.usersDetailsId = usersDetailsId;
        this.userIdFk = userIdFk;
        this.nameAddress = nameAddress;
        this.address = address;
        this.lat = lat;
        this.Long = Long;
        this.type = type;
        this.vehicles_type = vehicles_type;
        this.vehicles = vehicles;
        this.price = price;
        this.perfum_price = perfum_price;
        this.perfum_status = perfum_status;
        this.interior_vaccum_price = interior_vaccum_price;
        this.interior_vaccum_status = interior_vaccum_status;
        this.waterless_price = waterless_price;
        this.waterless_status = waterless_status;
        this.estimated_price = estimated_price;
    }


    public String getWashersId() {
        return washersId;
    }

    public void setWashersId(String washersId) {
        this.washersId = washersId;
    }

    public String getCustomersId() {
        return customersId;
    }

    public void setCustomersId(String customersId) {
        this.customersId = customersId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getFirebase_id() {
        return firebase_id;
    }

    public void setFirebase_id(String firebase_id) {
        this.firebase_id = firebase_id;
    }

    public String getUsersDetailsId() {
        return usersDetailsId;
    }

    public void setUsersDetailsId(String usersDetailsId) {
        this.usersDetailsId = usersDetailsId;
    }

    public String getUserIdFk() {
        return userIdFk;
    }

    public void setUserIdFk(String userIdFk) {
        this.userIdFk = userIdFk;
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

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLong() {
        return Long;
    }

    public void setLong(String aLong) {
        Long = aLong;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getVehicles_type() {
        return vehicles_type;
    }

    public void setVehicles_type(int vehicles_type) {
        this.vehicles_type = vehicles_type;
    }

    public int getVehicles() {
        return vehicles;
    }

    public void setVehicles(int vehicles) {
        this.vehicles = vehicles;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPerfum_price() {
        return perfum_price;
    }

    public void setPerfum_price(int perfum_price) {
        this.perfum_price = perfum_price;
    }

    public int getPerfum_status() {
        return perfum_status;
    }

    public void setPerfum_status(int perfum_status) {
        this.perfum_status = perfum_status;
    }

    public int getInterior_vaccum_price() {
        return interior_vaccum_price;
    }

    public void setInterior_vaccum_price(int interior_vaccum_price) {
        this.interior_vaccum_price = interior_vaccum_price;
    }

    public int getInterior_vaccum_status() {
        return interior_vaccum_status;
    }

    public void setInterior_vaccum_status(int interior_vaccum_status) {
        this.interior_vaccum_status = interior_vaccum_status;
    }

    public int getWaterless_price() {
        return waterless_price;
    }

    public void setWaterless_price(int waterless_price) {
        this.waterless_price = waterless_price;
    }

    public int getWaterless_status() {
        return waterless_status;
    }

    public void setWaterless_status(int waterless_status) {
        this.waterless_status = waterless_status;
    }

    public int getEstimated_price() {
        return estimated_price;
    }

    public String getEstimated_priceOnPrice() {
        return Utils.Rupiah(estimated_price);
    }

    public void setEstimated_price(int estimated_price) {
        this.estimated_price = estimated_price;
    }
}
