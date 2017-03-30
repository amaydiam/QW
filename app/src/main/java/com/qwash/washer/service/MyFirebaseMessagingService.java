package com.qwash.washer.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.qwash.washer.R;
import com.qwash.washer.Sample;
import com.qwash.washer.service.availableforjob.ServiceHandler;
import com.qwash.washer.ui.activity.HomeActivity;
import com.qwash.washer.ui.activity.ProgressOrderActivity;
import com.qwash.washer.utils.Prefs;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by binderbyte on 11/01/17.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onMessageReceived(final RemoteMessage remoteMessage) {
        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ

        if (remoteMessage.getData().size() > 0 && Prefs.isLogedIn(this)) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    ServiceHandler service = new ServiceHandler(getApplicationContext());
                    Log.v("masuk", remoteMessage.getData().toString());

                    try {
                        JSONObject json = new JSONObject(remoteMessage.getData());
                        int action = json.getInt(Sample.ACTION);

                        if (service.isRunning()) {
                            if (action == Sample.ACTION_ORDER && Prefs.isLogedIn(getApplicationContext()) && Prefs.getProgresWorking(getApplicationContext()) == Sample.CODE_NO_ORDER) {
                                String order = json.getString(Sample.ORDER);
                                StartActivityAndSendNotification(order);
                                Prefs.putProgresWorking(getApplicationContext(), Sample.CODE_GET_ORDER);
                                Prefs.putOrderedData(getApplicationContext(), order);
                            } else if (action == Sample.ACTION_CANCEL_ORDER && service.isRunning() &&
                                    (
                                            Prefs.getProgresWorking(getApplicationContext()) == Sample.CODE_GET_ORDER ||
                                                    Prefs.getProgresWorking(getApplicationContext()) == Sample.CODE_ACCEPT_ORDER

                                    )) {

                                EventBus.getDefault().post(new MessageFireBase(remoteMessage.getData()));

                                Prefs.putProgresWorking(getApplicationContext(), Sample.CODE_NO_ORDER);
                                Prefs.putOrderedData(getApplicationContext(), null);

                                NotificationManager nMgr = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                                nMgr.cancelAll();


                            } else if (action == Sample.ACTION_OPEN_FEED_ORDER && service.isRunning()) {
                                Bundle args = new Bundle();
                                args.putInt(Sample.ACTION, Sample.ACTION_OPEN_FEED_ORDER);
                                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                                        | Intent.FLAG_ACTIVITY_SINGLE_TOP
                                        | Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtras(args);
                                getApplicationContext().startActivity(intent);
                                Prefs.putProgresWorking(getApplicationContext(), Sample.CODE_NO_ORDER);
                                EventBus.getDefault().post(new MessageFireBase(remoteMessage.getData()));
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
        }
    }

    private void StartActivityAndSendNotification(String order) {

        Bundle args = new Bundle();
        args.putString(Sample.ORDER, order);
        Intent intent = new Intent(this, ProgressOrderActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP
                | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtras(args);
        startActivity(intent);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT
                | PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.fishtank_bubbles);

        NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(getResources().getString(R.string.app_name))
                .setContentText("NEW ORDER. GET NOW !!!")
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        Notification noti = notificationBuilder.build();
        noti.flags = Notification.FLAG_INSISTENT;
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(Sample.ID_NOTIF_ORDER, noti);
    }

}
