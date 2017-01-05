package com.qwash.washer.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.qwash.washer.R;
import com.qwash.washer.Sample;
import com.qwash.washer.model.Vehicle;
import com.qwash.washer.ui.activity.SelectVehicleActivity;
import com.qwash.washer.ui.activity.ServiceDetailUserActivity;
import com.qwash.washer.ui.widget.RobotoBoldTextView;
import com.qwash.washer.ui.widget.RobotoRegularButton;
import com.bumptech.glide.Glide;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.EntypoModule;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.joanzapata.iconify.fonts.MaterialCommunityModule;
import com.joanzapata.iconify.fonts.MaterialModule;
import com.joanzapata.iconify.fonts.SimpleLineIconsModule;
import com.joanzapata.iconify.widget.IconTextView;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PrepareOrderFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    @BindView(R.id.btn_cancel)
    RobotoRegularButton btnCancel;

    @BindView(R.id.indicator_1)
    IconTextView indicator1;
    @BindView(R.id.indicator_2)
    IconTextView indicator2;

    @BindView(R.id.btn_next)
    RobotoRegularButton btnNext;

    @OnClick(R.id.btn_cancel)
    void ActionCancel() {
        mListener.onCancelOrder();
        Toast.makeText(getActivity(), "Cancelled", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.btn_next)
    void ActionNext() {
        getActivity().startActivityForResult(new Intent(getActivity(), ServiceDetailUserActivity.class), 1);

    }


    @BindView(R.id.vehicle_image)
    ImageView vehicleImage;
    @BindView(R.id.vehicle_description)
    RobotoBoldTextView vehicleDescription;
    @BindView(R.id.btn_pick_vehicle)
    IconTextView btnPickVehicle;


    @OnClick(R.id.layout_select_vehicle)
    void SelectVehicle() {
        Intent intent = new Intent(getActivity(), SelectVehicleActivity.class);
        intent.putExtra(Sample.VEHICLE_OBJECT, vehicle);
        getActivity().startActivityForResult(intent, 1);
    }

    public Vehicle vehicle=null;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnOrderedListener mListener;

    public PrepareOrderFragment() {
        // Required empty public constructor
    }


    public static PrepareOrderFragment newInstance(String param1, String param2) {
        PrepareOrderFragment fragment = new PrepareOrderFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Iconify
                .with(new FontAwesomeModule())
                .with(new EntypoModule())
                .with(new MaterialModule())
                .with(new MaterialCommunityModule())
                .with(new SimpleLineIconsModule());
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_prepare_order, container, false);
        ButterKnife.bind(this, view);

        btnCancel.setText(getActivity().getResources().getString(R.string.button_cancel));
        indicator1.setTextColor(ContextCompat.getColor(getActivity(), R.color.blue_2196F3));
        indicator2.setTextColor(ContextCompat.getColor(getActivity(), R.color.blue_BBDEFB));

        return view;
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onCancelOrder();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnOrderedListener) {
            mListener = (OnOrderedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnOrderedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void setSelectedVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
        Glide
                .with(this)
                .load("")
                .centerCrop()
                .placeholder(vehicle.type == 1 ? R.drawable.mobil : R.drawable.motor)
                .crossFade()
                .into(vehicleImage);

        vehicleDescription.setText(vehicle.brand + "\n" + vehicle.model + " " + vehicle.transmission + " " + vehicle.year);
    }


    public interface OnOrderedListener {
        void onCancelOrder();
    }

}
