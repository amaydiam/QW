package com.qwash.washer.ui.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.zagum.switchicon.SwitchIconView;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.qwash.washer.MyApplication;
import com.qwash.washer.R;
import com.qwash.washer.Sample;
import com.qwash.washer.api.ApiUtils;
import com.qwash.washer.api.client.availableforjob.AvailableForJobService;
import com.qwash.washer.model.available_for_job.AvailableForJob;
import com.qwash.washer.service.ConnectivityReceiver;
import com.qwash.washer.service.availableforjob.LocationUpdateService;
import com.qwash.washer.service.availableforjob.ServiceHandler;
import com.qwash.washer.ui.activity.TopUpActivity;
import com.qwash.washer.ui.widget.RobotoLightTextView;
import com.qwash.washer.ui.widget.RobotoRegularTextView;
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
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements
        OnChartValueSelectedListener, ConnectivityReceiver.ConnectivityReceiverListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    String TAG = "HomeFragment";


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


    @BindView(R.id.status_connection)
    LinearLayout statusConnection;
    @BindView(R.id.change_saldo)
    RobotoRegularTextView changeSaldo;
    @BindView(R.id.rate)
    RobotoRegularTextView rate;
    @BindView(R.id.desc_available_for_job)
    RobotoLightTextView descAvailableForJob;
    @BindView(R.id.available_for_job)
    SwitchIconView availableForJob;
    @BindView(R.id.chart)
    LineChart mChart;
    private ProgressDialogBuilder dialogProgress;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnProfilFragmentInteractionListener mListener;
    private ServiceHandler service;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @OnClick(R.id.available_for_job)
    public void Click() {
        if (!service.isRunning()) {
            new TedPermission(getActivity())
                    .setPermissionListener(permissionMapsListener)
                    .setDeniedMessage("Anda harus menghidupkan permission ACCESS_FINE_LOCATION pada [Setting] > [Permission]")
                    .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
                    .check();
        } else {
            StopAvailableForJob();
        }
    }


    @OnClick(R.id.change_saldo)
    public void changeSaldo() {
        startActivity(new Intent(getContext(), TopUpActivity.class));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.getInstance().setConnectivityListener(this);

        service = new ServiceHandler(getActivity());

        dialogProgress = new ProgressDialogBuilder(getActivity());
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);

        setCheckedButtonStatus();

        mChart.setOnChartValueSelectedListener(this);

        // no description text
        mChart.getDescription().setEnabled(false);

        // enable touch gestures
        mChart.setTouchEnabled(true);

        mChart.setDragDecelerationFrictionCoef(0.9f);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setDrawGridBackground(false);
        mChart.setHighlightPerDragEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(true);

        // set an alternative background color
        mChart.setBackgroundColor(Color.LTGRAY);

        // add data
        setData(7, 30);

        mChart.animateX(2500);

        // get the legend (only possible after setting data)
        Legend l = mChart.getLegend();

        // modify the legend ...
        l.setForm(Legend.LegendForm.LINE);
        l.setTextSize(11f);
        l.setTextColor(Color.WHITE);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
