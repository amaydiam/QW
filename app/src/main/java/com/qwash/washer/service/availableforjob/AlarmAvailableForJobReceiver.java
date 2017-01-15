package com.qwash.washer.service.availableforjob;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

import com.qwash.washer.utils.Prefs;

public class AlarmAvailableForJobReceiver extends WakefulBroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context, LocationAvailableForJobService.class));
    }
}