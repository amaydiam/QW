package com.qwash.washer.api.model.washer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Amay on 1/12/2017.
 */
public class DataWasher {

    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("type")
    @Expose
    private Integer type;
    @SerializedName("fullName")
    @Expose
    private String fullName;
    @SerializedName("saldo")
    @Expose
    private Integer saldo;
    @SerializedName("firebaseId")
    @Expose
    private String firebaseId;
    @SerializedName("geometryLat")
    @Expose
    private Double geometryLat;
    @SerializedName("geometryLong")
    @Expose
    private Double geometryLong;
    @SerializedName("profileBirthdate")
    @Expose
    private String profileBirthdate;
    @SerializedName("profileGender")
    @Expose
    private String profileGender;
    @SerializedName("profilePhoto")
    @Expose
    private String profilePhoto;
    @SerializedName("profileProvince")
    @Expose
    private String profileProvince;
    @SerializedName("profileCity")
    @Expose
    private String profileCity;
    @SerializedName("profileNik")
    @Expose
    private String profileNik;
    @SerializedName("online")
    @Expose
    private Integer online;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getSaldo() {
        return saldo;
    }

    public void setSaldo(Integer saldo) {
        this.saldo = saldo;
    }

    public String getFirebaseId() {
        return firebaseId;
    }

    public void setFirebaseId(String firebaseId) {
        this.firebaseId = firebaseId;
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

    public String getProfileBirthdate() {
        return profileBirthdate;
    }

    public void setProfileBirthdate(String profileBirthdate) {
        this.profileBirthdate = profileBirthdate;
    }

    public String getProfileGender() {
        return profileGender;
    }

    public void setProfileGender(String profileGender) {
        this.profileGender = profileGender;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getProfileProvince() {
        return profileProvince;
    }

    public void setProfileProvince(String profileProvince) {
        this.profileProvince = profileProvince;
    }

    public String getProfileCity() {
        return profileCity;
    }

    public void setProfileCity(String profileCity) {
        this.profileCity = profileCity;
    }

    public String getProfileNik() {
        return profileNik;
    }

    public void setProfileNik(String profileNik) {
        this.profileNik = profileNik;
    }

    public Integer getOnline() {
        return online;
    }

    public void setOnline(Integer online) {
        this.online = online;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
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

}