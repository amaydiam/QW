package com.ad.sample.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ad.sample.R;
import com.ad.sample.ui.widget.RobotoRegularTextView;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.EntypoModule;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.joanzapata.iconify.fonts.MaterialCommunityIcons;
import com.joanzapata.iconify.fonts.MaterialCommunityModule;
import com.joanzapata.iconify.fonts.MaterialIcons;
import com.joanzapata.iconify.fonts.MaterialModule;
import com.joanzapata.iconify.fonts.SimpleLineIconsModule;

import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddVehicleActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindDimen(R.dimen.activity_vertical_margin)
    int activity_vertical_margin;


    @BindView(R.id.vehicle_mobil)
    CardView vehicleMobil;
    @BindView(R.id.vehicle_motor)
    CardView vehicleMotor;
    @BindView(R.id.select_vehicle)
    LinearLayout selectVehicle;
    @BindView(R.id.indicator_select_brand)
    LinearLayout indicatorSelectBrand;
    @BindView(R.id.indicator_select_model)
    LinearLayout indicatorSelectModel;
    @BindView(R.id.indicator_select_transmission)
    LinearLayout indicatorSelectTransmission;
    @BindView(R.id.indicator_select_year)
    LinearLayout indicatorSelectYear;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    @OnClick(R.id.vehicle_mobil)
    void VehicleMobil() {
        SelectVehicle(1);
    }

    @OnClick(R.id.vehicle_motor)
    void VehicleCar() {
        SelectVehicle(2);
    }

    @BindView(R.id.img_car)
    ImageView imgCar;
    @BindView(R.id.tx_car)
    RobotoRegularTextView txCar;

    @BindView(R.id.img_motorcycle)
    ImageView imgMotorcycle;
    @BindView(R.id.tx_motorcycle)
    RobotoRegularTextView txMotorcycle;

    @BindView(R.id.select_brand)
    RelativeLayout selectBrand;
    @BindView(R.id.select_model)
    RelativeLayout selectModel;
    @BindView(R.id.select_transmission)
    RelativeLayout selectTransmission;
    @BindView(R.id.select_year)
    RelativeLayout selectYear;


    @BindView(R.id.img_brand)
    ImageView imgBrand;
    @BindView(R.id.img_model)
    ImageView imgModel;
    @BindView(R.id.img_transmission)
    ImageView imgTransmission;
    @BindView(R.id.img_year)
    ImageView imgYear;

    @OnClick(R.id.select_brand)
    void select_brand() {
        startActivity(new Intent(this, SelectBrandActivity.class));
        //Toast.makeText(this, "Select Brand", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.select_model)
    void select_model() {
        startActivity(new Intent(this, SelectModelActivity.class));
        //Toast.makeText(this, "Select Model", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.select_transmission)
    void select_transmission() {
        startActivity(new Intent(this, SelectTransmissionActivity.class));
        //Toast.makeText(this, "Select Transmission", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.select_year)
    void select_year() {
        startActivity(new Intent(this, SelectYearActivity.class));
        //Toast.makeText(this, "Select Year", Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Iconify
                .with(new FontAwesomeModule())
                .with(new EntypoModule())
                .with(new MaterialModule())
                .with(new MaterialCommunityModule())
                .with(new SimpleLineIconsModule());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(
                new IconDrawable(this, MaterialIcons.md_arrow_back)
                        .colorRes(R.color.black_424242)
                        .actionBarSize());
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getSupportActionBar().setTitle("");
        toolbarTitle.setText(getResources().getString(R.string.title_activity_add_vehicle_type));

        imgBrand.setImageDrawable(new IconDrawable(this, MaterialCommunityIcons.mdi_speedometer).colorRes(R.color.blue_2196F3).actionBarSize());
        imgModel.setImageDrawable(new IconDrawable(this, MaterialCommunityIcons.mdi_steering).colorRes(R.color.blue_2196F3).actionBarSize());
        imgYear.setImageDrawable(new IconDrawable(this, MaterialCommunityIcons.mdi_calendar).colorRes(R.color.blue_2196F3).actionBarSize());

        Bitmap transmission_blue = BitmapFactory.decodeResource(getResources(), R.drawable.transmission_blue);
        imgTransmission.setImageBitmap(Bitmap.createScaledBitmap(transmission_blue, convertToPx(IconDrawable.ANDROID_ACTIONBAR_ICON_SIZE_DP), convertToPx(IconDrawable.ANDROID_ACTIONBAR_ICON_SIZE_DP), false));

        SelectVehicle(1);
        setWidthSelectView();

    }

    private void setWidthSelectView() {
        vehicleMotor.measure(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        int half_width = vehicleMotor.getMeasuredWidth() / 2;

        ViewGroup.LayoutParams paramsindicatorSelectBrand = indicatorSelectBrand.getLayoutParams();
        paramsindicatorSelectBrand.width = half_width + activity_vertical_margin;
        indicatorSelectBrand.setLayoutParams(paramsindicatorSelectBrand);

        ViewGroup.LayoutParams paramsindicatorSelectModel = indicatorSelectModel.getLayoutParams();
        paramsindicatorSelectModel.width = half_width + activity_vertical_margin;
        indicatorSelectModel.setLayoutParams(paramsindicatorSelectModel);

        ViewGroup.LayoutParams paramsindicatorSelectTransmission = indicatorSelectTransmission.getLayoutParams();
        paramsindicatorSelectTransmission.width = half_width + activity_vertical_margin;
        indicatorSelectTransmission.setLayoutParams(paramsindicatorSelectTransmission);

        ViewGroup.LayoutParams paramsindicatorSelectYear = indicatorSelectYear.getLayoutParams();
        paramsindicatorSelectYear.width = half_width + activity_vertical_margin;
        indicatorSelectYear.setLayoutParams(paramsindicatorSelectYear);
    }


    public int convertToPx(int dp) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (dp * scale + 0.5f);
    }


    private void SelectVehicle(int i) {
        if (i == 1) {
            vehicleMobil.setCardBackgroundColor(ContextCompat.getColor(this, R.color.blue_2196F3));
            imgCar.setImageResource(R.drawable.car_white);
            txCar.setTextColor(ContextCompat.getColor(this, R.color.white));

            vehicleMotor.setCardBackgroundColor(ContextCompat.getColor(this, R.color.white));
            imgMotorcycle.setImageResource(R.drawable.motor_blue);
            txMotorcycle.setTextColor(ContextCompat.getColor(this, R.color.black_424242));

        } else if (i == 2) {
            vehicleMobil.setCardBackgroundColor(ContextCompat.getColor(this, R.color.white));
            imgCar.setImageResource(R.drawable.car_blue);
            txCar.setTextColor(ContextCompat.getColor(this, R.color.black_424242));

            vehicleMotor.setCardBackgroundColor(ContextCompat.getColor(this, R.color.blue_2196F3));
            imgMotorcycle.setImageResource(R.drawable.motor_white);
            txMotorcycle.setTextColor(ContextCompat.getColor(this, R.color.white));

        }
    }

}
