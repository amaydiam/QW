package com.qwash.washer;


import android.content.Context;

public class Sample {

    public static final String BASE_URL_QWASH_PUBLIC = "http://api.qwash-indonesia.com:1337/public/";
    public static final String BASE_URL_QWASH_API = "http://api.qwash-indonesia.com:1337/api/v1/";

    public static final String IS_FINISH_LOADING_AWAL_DATA = "is_loading";
    public static final String IS_LOADING_MORE_DATA = "is_locked";
    public static final String KEYWORD = "keyword";
    public static final String TAG_FRAGMENT = "fargment";
    public static final String CITY = "city";
    public static final String ACTIVITY_INDEX = "lock_after_register";
    public static final String PROGRESS_WORKING = "lock_get_order";
    public static final String LIMIT_DATA = "10";
    public static final String PAGE = "page";
    public static final String DATA = "data";


    public static final String SERVER_KEY_FIREBASE = "AAAA2kHlMFE:APA91bHojkPnnebXqNs8IWgL37K0HCvFJyXkipFXbTbYmigL1bsUPJIjv5P1kJDgBbWzbzR6QkxMGNMgLDzb7f_uC-BFfzhb7cEbwnov5ErnjvHSyRw0gKBtfiCqKhz55pOr0zgyld2z";
    public static final String FCM_MESSAGE_URL = "https://fcm.googleapis.com/fcm/send";

    public static final String message = "message";

    public static final String TOKEN = "token";
    public static final String AUTH_LEVEL = "auth_level";
    public static final String MESSAGE = "message";
    public static final String LATITUDE = "lat";
    public static final String LONGITUDE = "long";
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
    public static final String ORDER_USERID = "customersId";
    public static final String ORDER_USERNAME = "username";
    public static final String ORDER_EMAIL = "email";
    public static final String ORDER_NAME = "name";
    public static final String ORDER_PHOTO = "photo";
    public static final String ORDER_AUTHLEVEL = "authLevel";
    public static final String ORDER_FIREBASE_ID = "firebase_id";

    // address order
    public static final String ORDER_USERSDETAILSID = "usersDetailsId";
    public static final String ORDER_USERIDFK = "userIdFk";
    public static final String ORDER_NAMEADDRESS = "nameAddress";
    public static final String ORDER_ADDRESS = "address";
    public static final String ORDER_LAT= "lat";
    public static final String ORDER_LONG = "long";
    public static final String ORDER_TYPE = "type";

    //vehicle order
    public static final String ORDER_VEHICLES_TYPE = "vehicles_type";
    public static final String ORDER_VEHICLES = "vehicles";

    //order detail
    public static final String ORDER_TOKEN = "token";
    public static final String ORDER_PRICE = "price";

    public static final String ORDER_PERFUM_PRICE = "perfum_price";
    public static final String ORDER_PERFUM_STATUS = "perfum_stattus";

    public static final String ORDER_INTERIOR_VACUUM_PRICE = "interior_vacuum";
    public static final String ORDER_INTERIOR_VACUUM_STATUS = "interior_vacuum_status";

    public static final String ORDER_WATERLESS_PRICE = "waterless_price";
    public static final String ORDER_WATERLESS_STATUS = "waterless_status";

    public static final String ORDER_ESTIMATED_PRICE = "estimated_price";


    public static final String WASHER = "washer";
    public static final String REGISTRATION_IDS = "registration_ids";
    public static final String RATING = "rating";

    //

    // kondisi dimana blum lagi dapet order, berarti order yg masuk bisa tampil
    public static final int CODE_NO_ORDER = 1;

    // kondisi dimana orderan sedang tampil di layar dan belum diambil,
    // berarti kalo ada order yg masuk ga ditampilin
    public static final int CODE_GET_ORDER = 2;

    // kondisi dimana orderan ditolak
    public static final int CODE_DEACLINE_ORDER = 3;

    // kondisi dimana orderan yg  tampil di layar dan telah diterima
    public static final int CODE_ACCEPT_ORDER = 4;

    // kondisi dimana washer memulai pencucian pada orderan yg  telah diterima
    public static final int CODE_START_WORKING = 5;

    // kondisi dimana washer selesai pencucian pada orderan yg  telah diterima
    public static final int CODE_FINISH_WORKING = 6;


    public static final String ORDERS_REF = "orders_ref";


    public static final String ACTION_FROM_NOTIFICATION = "isFromNotification";
    public static final int notifID = 1001;
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

    public static final String CODE = "code";
    public static final String AUTHORIZATION = "Authorization";
    public static final int NO_INDEX = 0;
    public static final int ACTIVATION_CODE_INDEX = 1;
    public static final int VERIFY_DOCUMENT_INDEX = 2;
    public static final int VERIFY_TOOLS_INDEX = 3;
    public static final int LOCK_MAP_AFTER_REGISTER_INDEX = 4;

    public static final int ID_NOTIF_ORDER = 1008;

    public static final String CUSTOMERS_ID = "customersId";
    public static final String PRICE = "price";
    public static final String NAMEADDRESS = "nameAddress";
    public static final String PERFUM ="perfum";
    public static final String VACUM = "vacum";
    public static final String WATERLESS = "waterless";
    public static final String SERVICES = "services";
    public static final String ORDERS_ID = "ordersId";
    public static final String VEHICLES = "vehicles";

    public static final String URL_PUSAT_BANTUAN_WASHER = "https://qwash-indonesia.com/panduan-washer";

    public static final String[] listMontOfYear(Context context) {
        return new String[]{
                context.getString(R.string.january),
                context.getString(R.string.february),
                context.getString(R.string.march),
                context.getString(R.string.april),
                context.getString(R.string.may),
                context.getString(R.string.june),
                context.getString(R.string.july),
                context.getString(R.string.august),
                context.getString(R.string.september),
                context.getString(R.string.october),
                context.getString(R.string.november),
                context.getString(R.string.december)
        };
    }


    public static String USER_ID = "userId";
    public static String EMAIL = "email";
    public static String USERNAME = "username";
    public static String TYPE = "type";
    public static String FULL_NAME = "fullName";
    public static String SALDO = "saldo";
    public static String FIREBASE_ID = "firebaseId";
    public static String GEOMETRY_LAT = "geometryLat";
    public static String GEOMETRY_LONG = "geometryLong";
    public static String PROFILE_BIRTHDATE = "profileBirthdate";
    public static String PROFILE_GENDER = "profileGender";
    public static String PROFILE_PHOTO = "profilePhoto";
    public static String PROFILE_PROVINCE = "profileProvince";
    public static String PROFILE_CITY = "profileCity";
    public static String PROFILE_NIK = "profileNik";
    public static String ONLINE = "online";
    public static String STATUS = "status";
    public static String CREATED_AT = "createdAt";
    public static String UPDATED_AT = "updatedAt";

    //Washer Accept
    public static final String WASHER_USERNAME = "username";
    public static final String WASHER_PHOTO = "photo";
    public static final String WASHER_NAME = "name";
    public static final String WASHER_EMAIL = "email";
    public static final String WASHERS_ID = "washersId";
    public static final String WASHER_FIREBASE_ID = "firebase_id";
    public static final String WASHER_RATING = "rating";

}
