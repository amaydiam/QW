package com.qwash.washer.ui.activity;

import android.app.NotificationManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bcgdv.asia.lib.ticktock.TickTockView;
import com.bumptech.glide.Glide;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.EntypoModule;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.joanzapata.iconify.fonts.MaterialCommunityIcons;
import com.joanzapata.iconify.fonts.MaterialCommunityModule;
import com.joanzapata.iconify.fonts.MaterialModule;
import com.joanzapata.iconify.fonts.SimpleLineIconsModule;
import com.qwash.washer.R;
import com.qwash.washer.Sample;
import com.qwash.washer.api.ApiUtils;
import com.qwash.washer.api.client.order.OrderService;
import com.qwash.washer.api.model.order.DataPickOrder;
import com.qwash.washer.api.model.order.DeaclineOrder;
import com.qwash.washer.api.model.order.FinishOrder;
import com.qwash.washer.api.model.order.PickOrder;
import com.qwash.washer.api.model.order.StartOrder;
import com.qwash.washer.model.PrepareOrder;
import com.qwash.washer.service.MessageFireBase;
import com.qwash.washer.service.PushNotification;
import com.qwash.washer.ui.widget.RobotoBoldTextView;
import com.qwash.washer.ui.widget.RobotoRegularButton;
import com.qwash.washer.ui.widget.RobotoRegularTextView;
import com.qwash.washer.utils.Prefs;
import com.qwash.washer.utils.ProgressDialogBuilder;

