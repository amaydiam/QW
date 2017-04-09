package com.qwash.washer.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.renderscript.Double2;

import com.qwash.washer.Sample;
import com.qwash.washer.ui.activity.ProgressOrderActivity;

import java.util.Locale;


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


    public static void putToken(final Context context, String token) {
        Prefs.putPref(context, Sample.TOKEN, token);
    }

    public static String getToken(final Context context) {
        return Prefs.getPref(context, Sample.TOKEN, "");
    }

    public static void putUserId(final Context context, String userId) {
        Prefs.putPref(context, Sample.USER_ID, userId);
    }

    public static String getUserId(final Context context) {
        return Prefs.getPref(context, Sample.USER_ID, "");
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

    public static String getPassword(final Context context) {
        return Prefs.getPref(context, Sample.PASSWORD, "");
    }

    public static void putPassword(final Context context, String Password) {
        Prefs.putPref(context, Sample.PASSWORD, Password);
    }

    public static String getUsername(final Context context) {
        return Prefs.getPref(context, Sample.USERNAME, "");
    }

    public static void putType(final Context context, Integer type) {
        Prefs.putPref(context, Sample.TYPE, String.valueOf(type));
    }

    public static String getType(final Context context) {
        return Prefs.getPref(context, Sample.TYPE, "");
    }

    public static void putFullName(final Context context, String fullName) {
        Prefs.putPref(context, Sample.FULL_NAME, fullName);
    }

    public static String getFullName(final Context context) {
        return Prefs.getPref(context, Sample.FULL_NAME, "");
    }

    public static void putSaldo(final Context context, int saldo) {
        Prefs.putPref(context, Sample.SALDO, String.valueOf(saldo));
    }

    public static int getSaldo(final Context context) {
        return Integer.parseInt(Prefs.getPref(context, Sample.SALDO, "0"));
    }

    public static String getSaldoRupiah(final Context context) {
        return Utils.Rupiah(Prefs.getPref(context, Sample.SALDO, ""));
    }



    public static void putFirebaseId(final Context context, String firebaseId) {
        Prefs.putPref(context, Sample.FIREBASE_ID, firebaseId);
    }

    public static String getFirebaseId(final Context context) {
        return Prefs.getPref(context, Sample.FIREBASE_ID, "");
    }


    public static void putGeometryLat(final Context context, Double geometryLat) {
        Prefs.putPref(context, Sample.GEOMETRY_LAT, String.valueOf(geometryLat));
    }

    public static Double getGeometryLat(final Context context) {
        return Double.valueOf(Prefs.getPref(context, Sample.GEOMETRY_LAT, "0.0"));
    }

    public static void putGeometryLong(final Context context, Double geometryLong) {
        Prefs.putPref(context, Sample.GEOMETRY_LONG, String.valueOf(geometryLong));
    }

    public static Double getGeometryLong(final Context context) {
        return Double.valueOf(Prefs.getPref(context, Sample.GEOMETRY_LONG, "0.0"));
    }

    public static void putProfileGender(final Context context, String profileGender) {
        Prefs.putPref(context, Sample.PROFILE_GENDER, profileGender);
    }

    public static String getProfileGender(final Context context) {
        return Prefs.getPref(context, Sample.PROFILE_GENDER, "");
    }

    public static void putProfilePhoto(final Context context, String profilePhoto) {
        Prefs.putPref(context, Sample.PROFILE_PHOTO, profilePhoto);
    }

    public static String getProfilePhoto(final Context context) {
        return Sample.URL_AVATAR_FILE + Prefs.getPref(context, Sample.PROFILE_PHOTO, "");
    }

    public static void putProfileProvince(final Context context, String profileProvince) {
        Prefs.putPref(context, Sample.PROFILE_PROVINCE, profileProvince);
    }

    public static String getProfileProvince(final Context context) {
        return Prefs.getPref(context, Sample.PROFILE_PROVINCE, "");
    }

    public static void putProfileCity(final Context context, String profileCity) {
        Prefs.putPref(context, Sample.PROFILE_CITY, profileCity);
    }

    public static String getProfileCity(final Context context) {
        return Prefs.getPref(context, Sample.PROFILE_CITY, "");
    }

    public static void putProfileNik(final Context context, String profileNik) {
        Prefs.putPref(context, Sample.PROFILE_NIK, profileNik);
    }

    public static String getProfileNik(final Context context) {
        return Prefs.getPref(context, Sample.PROFILE_NIK, "");
    }

    public static void putOnline(final Context context, Integer Online) {
        Prefs.putPref(context, Sample.ONLINE, String.valueOf(Online));
    }

    public static String getOnline(final Context context) {
        return Prefs.getPref(context, Sample.ONLINE, "");
    }


    public static void putStatus(final Context context, Integer status) {
        Prefs.putPref(context, Sample.STATUS, String.valueOf(status));
    }

    public static int getStatus(final Context context) {
        return Integer.parseInt(Prefs.getPref(context, Sample.STATUS, ""));
    }


    public static void putCreatedAt(final Context context, String status) {
        Prefs.putPref(context, Sample.CREATED_AT, status);
    }

    public static String getCreatedAt(final Context context) {
        return Prefs.getPref(context, Sample.CREATED_AT, "");
    }

    public static void putUpdatedAt(final Context context, String updatedAt) {
        Prefs.putPref(context, Sample.UPDATED_AT, updatedAt);
    }

    public static String getUpdatedAt(final Context context) {
        return Prefs.getPref(context, Sample.UPDATED_AT, "");
    }

    public static void putRating(final Context context, String Rating) {
        Prefs.putPref(context, Sample.RATING, Rating);
    }

    public static String getRating(final Context context) {
        return String.format("%.2f", Double.parseDouble(Prefs.getPref(context, Sample.RATING, "5")));
    }


    public static void putAvatarFile(final Context context, String avatar) {
        Prefs.putPref(context, Sample.AVATAR, avatar);
    }


    public static String getAvatarFile(final Context context) {
        return Sample.URL_AVATAR_FILE + Prefs.getPref(context, Sample.AVATAR, null);
    }


    public static void putKtpFile(final Context context, String enabled) {
        Prefs.putPref(context, Sample.KTP, enabled);
    }


    public static String getKTPFile(final Context context) {
        return Sample.URL_KTP_FILE + Prefs.getPref(context, Sample.KTP, null);
    }


    public static void putSkckFile(final Context context, String skck) {
        Prefs.putPref(context, Sample.SKCK, skck);
    }


    public static String getSkckFile(final Context context) {
        return Sample.URL_SKCK_FILE + Prefs.getPref(context, Sample.SKCK, null);
    }


    public static void putAvatar(final Context context, boolean enabled) {
        Prefs.putPref(context, Sample.AVATAR_STATUS, (enabled ? "true" : "false"));
    }


    public static boolean getAvatar(final Context context) {
        String e = Prefs.getPref(context, Sample.AVATAR_STATUS, "false");
        return e.equals("true");
    }

    public static void putKtp(final Context context, boolean enabled) {
        Prefs.putPref(context, Sample.KTP_STATUS, (enabled ? "true" : "false"));
    }


    public static boolean getKtp(final Context context) {
        String e = Prefs.getPref(context, Sample.KTP_STATUS, "false");
        return e.equals("true");
    }

    public static void putSkck(final Context context, boolean enabled) {
        Prefs.putPref(context, Sample.SKCK_STATUS, (enabled ? "true" : "false"));
    }


    public static boolean getSkck(final Context context) {
        String e = Prefs.getPref(context, Sample.SKCK_STATUS, "false");
        return e.equals("true");
    }


    //end data user

    public static void putAvailableForJob(final Context context, boolean enabled) {
        Prefs.putPref(context, Sample.AVAILABLE_FOR_JOB, (enabled ? "true" : "false"));
    }


    public static boolean getAvailableForJob(final Context context) {
        String e = Prefs.getPref(context, Sample.AVAILABLE_FOR_JOB, "false");
        return e.equals("true");
    }


    public static void putActivityIndex(final Context context, int activity_index) {
        Prefs.putPref(context, Sample.ACTIVITY_INDEX, String.valueOf(activity_index));
    }


    public static int getActivityIndex(final Context context) {
        return Integer.parseInt(Prefs.getPref(context, Sample.ACTIVITY_INDEX, String.valueOf(Sample.NO_INDEX)));
    }


    public static void putProgresWorking(final Context context, int latitude) {
        Prefs.putPref(context, Sample.PROGRESS_WORKING, String.valueOf(latitude));
    }

    public static int getProgresWorking(Context context) {
        return Integer.parseInt(Prefs.getPref(context, Sample.PROGRESS_WORKING, String.valueOf(Sample.CODE_NO_ORDER)));
    }


    public static void putOrderedData(final Context context, String order) {
        Prefs.putPref(context, Sample.ORDER, order);
    }

    public static String getOrderedData(final Context context) {
        return Prefs.getPref(context, Sample.ORDER, null);
    }

    public static void putLastOrdersId(final Context context, String ordersId) {
        Prefs.putPref(context, Sample.ORDERS_ID, ordersId);
    }

    public static String getLastOrdersId(final Context context) {
        return Prefs.getPref(context, Sample.ORDERS_ID, null);
    }


    public static String getLanguage(final Context context) {
        return Prefs.getPref(context, Sample.LANGUAGE, Locale.getDefault().getLanguage());
    }

    public static void putLanguage(Context context, String language) {
        Prefs.putPref(context, Sample.LANGUAGE, language);
    }


    public static boolean isLogedIn(Context context) {
        return getUserId(context).length() > 0
                && getToken(context).length() > 0
                && getStatus(context) == 1;
    }


    public static void Reset(Context context) {
        putToken(context, "");
        putUserId(context, "");
        putEmail(context, "");
        putPassword(context, "");
        putUsername(context, "");
        putType(context, 0);
        putFullName(context, "");
        putSaldo(context, 0);
        putFirebaseId(context, "");
        putGeometryLat(context, 0.0);
        putGeometryLong(context, 0.0);
        putProfileGender(context, "");
        putProfilePhoto(context, "");
        putProfileProvince(context, "");
        putProfileCity(context, "");
        putProfileNik(context, "");
        putOnline(context, 0);
        putStatus(context, 0);
        putCreatedAt(context, "");
        putUpdatedAt(context, "");
        putRating(context, "5");
        putOrderedData(context, null);
        putLastOrdersId(context, null);
        putAvatarFile(context, null);
        putAvatar(context, false);
        putKtpFile(context, null);
        putKtp(context, false);
        putSkckFile(context, null);
        putSkck(context, false);
        putActivityIndex(context, Sample.NO_INDEX);

    }


}