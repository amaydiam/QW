package com.qwash.washer.ui.fragment;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.qwash.washer.R;
import com.qwash.washer.service.availableforjob.AlarmAvailableForJobReceiver;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfilFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    @BindView(R.id.available_for_job)
    Switch availableForJob;
    @BindView(R.id.available_for_vaccum)
    Switch availableForVaccum;
    private PendingIntent pendingIntent;
    private Intent alarmIntent;

    @OnClick(R.id.layout_available_for_job)
    public void Click() {
        if (!isRunning()) {
            new TedPermission(getActivity())
                    .setPermissionListener(permissionMapsListener)
                    .setDeniedMessage("Anda harus menghidupkan permission ACCESS_FINE_LOCATION pada [Setting] > [Permission]")
                    .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
                    .check();
        } else {
            cancel();
            availableForJob.setChecked(false);
        }
    }

    PermissionListener permissionMapsListener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            start();
            availableForJob.setChecked(true);
        }

        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {
            String message = String.format(Locale.getDefault(), getString(R.string.message_denied), "ACCESS_FINE_LOCATION");
            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            availableForJob.setChecked(false);
        }

    };

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         alarmIntent = new Intent(getActivity(), AlarmAvailableForJobReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

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
       /* if (availableForJobServirce.isRunning()) {
            availableForJob.setChecked(true);
        }*/
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

    public interface OnProfilFragmentInteractionListener {
        void onProfilFragmentInteraction();
    }


    public void start() {
        AlarmManager manager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        int interval = 8000;
        manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);
        Toast.makeText(getActivity(), "Alarm Set", Toast.LENGTH_SHORT).show();
    }

    public void cancel() {
        AlarmManager manager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);
        Toast.makeText(getActivity(), "Alarm Canceled", Toast.LENGTH_SHORT).show();
    }

    public boolean isRunning() {
        return(PendingIntent.getBroadcast(getActivity(), 0, alarmIntent, PendingIntent.FLAG_NO_CREATE) != null);
    }
}
