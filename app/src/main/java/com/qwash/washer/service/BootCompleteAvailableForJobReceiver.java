package com.qwash.washer.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.qwash.washer.service.availableforjob.LocationUpdateService;

/**
 * Created by Amay on 3/29/2017.
 */

public class BootCompleteAvailableForJobReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent service = new Intent(context, LocationUpdateService.class);
        context.startService(service);

    }

}