package com.qwash.washer.api.model.order;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Amay on 3/30/2017.
 */

public class DataPickOrder  {

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
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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