import net.soulwolf.widget.materialradio.MaterialRadioButton;
import net.soulwolf.widget.materialradio.MaterialRadioGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import agency.tango.android.avatarview.loader.PicassoLoader;
import agency.tango.android.avatarview.views.AvatarView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProgressOrderActivity extends AppCompatActivity {

    @BindView(R.id.fitur_service_motor)
    MaterialRadioGroup fiturServiceMotor;
    @BindView(R.id.fitur_service_car)
    MaterialRadioGroup fiturServiceCar;
    @BindView(R.id.vehicle_image)
    ImageView vehicleImage;
    @BindView(R.id.estimated_price)
    RobotoBoldTextView estimatedPrice;
    @BindView(R.id.rd_perfumed)
    MaterialRadioButton rdPerfumed;
    @BindView(R.id.rg_perfumed)
    MaterialRadioGroup rgPerfumed;
    @BindView(R.id.rd_interior_vaccum)
    MaterialRadioButton rdInteriorVaccum;
    @BindView(R.id.rg_interior_vaccum)
    MaterialRadioGroup rgInteriorVaccum;
    @BindView(R.id.rd_waterless)
    MaterialRadioButton rdWaterless;
    @BindView(R.id.rg_waterless)
    MaterialRadioGroup rgWaterless;
    @BindView(R.id.additional_select)
    LinearLayout additionalSelect;
    @BindView(R.id.gbr5)
    ImageView gbr5;
    @BindView(R.id.address)
    RobotoRegularTextView address;
    @BindView(R.id.btn_navigation)
    ImageView btnNavigation;
    @BindView(R.id.layout_navigation)
    LinearLayout layoutNavigation;
    @BindView(R.id.customer_photo)
    AvatarView customerPhoto;
    @BindView(R.id.customer_name)
    RobotoRegularTextView customerName;
    @BindView(R.id.customer_phone)
    RobotoRegularTextView customerPhone;
    @BindView(R.id.btn_sms)
    ImageView btnSms;
    @BindView(R.id.layout_sms)
    LinearLayout layoutSms;
    @BindView(R.id.btn_call)
    ImageView btnCall;
    @BindView(R.id.layout_call)
    LinearLayout layoutCall;
    @BindView(R.id.btn_deacline)
    RobotoRegularButton btnDeacline;
    @BindView(R.id.btn_accept)
    RobotoRegularButton btnAccept;
    @BindView(R.id.countdown)
    TickTockView countdown;
    @BindView(R.id.layout_user)
    LinearLayout layoutUser;
    private ProgressDialogBuilder dialogProgress;


    @OnClick(R.id.layout_navigation)
    void Navigation() {
        String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?saddr=%f,%f(%s)&daddr=%f,%f(%s)", Prefs.getGeometryLat(this), Prefs.getGeometryLong(this), getString(R.string.your_location), Double.valueOf(prepareOrder.getLat()), Double.valueOf(prepareOrder.getLong()), prepareOrder.getName() + "'s Location");
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        intent.setPackage("com.google.android.apps.maps");
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            try {
                Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(unrestrictedIntent);
            } catch (ActivityNotFoundException innerEx) {
                Toast.makeText(this, "Please install a maps application", Toast.LENGTH_LONG).show();
            }
        }

    }

    @OnClick(R.id.layout_call)
    void Call() {
        Intent phoneIntent = new Intent(Intent.ACTION_DIAL, Uri.fromParts(
                "tel", prepareOrder.getUsername(), null));
        startActivity(phoneIntent);
    }


    @OnClick(R.id.layout_sms)
    void SMS() {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("smsto:" + Uri.encode(prepareOrder.getUsername())));
        startActivity(intent);

    }

    @OnClick(R.id.btn_deacline)
    void Deacline() {
        if (Prefs.getProgresWorking(this) == Sample.CODE_NO_ORDER || Prefs.getProgresWorking(this) == Sample.CODE_GET_ORDER) {
            finishOrder();
        } else if (Prefs.getProgresWorking(this) == Sample.CODE_ACCEPT_ORDER) {
            DeaclineOrderAction();
        }
    }

    @OnClick(R.id.btn_accept)
    void Accept() {
        if (Prefs.getProgresWorking(this) == Sample.CODE_NO_ORDER || Prefs.getProgresWorking(this) == Sample.CODE_GET_ORDER) {
            AcceptOrderAction();
        } else if (Prefs.getProgresWorking(this) == Sample.CODE_ACCEPT_ORDER) {
            StartOrderAction();
        } else if (Prefs.getProgresWorking(this) == Sample.CODE_START_WORKING) {
            FinishOrderAction();
        }
    }

    private void FinishOrderAction() {
        dialogProgress.show("Finish Order ...", getString(R.string.please_wait));
        Map<String, String> params = new HashMap<>();
        params.put(Sample.ORDERS_ID, Prefs.getLastOrdersId(this));
        params.put(Sample.WASHERS_ID, Prefs.getUserId(this));

        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            Log.v(key, value);
        }


        OrderService mService = ApiUtils.OrderService(this);
        mService.getFinishOrderLink("Bearer " + Prefs.getToken(this), params).enqueue(new Callback<FinishOrder>() {
            @Override
            public void onResponse(Call<FinishOrder> call, Response<FinishOrder> response) {
                dialogProgress.hide();
                if (response.isSuccessful()) {
                    if (response.body().getStatus()) {
                        PushNotif(Sample.CODE_FINISH_WORKING);
                        finishOrder();
                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<FinishOrder> call, Throwable t) {
                String message = t.getMessage();
                dialogProgress.hide();
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void StartOrderAction() {
        dialogProgress.show("Start ...", getString(R.string.please_wait));
        Map<String, String> params = new HashMap<>();
        params.put(Sample.ORDERS_ID, Prefs.getLastOrdersId(this));
        params.put(Sample.WASHERS_ID, Prefs.getUserId(this));
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            Log.v(key, value);
        }
        Log.v("token", "Bearer " + Prefs.getToken(this));


        OrderService mService = ApiUtils.OrderService(this);
        mService.getStartOrderLink("Bearer " + Prefs.getToken(this), params).enqueue(new Callback<StartOrder>() {
            @Override
            public void onResponse(Call<StartOrder> call, Response<StartOrder> response) {
                dialogProgress.hide();
                if (response.isSuccessful()) {
                    if (response.body().getStatus()) {
                        Prefs.putProgresWorking(ProgressOrderActivity.this, Sample.CODE_START_WORKING);
                        UpdateUI();
                        PushNotif(Sample.CODE_START_WORKING);
                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<StartOrder> call, Throwable t) {
                String message = t.getMessage();
                dialogProgress.hide();
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void AcceptOrderAction() {
        dialogProgress.show("Accept Order...", getString(R.string.please_wait));
        Map<String, String> params = new HashMap<>();
        params.put(Sample.CUSTOMERS_ID, prepareOrder.getCustomersId());
        params.put(Sample.WASHERS_ID, Prefs.getUserId(this));
        params.put(Sample.VEHICLES, String.valueOf(prepareOrder.getVehicles()));
        params.put(Sample.TYPE, String.valueOf(prepareOrder.getVehicles_type()));
        params.put(Sample.LAT, prepareOrder.getLat());
        params.put(Sample.LONG, prepareOrder.getLong());
        params.put(Sample.NAMEADDRESS, prepareOrder.getNameAddress());
        params.put(Sample.ADDRESS, prepareOrder.getAddress());
        params.put(Sample.PRICE, String.valueOf(prepareOrder.getEstimated_price()));
        params.put(Sample.PERFUM, String.valueOf(prepareOrder.getPerfum_status()));
        params.put(Sample.VACUM, String.valueOf(prepareOrder.getInterior_vaccum_status()));
        params.put(Sample.WATERLESS, String.valueOf(prepareOrder.getWaterless_status()));
        params.put(Sample.SERVICES, "service");
        params.put(Sample.TOKEN, prepareOrder.getToken());

        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            Log.v(key, value);
        }


        OrderService mService = ApiUtils.OrderService(this);
        mService.getPickOrderLink("Bearer " + Prefs.getToken(this), params).enqueue(new Callback<PickOrder>() {
            @Override
            public void onResponse(Call<PickOrder> call, Response<PickOrder> response) {
                dialogProgress.hide();
                if (response.isSuccessful()) {
                    if (response.body().getStatus()) {
                        DataPickOrder dataPickOrder = response.body().getDataPickOrder();
                        Prefs.putLastOrdersId(ProgressOrderActivity.this, dataPickOrder.getOrdersId());
                        Prefs.putProgresWorking(ProgressOrderActivity.this, Sample.CODE_ACCEPT_ORDER);
                        UpdateUI();
                        PushNotif(Sample.CODE_ACCEPT_ORDER);
                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<PickOrder> call, Throwable t) {
                String message = t.getMessage();
                dialogProgress.hide();
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void DeaclineOrderAction() {
        dialogProgress.show("Deacline Order...", getString(R.string.please_wait));
        Map<String, String> params = new HashMap<>();
        params.put(Sample.ORDERS_ID, Prefs.getLastOrdersId(this));
        params.put(Sample.WASHERS_ID, Prefs.getUserId(this));

        OrderService mService = ApiUtils.OrderService(this);
        mService.getDeaclineOrderLink("Bearer " + Prefs.getToken(this), params).enqueue(new Callback<DeaclineOrder>() {
            @Override
            public void onResponse(Call<DeaclineOrder> call, Response<DeaclineOrder> response) {
                dialogProgress.hide();
                if (response.isSuccessful()) {
                    if (response.body().getStatus()) {
                        PushNotif(Sample.CODE_DEACLINE_ORDER);
                        finishOrder();
                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<DeaclineOrder> call, Throwable t) {
                String message = t.getMessage();
                dialogProgress.hide();
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void PushNotif(final int code) {
        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... params) {
                try {
                    JSONObject root = new JSONObject();
                    JSONArray jsonArray = new JSONArray();
                    jsonArray.put(prepareOrder.getFirebase_id());

                    JSONObject data = new JSONObject();
                    data.put(Sample.ACTION, code);

                    JSONObject order = new JSONObject();

                    JSONObject details = new JSONObject();
                    details.put(Sample.ORDERS_ID, Prefs.getLastOrdersId(ProgressOrderActivity.this));
                    order.put(Sample.DETAILS, details);

                    JSONObject washer = new JSONObject();
                    washer.put(Sample.WASHER_FIREBASE_ID, Prefs.getFirebaseId(ProgressOrderActivity.this));
                    washer.put(Sample.WASHERS_ID, Prefs.getUserId(ProgressOrderActivity.this));
                    washer.put(Sample.WASHER_EMAIL, Prefs.getEmail(ProgressOrderActivity.this));
                    washer.put(Sample.WASHER_NAME, Prefs.getFullName(ProgressOrderActivity.this));
                    washer.put(Sample.WASHER_USERNAME, Prefs.getUsername(ProgressOrderActivity.this));
                    washer.put(Sample.WASHER_PHOTO, Prefs.getProfilePhoto(ProgressOrderActivity.this));
                    washer.put(Sample.WASHER_RATING, Prefs.getRating(ProgressOrderActivity.this));
                    order.put(Sample.WASHER, washer);

                    data.put(Sample.ORDER, order);
                    root.put(Sample.DATA, data);
                    root.put(Sample.REGISTRATION_IDS, jsonArray);

                    String result = PushNotification.postToFCM(root.toString());

                    Log.v("result", result);

                    return result;
                } catch (Exception ex) {
                    ex.printStackTrace();
                    Log.v("Error", ex.getMessage());
                }
                return null;
            }

            @Override
            protected void onPostExecute(String result) {
            }
        }.execute();
    }

    private Vibrator v;
    private MediaPlayer player;
    private PrepareOrder prepareOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Iconify
                .with(new FontAwesomeModule())
                .with(new EntypoModule())
                .with(new MaterialModule())
                .with(new MaterialCommunityModule())
                .with(new SimpleLineIconsModule());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_order);
        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN |
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        ButterKnife.bind(this);
        dialogProgress = new ProgressDialogBuilder(this);

        countdown.setOnTickListener(new TickTockView.OnTickListener() {
            @Override
            public String getText(long timeRemaining) {
                int seconds = (int) (timeRemaining / 1000) % 60;
                if(seconds==0){
                    finishOrder();
                }
                return String.valueOf(new DecimalFormat("00").format(seconds));
            }
        });



        NotificationManager nMgr = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        nMgr.cancel(Sample.ID_NOTIF_ORDER);


        SetUI();
        getData();
        setData();
        UpdateUI();

    }

    private void SetUI() {
        btnNavigation.setImageDrawable(
                new IconDrawable(this, MaterialCommunityIcons.mdi_navigation)
                        .colorRes(R.color.blue_1E87DA).actionBarSize());
        btnCall.setImageDrawable(
                new IconDrawable(this, MaterialCommunityIcons.mdi_phone)
                        .colorRes(R.color.blue_1E87DA).actionBarSize());
        btnSms.setImageDrawable(
                new IconDrawable(this, MaterialCommunityIcons.mdi_message_text)
                        .colorRes(R.color.blue_1E87DA).actionBarSize());

    }

    private void setData() {

        //header
        if (prepareOrder.getVehicles_type() == 1) {
            fiturServiceCar.setVisibility(View.VISIBLE);
            fiturServiceMotor.setVisibility(View.GONE);
        } else {
            fiturServiceCar.setVisibility(View.GONE);
            fiturServiceMotor.setVisibility(View.VISIBLE);
        }

        int d = 0;
        if (prepareOrder.getVehicles() == Sample.VEHICLE_CAR_CITY_CAR) {
            d = R.drawable.big_citycar;
        } else if (prepareOrder.getVehicles() == Sample.VEHICLE_CAR_MINIVAN) {
            d = R.drawable.big_minivan;
        } else if (prepareOrder.getVehicles() == Sample.VEHICLE_CAR_SUV) {
            d = R.drawable.big_suv;
        } else if (prepareOrder.getVehicles() == Sample.VEHICLE_MOTORCYCLE_UNDER_150) {
            d = R.drawable.big_under_srp_cc;
        } else if (prepareOrder.getVehicles() == Sample.VEHICLE_MOTORCYCLE_150) {
            d = R.drawable.big_srp_cc;
        } else if (prepareOrder.getVehicles() == Sample.VEHICLE_MOTORCYCLE_ABOVE_150) {
            d = R.drawable.big_above_srp_cc;
        }

        Glide
                .with(this)
                .load("")
                .centerCrop()
                .placeholder(d)
                .crossFade()
                .into(vehicleImage);

        //estimasi price
        estimatedPrice.setText(prepareOrder.getEstimated_priceOnPrice());

        //additional
        if (prepareOrder.getVehicles_type() == 1 && (prepareOrder.getPerfum_status() == 1
                || prepareOrder.getInterior_vaccum_status() == 1
                || prepareOrder.getWaterless_status() == 1
        )) {
            additionalSelect.setVisibility(View.VISIBLE);
            if (prepareOrder.getPerfum_status() == 1)
                rdPerfumed.setVisibility(View.VISIBLE);
            else
                rdPerfumed.setVisibility(View.GONE);

            if (prepareOrder.getInterior_vaccum_status() == 1)
                rdInteriorVaccum.setVisibility(View.VISIBLE);
            else
                rdInteriorVaccum.setVisibility(View.GONE);

            if (prepareOrder.getWaterless_status() == 1)
                rdWaterless.setVisibility(View.VISIBLE);
            else
                rdWaterless.setVisibility(View.GONE);

        } else {
            additionalSelect.setVisibility(View.GONE);
        }

        //address
        address.setText(prepareOrder.getAddress());

        //user

        PicassoLoader imageLoader = new PicassoLoader();
        imageLoader.loadImage(customerPhoto, Sample.BASE_URL_QWASH_PUBLIC + "", prepareOrder.getName());

        customerName.setText(prepareOrder.getName());
        customerPhone.setText(prepareOrder.getUsername());


    }

    private void getData() {
        try {
            JSONObject jsonOrder = new JSONObject(Prefs.getOrderedData(this));

            JSONObject customer = new JSONObject(jsonOrder.getString(Sample.CUSTOMER));
            String customersId = customer.getString(Sample.ORDER_USERID);
            String username = customer.getString(Sample.ORDER_USERNAME);
            String email = customer.getString(Sample.ORDER_EMAIL);
            String name = customer.getString(Sample.ORDER_NAME);
            String firebase_id = customer.getString(Sample.ORDER_FIREBASE_ID);


            JSONObject address = new JSONObject(jsonOrder.getString(Sample.ADDRESS));
            String usersDetailsId = address.getString(Sample.ORDER_USERSDETAILSID);
            String userIdFk = address.getString(Sample.ORDER_USERIDFK);
            String nameAddress = address.getString(Sample.ORDER_NAMEADDRESS);
            String order_address = address.getString(Sample.ORDER_ADDRESS);
            String lat = address.getString(Sample.ORDER_LAT);
            String Long = address.getString(Sample.ORDER_LONG);
            String type = address.getString(Sample.ORDER_TYPE);


            JSONObject vehicle = new JSONObject(jsonOrder.getString(Sample.VEHICLE));
            int vehicles_type = Integer.parseInt(vehicle.getString(Sample.ORDER_VEHICLES_TYPE));
            int vehicles = Integer.parseInt(vehicle.getString(Sample.ORDER_VEHICLES));


            JSONObject details = new JSONObject(jsonOrder.getString(Sample.DETAILS));

            String token = details.getString(Sample.ORDER_TOKEN);
            int price = Integer.parseInt(details.getString(Sample.ORDER_PRICE));

            int perfum_price = Integer.parseInt(details.getString(Sample.ORDER_PERFUM_PRICE));
            int perfum_status = Integer.parseInt(details.getString(Sample.ORDER_PERFUM_STATUS));


            int interior_vaccum_price = Integer.parseInt(details.getString(Sample.ORDER_INTERIOR_VACUUM_PRICE));
            int interior_vaccum_status = Integer.parseInt(details.getString(Sample.ORDER_INTERIOR_VACUUM_STATUS));


            int waterless_price = Integer.parseInt(details.getString(Sample.ORDER_WATERLESS_PRICE));
            int waterless_status = Integer.parseInt(details.getString(Sample.ORDER_WATERLESS_STATUS));

            int estimated_price = Integer.parseInt(details.getString(Sample.ORDER_ESTIMATED_PRICE));

            prepareOrder = new PrepareOrder(token, customersId, username, email, name, firebase_id, usersDetailsId, userIdFk, nameAddress, order_address, lat, Long, type, vehicles_type, vehicles, price, perfum_price, perfum_status, interior_vaccum_price, interior_vaccum_status, waterless_price, waterless_status, estimated_price);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void UpdateUI() {

        if (Prefs.getProgresWorking(this) == Sample.CODE_NO_ORDER || Prefs.getProgresWorking(this) == Sample.CODE_GET_ORDER) {
            Prefs.putProgresWorking(getApplicationContext(), Sample.CODE_GET_ORDER);
            PlaySound();
            StartCountDown();
            layoutNavigation.setVisibility(View.GONE);
            layoutUser.setVisibility(View.GONE);
        } else if (Prefs.getProgresWorking(this) == Sample.CODE_ACCEPT_ORDER) {
            StopSound();
            StopCountDown();
            additionalSelect.setVisibility(View.GONE);
            layoutNavigation.setVisibility(View.VISIBLE);
            layoutUser.setVisibility(View.VISIBLE);

            if (Prefs.getProgresWorking(this) == Sample.CODE_ACCEPT_ORDER)
                btnAccept.setText(R.string.start);

        } else if (Prefs.getProgresWorking(this) == Sample.CODE_START_WORKING) {
            btnDeacline.setVisibility(View.GONE);
            btnAccept.setText(R.string.finish);
            if (prepareOrder.getVehicles_type() == 1 && (prepareOrder.getPerfum_status() == 1 || prepareOrder.getInterior_vaccum_status() == 1 || prepareOrder.getWaterless_status() == 1))
                additionalSelect.setVisibility(View.VISIBLE);
            else
                additionalSelect.setVisibility(View.GONE);
            layoutNavigation.setVisibility(View.GONE);
            layoutUser.setVisibility(View.GONE);

        }

    }

    void PlaySound() {
        Uri alert = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.fishtank_bubbles);
        if (alert == null) {
            alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);

            if (alert == null) {
                // alert is null, using backup
                alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                // I can't see this ever being null (as always have a default notification)
                // but just incase
                if (alert == null) {
                    // alert backup is null, using 2nd backup
                    alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
                }
            }
        }
        //   show();

        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        player = MediaPlayer.create(this, alert);
        player.setLooping(true);
        player.start();
        long[] pattern = {0, 1000, 1000};
        v.vibrate(pattern, 0);
    }


    void StopSound() {
        if (player != null && player.isPlaying()) {
            player.stop();
        }
        if (v != null && v.hasVibrator()) {
            v.cancel();
        }
    }


    void StartCountDown() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.SECOND, 16);
        if (countdown != null) {
            countdown.start(c);
        }
    }

    void StopCountDown() {
        if (countdown != null) {
            countdown.setVisibility(View.GONE);
            countdown.stop();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        StopSound();

    }


    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        if (!Prefs.isLogedIn(this)) {
            //   toHomeActivity();
        } else if (Prefs.getOrderedData(this) != null
                && Prefs.getProgresWorking(this) != Sample.CODE_NO_ORDER
                && Prefs.getProgresWorking(this) != Sample.CODE_GET_ORDER) {
            //  ProgressOrderedrActiivity();
        } else {

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Iconify
                .with(new FontAwesomeModule())
                .with(new EntypoModule())
                .with(new MaterialModule())
                .with(new MaterialCommunityModule())
                .with(new SimpleLineIconsModule());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageFireBase(MessageFireBase MessageFireBase) {
        try {
            JSONObject json = new JSONObject(MessageFireBase.getData());
            int action = json.getInt(Sample.ACTION);
            if (action == Sample.ACTION_CANCEL_ORDER) {
                finishOrder();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void finishOrder() {
        Prefs.putProgresWorking(this, Sample.CODE_NO_ORDER);
        Prefs.putOrderedData(ProgressOrderActivity.this, null);
        finish();
    }


    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }


    @Override
    public void onBackPressed() {
        // super.onBackPressed();
    }

}