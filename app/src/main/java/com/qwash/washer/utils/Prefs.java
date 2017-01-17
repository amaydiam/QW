package com.qwash.washer.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.qwash.washer.Sample;


public final class Prefs {

    public static SharedPreferences get(final Context context) {
        return context.getSharedPreferences("com.qwash.user",
                Context.MODE_PRIVATE);
    }

    public static String getPref(final Context context, String pref, String def) {
        SharedPreferences prefs = Prefs.get(context);
        String val = prefs.getString(pref, def);

        if (TextUtils.isNullOrEmpty(val))
            return def;
        else
            return val;
    }

    public static void putPref(final Context context, String pref, String val) {
        SharedPreferences prefs = Prefs.get(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(pref, val);
        editor.apply();
    }



    //Data User
    public static void putFirebaseId(final Context context, String token) {
        Prefs.putPref(context, Sample.FIREBASE_ID, token);
    }

    public static String getFirebaseId(final Context context) {
        return Prefs.getPref(context, Sample.FIREBASE_ID, "");
    }

    public static void putToken(final Context context, String token) {
        Prefs.putPref(context, Sample.TOKEN, token);
    }

    public static String getToken(final Context context) {
        return Prefs.getPref(context, Sample.TOKEN, "");
    }

        public static void putUserId(final Context context, String id_user) {
        Prefs.putPref(context, Sample.USER_ID, id_user);
    }

    public static String getUserId(final Context context) {
        return Prefs.getPref(context, Sample.USER_ID, "");
    }

    public static void putName(final Context context, String name) {
        Prefs.putPref(context, Sample.NAME, name);
    }

    public static String getName(final Context context) {
        return Prefs.getPref(context, Sample.NAME, "");
    }

    public static void putAuthLevel(final Context context, String auth_level) {
        Prefs.putPref(context, Sample.AUTH_LEVEL, auth_level);
    }

    public static String getAuthLevel(final Context context) {
        return Prefs.getPref(context, Sample.AUTH_LEVEL, "");
    }

    public static void putEmail(final Context context, String email) {
        Prefs.putPref(context, Sample.EMAIL, email);
    }

    public static String getEmail(final Context context) {
        return Prefs.getPref(context, Sample.EMAIL, "");
    }

    public static void putUsername(final Context context, String username) {
        Prefs.putPref(context, Sample.USERNAME, username);
    }

    public static String getUsername(final Context context) {
        return Prefs.getPref(context, Sample.USERNAME, "");
    }


    public static void putPhone(final Context context, String phone) {
        Prefs.putPref(context, Sample.PHONE, phone);
    }

    public static String getPhone(final Context context) {
        return Prefs.getPref(context, Sample.PHONE, "");
    }

    //end data user


    public static void putLatitude(final Context context, float latitude) {
        Prefs.putPref(context, Sample.LATITUDE, String.valueOf(latitude));
    }

    public static float getLatitude(Context context) {
        return Float.parseFloat(Prefs.getPref(context, Sample.LATITUDE, "0f"));
    }

    public static void putLongitude(final Context context, float Longitude) {
        Prefs.putPref(context, Sample.LONGITUDE, String.valueOf(Longitude));
    }

    public static float getLongitude(Context context) {
        return Float.parseFloat(Prefs.getPref(context, Sample.LONGITUDE, "0f"));
    }

    public static boolean isLogedIn(Context context) {

        if (getUserId(context).length() > 0
                && getToken(context).length() > 0) {
            return true;
        }

        return false;
    }


    public static void Reset(Context context) {
        putToken(context,"");
        putUserId(context,"");
        putName(context,"");
        putAuthLevel(context,"");
        putEmail(context,"");
        putUsername(context,"");
        putPhone(context,"");
        putLatitude(context,0);
        putLongitude(context,0);
    }


}