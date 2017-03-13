package com.qwash.washer;


public class Sample {

    public static final String BASE_URL_QWASH = "http://apis.aanaliudin.com/index.php/api/";
    public static final String BASE_URL_IMAGE = " http://apis.aanaliudin.com/sources/images/profile/";

    public static final String IS_FINISH_LOADING_AWAL_DATA = "is_loading";
    public static final String IS_LOADING_MORE_DATA = "is_locked";
    public static final String KEYWORD = "keyword";
    public static final String TAG_FRAGMENT = "fargment";
    public static final String CITY = "city";
    public static final String LOCK_AFTER_REGISTER = "lock_after_register";
    public static final String PROGRESS_WORKING = "lock_get_order";
    public static final String SALDO = "saldo";
    public static final String LIMIT_DATA = "10";
    public static final String PAGE = "page";
    public static final String DATA = "data";


    public static final String SERVER_KEY_FIREBASE = "AAAA2kHlMFE:APA91bHojkPnnebXqNs8IWgL37K0HCvFJyXkipFXbTbYmigL1bsUPJIjv5P1kJDgBbWzbzR6QkxMGNMgLDzb7f_uC-BFfzhb7cEbwnov5ErnjvHSyRw0gKBtfiCqKhz55pOr0zgyld2z";
    public static final String FCM_MESSAGE_URL = "https://fcm.googleapis.com/fcm/send";

    public static final String message = "message";

    public static final String FIREBASE_ID = "firebase_id";
    public static final String USER_ID = "user_id";
    public static final String USERNAME = "username";
    public static final String TOKEN = "token";
    public static final String NAME = "name";
    public static final String AUTH_LEVEL = "auth_level";
    public static final String MESSAGE = "message";
    public static final String LATITUDE = "Latitude";
    public static final String LONGITUDE = "Longitude";
    public static final String PHONE = "phone";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String PHOTO = "photo";

    public static final String ACTION = "action";
    public static final String ORDER = "order";

    public static final String CUSTOMER_ORDER = "customer_order";
    public static final String CUSTOMER = "customer";
    public static final String ADDRESS = "address";
    public static final String VEHICLE = "vehicle";
    public static final String DETAILS = "details";


    // customer order
    public static final String ORDER_USERID = "userId";
    public static final String ORDER_USERNAME = "username";
    public static final String ORDER_EMAIL = "email";
    public static final String ORDER_NAME = "name";
    public static final String ORDER_PHONE = "phone";
    public static final String ORDER_PHOTO = "photo";
    public static final String ORDER_AUTHLEVEL = "authLevel";
    public static final String ORDER_FIREBASE_ID = "firebase_id";

    // address order
    public static final String ORDER_USERSDETAILSID = "usersDetailsId";
    public static final String ORDER_USERIDFK = "userIdFk";
    public static final String ORDER_NAMEADDRESS = "nameAddress";
    public static final String ORDER_ADDRESS = "address";
    public static final String ORDER_LATLONG = "latlong";
    public static final String ORDER_TYPE = "type";

    //vehicle order
    public static final String ORDER_VEHICLES_TYPE = "vehicles_type";
    public static final String ORDER_VEHICLES = "vehicles";

    //order detail
    public static final String ORDER_PRICE = "price";

    public static final String ORDER_PERFUM_PRICE = "perfum_price";
    public static final String ORDER_PERFUM_STATUS = "perfum_stattus";

    public static final String ORDER_INTERIOR_VACUUM_PRICE = "interior_vacuum";
    public static final String ORDER_INTERIOR_VACUUM_STATUS = "interior_vacuum_status";

    public static final String ORDER_WATERLESS_PRICE = "waterless_price";
    public static final String ORDER_WATERLESS_STATUS = "waterless_status";

    public static final String ORDER_ESTIMATED_PRICE = "estimated_price";


    //PickOrder Accept
    public static final String WASHER_PHONE = "phone";
    public static final String WASHER_PHOTO = "photo";
    public static final String WASHER_NAME = "name";
    public static final String WASHER_EMAIL = "email";
    public static final String WASHER_USER_ID = "userId";
    public static final String WASHER_FIREBASE_ID = "firebase_id";

    public static final String WASHER = "washer";
    public static final String REGISTRATION_IDS = "registration_ids";
    public static final String WASHER_RATING = "rating";
    public static final String RATING = "rating";

    //

    public static final int CODE_NO_ORDER = 1;
    public static final int CODE_GET_ORDER = 2;
    public static final int CODE_DEACLINE_ORDER = 3;
    public static final int CODE_ACCEPT_ORDER = 4;
    public static final int CODE_START_WORKING = 5;
    public static final int CODE_FINISH_WORKING = 6;


    //

    public static final String WASHER_ID = "washer_id";
    public static final String ORDERS_REF = "orders_ref";


    public static final String ACTION_FROM_NOTIFICATION = "isFromNotification";
    public static final String LAT = "lat";
    public static final String LONG = "long";
    public static final String VACCUM = "vacuum";

    public static final String AVAILABLE_FOR_JOB = "available_for_job";


    public static final int ACTION_ORDER = 1;
    public static final int ACTION_CANCEL_ORDER = 2;
    public static final int ACTION_OPEN_FEED_ORDER = 3;


    public static final String TYPE_WASH_HISTORY = "type_wash_history";
    public static final int WASH_HISTORY_IN_PROGRESS = 1;
    public static final int WASH_HISTORY_COMPLETE = 2;
    public static final String NIK = "nik";
    public static final String BIRTHDATE = "birthdate";
    public static final String GENDER = "gender";
    public static final String PROVINCE = "province";


    public static final int VEHICLE_CAR = 1;
    public static final int VEHICLE_CAR_CITY_CAR = 1;
    public static final int VEHICLE_CAR_MINIVAN = 2;
    public static final int VEHICLE_CAR_SUV = 3;

    public static final int VEHICLE_MOTORCYCLE = 2;

    public static final int VEHICLE_MOTORCYCLE_UNDER_150 = 4;
    public static final int VEHICLE_MOTORCYCLE_150 = 5;
    public static final int VEHICLE_MOTORCYCLE_ABOVE_150 = 6;
}
