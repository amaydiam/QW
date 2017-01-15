package com.qwash.washer.ui.activity;

import android.app.NotificationManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.EntypoModule;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.joanzapata.iconify.fonts.MaterialCommunityModule;
import com.joanzapata.iconify.fonts.MaterialIcons;
import com.joanzapata.iconify.fonts.MaterialModule;
import com.joanzapata.iconify.fonts.SimpleLineIconsModule;
import com.qwash.washer.R;
import com.qwash.washer.Sample;
import com.qwash.washer.model.PrepareOrder;
import com.qwash.washer.ui.widget.RobotoBoldTextView;
import com.qwash.washer.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ProgressOrderActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title_toolbar)
    TextView toolbarTitle;
    MediaPlayer mp = null;
    @BindView(R.id.vehicle_image)
    ImageView vehicleImage;
    @BindView(R.id.vehicle_description)
    RobotoBoldTextView vehicleDescription;
    @BindView(R.id.des_date)
    TextView desDate;
    @BindView(R.id.date_time)
    TextView dateTime;
    @BindView(R.id.estimated_price)
    TextView estimatedPrice;
    @BindView(R.id.perfumed)
    TextView perfumed;
    @BindView(R.id.layout_perfumed)
    RelativeLayout layoutPerfumed;
    @BindView(R.id.interior_vaccum)
    TextView interiorVaccum;
    @BindView(R.id.layout_interior_vaccum)
    RelativeLayout layoutInteriorVaccum;
    @BindView(R.id.additional_select)
    RelativeLayout additionalSelect;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.seconds_vehicle_description)
    TextView secondsVehicleDescription;
    private PrepareOrder prepareOrder;

    @OnClick(R.id.btn_deacline)
    void Deacline() {
        finish();
    }

    @OnClick(R.id.btn_accept)
    void Accept() {
        finish();
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Iconify
                .with(new FontAwesomeModule())
                .with(new EntypoModule())
                .with(new MaterialModule())
                .with(new MaterialCommunityModule())
                .with(new SimpleLineIconsModule());
        setContentView(R.layout.activity_progress_order);

        Bundle bundle = getIntent().getExtras();
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
        getSupportActionBar().setTitle("Detail Order");
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(0);
        PlaySound();

        if (bundle != null) {
            String order = bundle.getString(Sample.ORDER);
            try {

                JSONObject json = new JSONObject(order);
                JSONObject jsonCustomer = (JSONObject) json.get(Sample.CUSTOMER);
                JSONObject jsonAddress = (JSONObject) json.get(Sample.ADDRESS);
                JSONObject jsonVehicle = (JSONObject) json.get(Sample.VEHICLE);
                JSONObject jsonDetails = (JSONObject) json.get(Sample.DETAILS);
                // customer order

                String userId = jsonCustomer.getString(Sample.ORDER_USERID);
                String username = jsonCustomer.getString(Sample.ORDER_USERNAME);
                String email = jsonCustomer.getString(Sample.ORDER_EMAIL);
                String name = jsonCustomer.getString(Sample.ORDER_NAME);
                String phone = jsonCustomer.getString(Sample.ORDER_PHONE);
                String authLevel = jsonCustomer.getString(Sample.ORDER_AUTHLEVEL);
                String firebase_id = jsonCustomer.getString(Sample.ORDER_FIREBASE_ID);

                // address order
                String usersDetailsId = jsonAddress.getString(Sample.ORDER_USERSDETAILSID);
                String userIdFk = jsonAddress.getString(Sample.ORDER_USERIDFK);
                String nameAddress = jsonAddress.getString(Sample.ORDER_NAMEADDRESS);
                String address = jsonAddress.getString(Sample.ORDER_ADDRESS);
                String latlong = jsonAddress.getString(Sample.ORDER_LATLONG);
                String type = jsonAddress.getString(Sample.ORDER_TYPE);

                //vehicle order
                String vCustomersId = jsonVehicle.getString(Sample.ORDER_VCUSTOMERSID);
                String vName = jsonVehicle.getString(Sample.ORDER_VNAME);
                String vBrand = jsonVehicle.getString(Sample.ORDER_VBRAND);
                String models = jsonVehicle.getString(Sample.ORDER_MODELS);
                String vTransmision = jsonVehicle.getString(Sample.ORDER_VTRANSMISION);
                String years = jsonVehicle.getString(Sample.ORDER_YEARS);
                String vId = jsonVehicle.getString(Sample.ORDER_VID);
                String vBrandId = jsonVehicle.getString(Sample.ORDER_VBRANDID);
                String vModelId = jsonVehicle.getString(Sample.ORDER_VMODELID);
                String vTransId = jsonVehicle.getString(Sample.ORDER_VTRANSID);
                String vYearsId = jsonVehicle.getString(Sample.ORDER_VYEARSID);

                //order detail
                String price = jsonDetails.getString(Sample.ORDER_PRICE);
                String perfumed = jsonDetails.getString(Sample.ORDER_PERFUMED);
                String interior_vaccum = jsonDetails.getString(Sample.ORDER_INTERIOR_VACCUM);
                String estimated_price = jsonDetails.getString(Sample.ORDER_ESTIMATED_PRICE);
                String datetime = jsonDetails.getString(Sample.ORDER_DATETIME);

                prepareOrder = new PrepareOrder();

                prepareOrder.userId = userId;
                prepareOrder.username = username;
                prepareOrder.email = email;
                prepareOrder.name = name;
                prepareOrder.phone = phone;
                prepareOrder.authLevel = authLevel;
                prepareOrder.firebase_id = firebase_id;

                // address order
                prepareOrder.usersDetailsId = usersDetailsId;
                prepareOrder.userIdFk = userIdFk;
                prepareOrder.nameAddress = nameAddress;
                prepareOrder.address = address;
                prepareOrder.latlong = latlong;
                prepareOrder.type = type;

                //vehicle order
                prepareOrder.vCustomersId = vCustomersId;
                prepareOrder.vName = vName;
                prepareOrder.vBrand = vBrand;
                prepareOrder.models = models;
                prepareOrder.vTransmision = vTransmision;
                prepareOrder.years = years;
                prepareOrder.vId = vId;
                prepareOrder.vBrandId = vBrandId;
                prepareOrder.vModelId = vModelId;
                prepareOrder.vTransId = vTransId;
                prepareOrder.vYearsId = vYearsId;

                //order detail
                prepareOrder.price = Integer.parseInt(price);
                prepareOrder.perfumed = Integer.parseInt(perfumed);
                prepareOrder.interior_vaccum = Integer.parseInt(interior_vaccum);
                prepareOrder.estimated_price = Integer.parseInt(estimated_price);
                prepareOrder.datetime = datetime;

                setData();

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }

    private void setData() {
        if (prepareOrder != null) {
            Glide
                    .with(this)
                    .load("")
                    .centerCrop()
                    .placeholder(prepareOrder.vId.equalsIgnoreCase("1") ? R.drawable.mobil : R.drawable.motor)
                    .crossFade()
                    .into(vehicleImage);
            vehicleDescription.setText(prepareOrder.vBrand + "\n" + prepareOrder.models + " " + prepareOrder.vTransmision + " " + prepareOrder.years);
            vehicleDescription.setTextColor(ContextCompat.getColor(this, R.color.white));

            desDate.setText("");
            dateTime.setText(prepareOrder.datetime);
            estimatedPrice.setText(Utils.Rupiah(prepareOrder.estimated_price));

            if (prepareOrder.vId.equalsIgnoreCase("1")) {
                additionalSelect.setVisibility(View.GONE);
                if (prepareOrder.perfumed != 0 || prepareOrder.interior_vaccum != 0) {
                    additionalSelect.setVisibility(View.VISIBLE);
                    if (prepareOrder.perfumed != 0) {
                        layoutPerfumed.setVisibility(View.VISIBLE);
                        perfumed.setText(Utils.Rupiah(prepareOrder.perfumed));
                    } else
                        perfumed.setVisibility(View.GONE);
                    if (prepareOrder.interior_vaccum != 0) {
                        layoutInteriorVaccum.setVisibility(View.VISIBLE);
                        interiorVaccum.setText(Utils.Rupiah(prepareOrder.interior_vaccum));
                    } else
                        interiorVaccum.setVisibility(View.GONE);
                }
            } else {
                additionalSelect.setVisibility(View.GONE);
            }

            address.setText(prepareOrder.address);

            secondsVehicleDescription.setText(prepareOrder.vBrand + "\n" + prepareOrder.models + " " + prepareOrder.vTransmision + " " + prepareOrder.years);
        }

    }

    protected void PlaySound() {
        stopPlaying();
        mp = MediaPlayer.create(this, R.raw.fishtank_bubbles);
        mp.setLooping(true);
        mp.start();
    }

    private void stopPlaying() {
        if (mp != null) {
            mp.stop();
            mp.release();
            mp = null;
        }
    }

    @Override
    public void onBackPressed() {
        //disable back
        //  super.onBackPressed();
    }

    @Override
    protected void onPause() {
        //disable Pause
        super.onPause();
        stopPlaying();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopPlaying();
        finish();
    }
}
