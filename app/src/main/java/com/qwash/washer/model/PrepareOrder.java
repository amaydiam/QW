package com.qwash.washer.model;

import java.io.Serializable;

public class PrepareOrder implements Serializable {

    //
    public String orders_ref;

    // customer order
    public String userId;
    public String username;
    public String email;
    public String name;
    public String phone;
    public String photo;
    public String authLevel;
    public String firebase_id;

    // address order
    public String usersDetailsId;
    public String userIdFk;
    public String nameAddress;
    public String address;
    public String latlong;
    public String type;

    //vehicle order
    public int vehicles_type;
    public int vehicles;

    //order detail
    public int price;
    public int perfum_price;
    public int perfum_status;

    public int interior_vaccum_price;
    public int interior_vaccum_status;

    public int waterless_price;
    public int waterless_status;

    public int estimated_price;

    public String pick_date;
    public String pick_time;


}
