package com.ad.sample.utils;

public class TextUtils {

    public static boolean isNullOrEmpty(String str) {
        return (str == null || str.equals("null") || str.equals(""));
    }

}
