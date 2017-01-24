package com.qwash.washer.service.availableforjob;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;
import com.qwash.washer.Sample;
import com.qwash.washer.api.ApiUtils;
import com.qwash.washer.api.client.availableforjob.AvailableForJobService;
import com.qwash.washer.api.model.order.OrderStartWash;
import com.qwash.washer.model.availableforjob.AvailableForJob;
import com.qwash.washer.utils.Prefs;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Grishma on 16/5/16.
 */
public class LocationUpdateService extends Service implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    protected static final String TAG = "LocationUpdateService";
    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 3 * 10000;
    public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
            UPDATE_INTERVAL_IN_MILLISECONDS / 2;
    public static Boolean mRequestingLocationUpdates;
    protected String mLastUpdateTime;
    protected GoogleApiClient mGoogleApiClient;
    protected LocationRequest mLocationRequest;

    protected Location mCurrentLocation;
    public static boolean isEnded = false;
    private int count = 1;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Prefs.putAvailableForJob(this, true);
        isEnded = false;
        mRequestingLocationUpdates = false;
        mLastUpdateTime = "";
        buildGoogleApiClient();
        if (mGoogleApiClient.isConnected() && mRequestingLocationUpdates) {
            startLocationUpdates();
        }
        return Service.START_REDELIVER_INTENT;
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
        mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
        Prefs.putLatitude(this, mCurrentLocation.getLatitude());
        Prefs.putLongitude(this, mCurrentLocation.getLongitude());
        SendToServer();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + connectionResult.getErrorCode());
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
            Log.d(TAG, "Update To Server");
            Map<String, String> params = new HashMap<>();
            params.put(Sample.USER_ID, Prefs.getUserId(this));
            params.put(Sample.LAT, String.valueOf(mCurrentLocation.getLatitude()));
            params.put(Sample.LONG, String.valueOf(mCurrentLocation.getLongitude()));
            params.put(Sample.VACCUM, String.valueOf(Prefs.getAvailableForVaccum(this)));


            for (Map.Entry entry : params.entrySet()) {
                System.out.println(entry.getKey() + ", " + entry.getValue());
            }

            AvailableForJobService mService = ApiUtils.AvailableForJobService(this);
            (count == 1 ? mService.getWasherOnLink(params) : mService.getWasherOnUpdateLink(params)).enqueue(new Callback<AvailableForJob>() {
                @Override
                public void onResponse(Call<AvailableForJob> call, Response<AvailableForJob> response) {
                    Log.w("response", new Gson().toJson(response));
                    if (response.isSuccessful()) {
                        if (response.body().getStatus()) {
                            count = count + 1;
                        }
                        Log.d(TAG, "posts loaded from API");
                    } else {
                        int statusCode = response.code();
                        Log.d(TAG, "error loading from API, status: " + statusCode);
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
                    Log.d(TAG, message);
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
            Log.i(TAG, " startLocationUpdates===");
            isEnded = true;
        }
    }

    /**
     * Removes location updates from the FusedLocationApi.
     */
    protected void stopLocationUpdates() {
        if (mRequestingLocationUpdates) {
            mRequestingLocationUpdates = false;
            Log.d(TAG, "stopLocationUpdates();==");
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopLocationUpdates();
        Prefs.putAvailableForJob(this, false);
    }


}