//        l.setYOffset(11f);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(7);


        return view;
    }

    private void setData(int count, float range) {

        ArrayList<Entry> yVals1 = new ArrayList<Entry>();

        for (int i = 0; i < count; i++) {
            float mult = range / 2f;
            float val = (float) (Math.random() * mult) + 50;
            yVals1.add(new Entry(i, val));
        }

        LineDataSet set1;

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(yVals1, "Pendapatan");

            set1.setAxisDependency(YAxis.AxisDependency.LEFT);
            set1.setColor(ColorTemplate.getHoloBlue());
            set1.setCircleColor(Color.WHITE);
            set1.setLineWidth(2f);
            set1.setCircleRadius(3f);
            set1.setFillAlpha(65);
            set1.setFillColor(ColorTemplate.getHoloBlue());
            set1.setHighLightColor(Color.rgb(244, 117, 117));
            set1.setDrawCircleHole(false);
            //set1.setFillFormatter(new MyFillFormatter(0f));
            //set1.setDrawHorizontalHighlightIndicator(false);
            //set1.setVisible(false);
            //set1.setCircleHoleColor(Color.WHITE);

            // create a data object with the datasets
            LineData data = new LineData(set1);
            data.setValueTextColor(Color.WHITE);
            data.setValueTextSize(9f);

            // set data
            mChart.setData(data);
        }

        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                try {
                    //do your code here
                    CheckConnection(ConnectivityReceiver.isConnected(getActivity()), ConnectivityReceiver.isConnectedFast(getActivity()));
                } catch (Exception e) {
                    // TODO: handle exception
                } finally {
                    //also call the same runnable to call it at regular interval
                    handler.postDelayed(this, 1000 * 30);
                }
            }
        };
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Log.i("Entry selected", e.toString());

        mChart.centerViewToAnimated(e.getX(), e.getY(), mChart.getData().getDataSetByIndex(h.getDataSetIndex())
                .getAxisDependency(), 500);
    }

    @Override
    public void onNothingSelected() {

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
        if (!service.isRunning()) {
            Intent bindIntent = new Intent(getActivity(), LocationUpdateService.class);
            getActivity().startService(bindIntent);
            service.doBindService();
            setCheckedButtonStatus();
            TastyToast.makeText(getActivity(), getResources().getString(R.string.ready_job), TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
        }
    }


    private void stopAvaibleForJob() {
        if (service.isRunning()) {
            Intent bindIntent = new Intent(getActivity(), LocationUpdateService.class);
            getActivity().stopService(bindIntent);
            service.doUnbindService();
            setCheckedButtonStatus();
            TastyToast.makeText(getActivity(), getResources().getString(R.string.not_ready_job), TastyToast.LENGTH_SHORT, TastyToast.WARNING);
        }
    }


    public void setCheckedButtonStatus() {
        if (service.isRunning()) {
            availableForJob.setIconEnabled(true, true);
            descAvailableForJob.setText(getResources().getString(R.string.ready_job));
            statusConnection.setVisibility(View.VISIBLE);
            descAvailableForJob.setTextColor(ContextCompat.getColor(getActivity(), R.color.green_4CAF50));

        } else {
            availableForJob.setIconEnabled(false, true);
            descAvailableForJob.setText(getResources().getString(R.string.not_ready_job));
            statusConnection.setVisibility(View.GONE);
            descAvailableForJob.setTextColor(ContextCompat.getColor(getActivity(), R.color.textColorSecondary));
        }
        CheckConnection(ConnectivityReceiver.isConnected(getActivity()), ConnectivityReceiver.isConnectedFast(getActivity()));
    }


    private void StopAvailableForJob() {
        {
            dialogProgress.show("Stop Available for Job ...", "Please wait...");
            Map<String, String> params = new HashMap<>();
            params.put(Sample.WASHERS_ID, Prefs.getUserId(getActivity()));

            AvailableForJobService mService = ApiUtils.AvailableForJobService(getActivity());
            mService.getWasherOffLink("Bearer " + Prefs.getToken(getActivity()), params).enqueue(new Callback<AvailableForJob>() {
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

    @Override
    public void onNetworkConnectionChanged(boolean isConnected, boolean isSlow) {
        CheckConnection(isConnected, isSlow);

    }

    private void CheckConnection(boolean isConnected, boolean isFastConnection) {
        statusConnection.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        if (isConnected) {
            if (isFastConnection) {
                LinearLayout viewComplete = (LinearLayout) inflater.inflate(R.layout.connection_success, null, false);
                statusConnection.addView(viewComplete);
                RobotoLightTextView message_complete = (RobotoLightTextView) viewComplete.findViewById(R.id.message);
                message_complete.setText("Connected !!");
            } else {
                LinearLayout viewSlow = (LinearLayout) inflater.inflate(R.layout.connection_slow, null, false);
                statusConnection.addView(viewSlow);
                RobotoLightTextView message_slow = (RobotoLightTextView) viewSlow.findViewById(R.id.message);
                message_slow.setText("Your Connection is Slowly !!");
            }
        } else {
            LinearLayout viewError = (LinearLayout) inflater.inflate(R.layout.connection_error, null, false);
            statusConnection.addView(viewError);
            RobotoLightTextView message_error = (RobotoLightTextView) viewError.findViewById(R.id.message);
            message_error.setText("Not Connected. Check Your Internet !!");
        }
    }

    public interface OnProfilFragmentInteractionListener {
        void onProfilFragmentInteraction();
    }

}
