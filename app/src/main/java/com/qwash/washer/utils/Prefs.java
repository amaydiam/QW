package com.qwash.washer.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.qwash.washer.Sample;


public final class Prefs {

    public static SharedPreferences get(final Context context) {
        return context.getSharedPreferences("com.qwash.washer",
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

    public static void putPhoto(final Context context, String photo) {
        Prefs.putPref(context, Sample.PHOTO, photo);
    }

    public static String getPhoto(final Context context) {
        return Prefs.getPref(context, Sample.PHOTO, "");
    }


    public static void putRating(final Context context, String phone) {
        Prefs.putPref(context, Sample.RATING, phone);
    }

    public static String getRating(final Context context) {
        return Prefs.getPref(context, Sample.RATING, "0");
    }


    //end data user


    public static void putAvailableForJob(final Context context, boolean enabled) {
        Prefs.putPref(context, Sample.AVAILABLE_FOR_JOB, (enabled ? "true" : "false"));
    }


    public static boolean getAvailableForJob(final Context context) {
        String e = Prefs.getPref(context, Sample.AVAILABLE_FOR_JOB, "false");
        return e.equals("true");
    }

    public static void putAvailableForVaccum(final Context context, int isAvailableForVaccum) {
        Prefs.putPref(context, Sample.VACCUM, String.valueOf(isAvailableForVaccum));
    }

    public static int getAvailableForVaccum(Context context) {
        return Integer.parseInt(Prefs.getPref(context, Sample.VACCUM, "0"));
    }

    public static void putLatitude(final Context context, double latitude) {
        Prefs.putPref(context, Sample.LATITUDE, String.valueOf(latitude));
    }

    public static float getLatitude(Context context) {
        return Float.parseFloat(Prefs.getPref(context, Sample.LATITUDE, "0f"));
    }

    public static void putLongitude(final Context context, double Longitude) {
        Prefs.putPref(context, Sample.LONGITUDE, String.valueOf(Longitude));
    }

    public static float getLongitude(Context context) {
        return Float.parseFloat(Prefs.getPref(context, Sample.LONGITUDE, "0f"));
    }

    public static boolean isLogedIn(Context context) {

        return getUserId(context).length() > 0
                && getToken(context).length() > 0;

    }


    public static void putLockMapRegister(final Context context, boolean enabled) {
        Prefs.putPref(context, Sample.LOCK_AFTER_REGISTER, (enabled ? "true" : "false"));
    }


    public static boolean getLockMapRegister(final Context context) {
        String e = Prefs.getPref(context, Sample.LOCK_AFTER_REGISTER, "false");
        return e.equals("true");
    }


    public static void putProgresWorking(final Context context, int latitude) {
        Prefs.putPref(context, Sample.PROGRESS_WORKING, String.valueOf(latitude));
    }

    public static int getProgresWorking(Context context) {
        return Integer.parseInt(Prefs.getPref(context, Sample.PROGRESS_WORKING, String.valueOf(Sample.CODE_NO_ORDER)));
    }


    public static void putOrderedData(final Context context, String phone) {
        Prefs.putPref(context, Sample.ORDER, phone);
    }

    public static String getOrdered(final Context context) {
        return Prefs.getPref(context, Sample.ORDER, null);
    }


    public static void Reset(Context context) {
        putToken(context, "");
        putUserId(context, "");
        putName(context, "");
        putAuthLevel(context, "");
        putEmail(context, "");
        putUsername(context, "");
        putPhone(context, "");
        putLatitude(context, 0);
        putLongitude(context, 0);
    }


}