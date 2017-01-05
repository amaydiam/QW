package com.qwash.washer.utils;

public class TextUtils {

    public static boolean isNullOrEmpty(String str) {
        return (str == null || str.equals("null") || str.equals(""));
    }

}
