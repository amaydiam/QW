package com.qwash.washer.ui.fragment;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.qwash.washer.R;
import com.qwash.washer.Sample;
import com.qwash.washer.api.ApiUtils;
import com.qwash.washer.api.client.availableforjob.AvailableForJobService;
import com.qwash.washer.model.available_for_job.AvailableForJob;
import com.qwash.washer.service.availableforjob.LocationUpdateService;
import com.qwash.washer.ui.activity.HomeActivity;
import com.qwash.washer.ui.activity.TopUpActivity;
import com.qwash.washer.ui.widget.RobotoLightTextView;
import com.qwash.washer.utils.Prefs;
import com.qwash.washer.utils.ProgressDialogBuilder;
import com.sdsmdg.tastytoast.TastyToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfilFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ACTION_FROM_NOTIFICATION = "isFromNotification";
    private static final int notifID = 1001;
    public boolean mIsServiceStarted = false;
    String TAG = "ProfilFragment";
    @BindView(R.id.available_for_job)
    Switch availableForJob;
    @BindView(R.id.available_for_vaccum)
    Switch availableForVaccum;
    @BindView(R.id.change_saldo)
    RobotoLightTextView changeSaldo;
    PermissionListener permissionMapsListener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            startAvaibleForJob();
        }

        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {
            String message = String.format(Locale.getDefault(), getString(R.string.message_denied), "ACCESS_FINE_LOCATION");
            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        }

    };
    private ProgressDialogBuilder dialogProgress;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnProfilFragmentInteractionListener mListener;
    public ProfilFragment() {
        // Required empty public constructor
    }

    public static ProfilFragment newInstance(String param1, String param2) {
        ProfilFragment fragment = new ProfilFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private static void OnGoingLocationNotification(Context mcontext) {

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(mcontext)
                        .setSound(alarmSound)
                        .setSmallIcon(R.drawable.ic_cast_off_light)
                        .setContentTitle("Available for Job")
                        .setOngoing(true).setContentText("Active");
        mBuilder.setAutoCancel(false);

        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(mcontext, HomeActivity.class);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        resultIntent.setAction(ACTION_FROM_NOTIFICATION);

        PendingIntent resultPendingIntent = PendingIntent.getActivity(mcontext, (int) System.currentTimeMillis(), resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);

        NotificationManager mNotificationManager =
                (NotificationManager) mcontext.getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.cancel(notifID);

        Notification mNotification = mBuilder.build();
        mNotification.defaults |= Notification.DEFAULT_VIBRATE;
        mNotification.flags |= Notification.FLAG_ONGOING_EVENT;

        mNotificationManager.notify(notifID, mNotification);

    }

    @OnClick(R.id.layout_available_for_job)
    public void Click() {
        if (!mIsServiceStarted) {
            new TedPermission(getActivity())
                    .setPermissionListener(permissionMapsListener)
                    .setDeniedMessage("Anda harus menghidupkan permission ACCESS_FINE_LOCATION pada [Setting] > [Permission]")
                    .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
                    .check();
        } else {
            SendToServer();
        }
    }

    @OnCheckedChanged(R.id.available_for_vaccum)
    void onChecked(boolean b) {
        if (b)
            Prefs.putAvailableForVaccum(getActivity(), 1);
        else
            Prefs.putAvailableForVaccum(getActivity(), 0);
    }

    @OnClick(R.id.change_saldo)
    public void changeSaldo() {
        startActivity(new Intent(getContext(), TopUpActivity.class));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dialogProgress = new ProgressDialogBuilder(getActivity());
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profil, container, false);
        ButterKnife.bind(this, view);
        if (Prefs.getAvailableForJob(getActivity())) {
            mIsServiceStarted = true;
            setCheckedButtonStatus();
        }
        return view;
    }

    public void onButtonPressed() {
        if (mListener != null) {
            mListener.onProfilFragmentInteraction();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnProfilFragmentInteractionListener) {
            mListener = (OnProfilFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnProfilFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void startAvaibleForJob() {
        if (!mIsServiceStarted) {
            mIsServiceStarted = true;
            setCheckedButtonStatus();
            OnGoingLocationNotification(getActivity());
            Intent bindIntent = new Intent(getActivity(), LocationUpdateService.class);
            getActivity().startService(bindIntent);
            TastyToast.makeText(getActivity(), "Now, you are available for job", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);

        }
    }


    private void stopAvaibleForJob() {
        if (mIsServiceStarted) {
            mIsServiceStarted = false;
            setCheckedButtonStatus();
            cancelNotification(getActivity(), notifID);
            Intent bindIntent = new Intent(getActivity(), LocationUpdateService.class);
            getActivity().stopService(bindIntent);
            TastyToast.makeText(getActivity(), "Now, you are not available for job", TastyToast.LENGTH_SHORT, TastyToast.WARNING);

        }
    }


    public void setCheckedButtonStatus() {
        if (mIsServiceStarted) {
            availableForJob.setChecked(true);
            availableForVaccum.setEnabled(true);
        } else {
            availableForJob.setChecked(false);
            availableForVaccum.setChecked(false);
            availableForVaccum.setEnabled(false);
            Prefs.putAvailableForVaccum(getActivity(), 0);
        }
    }


    private void SendToServer() {
        {
            dialogProgress.show("Stop Available for Job ...", "Please wait...");
            Map<String, String> params = new HashMap<>();
            params.put(Sample.USER_ID, Prefs.getUserId(getActivity()));

            AvailableForJobService mService = ApiUtils.AvailableForJobService(getActivity());
            mService.getWasherOffLink(params).enqueue(new Callback<AvailableForJob>() {
                @Override
                public void onResponse(Call<AvailableForJob> call, Response<AvailableForJob> response) {
                    dialogProgress.hide();
                    if (response.isSuccessful()) {
                        if (response.body().getStatus()) {
                            stopAvaibleForJob();
                        }
                    } else {
                        int statusCode = response.code();
                        try {
                            JSONObject jsonObject = new JSONObject(response.errorBody().string());
                            String message = jsonObject.getString(Sample.MESSAGE);
                            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                        } catch (JSONException | IOException e) {
                            e.printStackTrace();
                            TastyToast.makeText(getActivity(), "Mistakes", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                        }
                    }
                }

                @Override
                public void onFailure(Call<AvailableForJob> call, Throwable t) {
                    String message = t.getMessage();
                    dialogProgress.hide();
                    TastyToast.makeText(getActivity(), message, TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                }
            });
        }
    }

    private void cancelNotification(Context mContext, int mnotinotifId) {
        NotificationManager manager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancel(mnotinotifId);
    }

    public interface OnProfilFragmentInteractionListener {
        void onProfilFragmentInteraction();
    }

}
