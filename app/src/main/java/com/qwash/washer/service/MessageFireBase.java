package com.qwash.washer.service;

import java.util.Map;

/**
 * Created by Amay on 1/16/2017.
 */
public class MessageFireBase {

    private final Map<String, String> data;

    public MessageFireBase(Map<String, String> data) {
        this.data = data;
    }

    public Map<String, String> getData() {
        return data;
    }
}
