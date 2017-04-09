package com.qwash.washer.service.availableforjob;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.qwash.washer.R;
import com.qwash.washer.Sample;
import com.qwash.washer.api.ApiUtils;
import com.qwash.washer.api.client.availableforjob.AvailableForJobService;
import com.qwash.washer.model.available_for_job.AvailableForJob;
import com.qwash.washer.ui.activity.HomeActivity;
import com.qwash.washer.utils.Prefs;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.qwash.washer.Sample.ACTION_FROM_NOTIFICATION;
import static com.qwash.washer.Sample.notifID;

/**
 * Created by Grishma on 16/5/16.
 */
public class LocationUpdateService extends Service implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 3 * 10000;
    public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
            UPDATE_INTERVAL_IN_MILLISECONDS / 2;
    public static Boolean mRequestingLocationUpdates;
    private static LocationUpdateService service;
    protected GoogleApiClient mGoogleApiClient;
    protected LocationRequest mLocationRequest;
    protected Location mCurrentLocation;
    private int count = 1;
    private NotificationManager nm;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LocationUpdateService.service = this;
        // Prefs.putAvailableForJob(this, true);
        mRequestingLocationUpdates = false;
        buildGoogleApiClient();
        if (mGoogleApiClient.isConnected() && mRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }


    @Override
    public void onConnected(Bundle bundle) {
        startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
        Prefs.putGeometryLat(this, mCurrentLocation.getLatitude());
        Prefs.putGeometryLong(this, mCurrentLocation.getLongitude());
        SendToServer();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        createLocationRequest();
    }

    /**
     * Updates the latitude, the longitude, and the last location time in the UI.
     */

    private void SendToServer() {
        {
            Map<String, String> params = new HashMap<>();
            params.put(Sample.WASHERS_ID, Prefs.getUserId(this));
            params.put(Sample.LAT, String.valueOf(mCurrentLocation.getLatitude()));
            params.put(Sample.LONG, String.valueOf(mCurrentLocation.getLongitude()));
            params.put(Sample.FIREBASE_ID, Prefs.getFirebaseId(this));
            Set keys = params.keySet();
            for (Object key1 : keys) {
                String key = (String) key1;
                String value = params.get(key);
                Log.v(key + "", value);
            }
            Log.v("Auth", "Bearer " + Prefs.getToken(this));


            AvailableForJobService mService = ApiUtils.AvailableForJobService(this);
            //  (count == 1 ? mService.getWasherOnLink(params) : mService.getWasherOnUpdateLink(params)).enqueue(new Callback<AvailableForJob>() {
            mService.getWasherOnLink("Bearer " + Prefs.getToken(this), params).enqueue(new Callback<AvailableForJob>() {
                @Override
                public void onResponse(Call<AvailableForJob> call, Response<AvailableForJob> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus()) {
                            count = count + 1;
                        }
                    } else {
                        int statusCode = response.code();
                        try {
                            JSONObject jsonObject = new JSONObject(response.errorBody().string());
                            String message = jsonObject.getString(Sample.MESSAGE);
                        } catch (JSONException | IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<AvailableForJob> call, Throwable t) {
                    String message = t.getMessage();
                }
            });
        }
    }


    protected void createLocationRequest() {
        mGoogleApiClient.connect();
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    /**
     * Requests location updates from the FusedLocationApi.
     */
    protected void startLocationUpdates() {
        if (!mRequestingLocationUpdates) {
            mRequestingLocationUpdates = true;

            // The final argument to {@code requestLocationUpdates()} is a LocationListener
            // (http://developer.android.com/reference/com/google/android/gms/location/LocationListener.html).
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            LocationServices.FusedLocationApi.requestLocationUpdates(
                    mGoogleApiClient, mLocationRequest, this);
            OnGoingLocationNotification();
        }
    }


    private void OnGoingLocationNotification() {

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSound(alarmSound)
                        .setSmallIcon(R.drawable.ic_cast_off_light)
                        .setContentTitle("Available for Job")
                        .setOngoing(true).setContentText("Active");
        mBuilder.setAutoCancel(false);

        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(this, HomeActivity.class);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        resultIntent.setAction(ACTION_FROM_NOTIFICATION);

        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);

        nm =
                (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        nm.cancel(notifID);

        Notification mNotification = mBuilder.build();
        mNotification.defaults |= Notification.DEFAULT_VIBRATE;
        mNotification.flags |= Notification.FLAG_ONGOING_EVENT;

        nm.notify(notifID, mNotification);

    }



    /**
     * Removes location updates from the FusedLocationApi.
     */
    protected void stopLocationUpdates() {
        if (mRequestingLocationUpdates) {
            mRequestingLocationUpdates = false;
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        nm.cancel(notifID);
        stopLocationUpdates();
        // Prefs.putAvailableForJob(this, false);
    }


}
