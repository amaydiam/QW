package com.qwash.washer.model;

import java.io.Serializable;

public class PrepareOrder implements Serializable {
    
    // customer order
    public String userId;
    public String username;
    public String email;
    public String name;
    public String phone;
    public String authLevel;
    public String firebase_id;

    // address order
    public String usersDetailsId;
    public String  userIdFk;
    public String  nameAddress;
    public String  address;
    public String  latlong;
    public String  type;

    //vehicle order
    public String vCustomersId;
    public String vName;
    public String vBrand;
    public String models;
    public String vTransmision;
    public String years;
    public String vId;
    public String vBrandId;
    public String vModelId;
    public String vTransId;
    public String vYearsId;

    //order detail
    public int price;
    public int perfumed;
    public int interior_vaccum;
    public int estimated_price;
    public String datetime;


}
