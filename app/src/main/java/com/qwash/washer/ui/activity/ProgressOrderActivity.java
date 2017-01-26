package com.qwash.washer.ui.activity;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.qwash.washer.api.ApiUtils;
import com.qwash.washer.api.client.order.OrderService;
import com.qwash.washer.api.model.order.OrderFinish;
import com.qwash.washer.api.model.order.OrderStartWash;
import com.qwash.washer.api.model.order.PickOrder;
import com.qwash.washer.model.PrepareOrder;
import com.qwash.washer.service.MessageFireBase;
import com.qwash.washer.service.PushNotification;
import com.qwash.washer.ui.widget.RobotoBoldTextView;
import com.qwash.washer.utils.Prefs;
import com.qwash.washer.utils.ProgressDialogBuilder;
import com.qwash.washer.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
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

    @BindView(R.id.vehicle_image)
    ImageView vehicleImage;
    @BindView(R.id.vehicle_description)
    RobotoBoldTextView vehicleDescription;
    @BindView(R.id.des_date)
    TextView desDate;
    @BindView(R.id.des_time)
    TextView desTime;
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
    @BindView(R.id.btn_deacline)
    Button btnDeacline;
    @BindView(R.id.btn_accept)
    Button btnAccept;

    @BindView(R.id.btn_navigation)
    ImageView btnNavigation;
    @BindView(R.id.customer_photo)
    AvatarView customerPhoto;
    @BindView(R.id.customer_name)
    TextView customerName;
    @BindView(R.id.customer_phone)
    TextView customerPhone;
    @BindView(R.id.btn_sms)
    ImageView btnSms;
    @BindView(R.id.btn_call)
    ImageView btnCall;
    @BindView(R.id.batas)
    View batas;
    @BindView(R.id.layout_navigation)
    LinearLayout layoutNavigation;
    @BindView(R.id.layout_sms)
    LinearLayout layoutSms;
    @BindView(R.id.layout_call)
    LinearLayout layoutCall;
    MediaPlayer mp = null;
    private PrepareOrder prepareOrder;
    private Context context;
    private String order_data;
    private String TAG = "ProgressOrderActivity";
    private ProgressDialogBuilder dialogProgress;

    @OnClick(R.id.btn_deacline)
    void ActionDeacline() {
        Deacline();
    }

    @OnClick({R.id.btn_accept, R.id.layout_navigation, R.id.layout_call, R.id.layout_sms})
    void Action(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_accept:
                stopPlaying();
                if (Prefs.getProgresWorking(context) == Sample.CODE_NO_ORDER) {
                    //AcceptOrder Order
                    AcceptOrder();
                } else if (Prefs.getProgresWorking(context) == Sample.CODE_ACCEPT) {
                    //sedang mencuci
                    StartWash();
                } else if (Prefs.getProgresWorking(context) == Sample.CODE_START) {
                    //finish pekerjaan mencuci
                    FinishWash();
                }
                break;
            case R.id.layout_navigation:
                String[] LatLong = prepareOrder.latlong.split(",");
                String uri = "http://maps.google.com/maps?f=d&hl=en&saddr=" + Prefs.getLatitude(this) + "," + Prefs.getLongitude(this) + "&daddr=" +
                        LatLong[0] + "," + LatLong[1];
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(Intent.createChooser(intent, "Select an application"));
                break;
            case R.id.layout_call:
                Intent phoneIntent = new Intent(Intent.ACTION_DIAL, Uri.fromParts(
                        "tel", prepareOrder.phone, null));
                startActivity(phoneIntent);
                break;
            case R.id.layout_sms:
                Intent sms = new Intent(Intent.ACTION_SENDTO);
                sms.setData(Uri.parse("smsto:" + Uri.encode(prepareOrder.phone)));
                startActivity(sms);
                break;
        }

    }

    private void CheckWorkingStatus() {
        if (Prefs.getProgresWorking(context) == Sample.CODE_NO_ORDER) {
            batas.setVisibility(View.GONE);
            layoutNavigation.setVisibility(View.GONE);
            layoutSms.setVisibility(View.GONE);
            layoutCall.setVisibility(View.GONE);
            additionalSelect.setVisibility(View.VISIBLE);
            btnDeacline.setVisibility(View.VISIBLE);
            btnAccept.setText("Accept");
        } else if (Prefs.getProgresWorking(context) == Sample.CODE_ACCEPT) {
            batas.setVisibility(View.VISIBLE);
            layoutNavigation.setVisibility(View.VISIBLE);
            layoutSms.setVisibility(View.VISIBLE);
            layoutCall.setVisibility(View.VISIBLE);
            additionalSelect.setVisibility(View.GONE);
            btnDeacline.setVisibility(View.VISIBLE);
            btnAccept.setText("Start");
        } else if (Prefs.getProgresWorking(context) == Sample.CODE_START) {
            batas.setVisibility(View.GONE);
            layoutNavigation.setVisibility(View.GONE);
            layoutSms.setVisibility(View.GONE);
            layoutCall.setVisibility(View.GONE);
            additionalSelect.setVisibility(View.VISIBLE);
            btnDeacline.setVisibility(View.GONE);
            btnAccept.setText("SELESAI");
        }
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
        ButterKnife.bind(this);
        context = getApplicationContext();
        dialogProgress = new ProgressDialogBuilder(this);
        Bundle bundle = getIntent().getExtras();

        btnNavigation.setImageDrawable(
                new IconDrawable(this, MaterialIcons.md_navigation)
                        .colorRes(R.color.blue_2196F3).sizeDp(60));

        btnCall.setImageDrawable(
                new IconDrawable(this, MaterialIcons.md_call)
                        .colorRes(R.color.blue_2196F3)
                        .actionBarSize());

        btnSms.setImageDrawable(
                new IconDrawable(this, MaterialIcons.md_message)
                        .colorRes(R.color.blue_2196F3)
                        .actionBarSize());

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(0);
        PlaySound();

        if (bundle != null) {
            order_data = bundle.getString(Sample.ORDER);
            try {

                JSONObject json = new JSONObject(order_data);

                JSONObject jsonCustomerOrder = (JSONObject) json.get(Sample.CUSTOMER_ORDER);
                JSONObject jsonCustomer = (JSONObject) json.get(Sample.CUSTOMER);
                JSONObject jsonAddress = (JSONObject) json.get(Sample.ADDRESS);
                JSONObject jsonVehicle = (JSONObject) json.get(Sample.VEHICLE);
                JSONObject jsonDetails = (JSONObject) json.get(Sample.DETAILS);
                // customer order

                String orders_ref = jsonCustomerOrder.getString(Sample.ORDERS_REF);

                String userId = jsonCustomer.getString(Sample.ORDER_USERID);
                String username = jsonCustomer.getString(Sample.ORDER_USERNAME);
                String email = jsonCustomer.getString(Sample.ORDER_EMAIL);
                String name = jsonCustomer.getString(Sample.ORDER_NAME);
                String phone = jsonCustomer.getString(Sample.ORDER_PHONE);
                String photo = jsonCustomer.getString(Sample.ORDER_PHOTO);
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
                String vTransmission = jsonVehicle.getString(Sample.ORDER_VTRANSMISSION);
                String years = jsonVehicle.getString(Sample.ORDER_YEARS);
                String vId = jsonVehicle.getString(Sample.ORDER_VID);
                String vBrandId = jsonVehicle.getString(Sample.ORDER_VBRANDID);
                String vModelId = jsonVehicle.getString(Sample.ORDER_VMODELID);
                String vTransId = jsonVehicle.getString(Sample.ORDER_VTRANSID);
                String vYearsId = jsonVehicle.getString(Sample.ORDER_VYEARSID);

                //order detail
                String price = jsonDetails.getString(Sample.ORDER_PRICE);
                String perfumed = jsonDetails.getString(Sample.ORDER_PERFUMED);
                String perfumed_status = jsonDetails.getString(Sample.ORDER_PERFUMED_STATUS);
                String interior_vaccum = jsonDetails.getString(Sample.ORDER_INTERIOR_VACCUM);
                String interior_vaccum_status = jsonDetails.getString(Sample.ORDER_INTERIOR_VACCUM_STATUS);
                String estimated_price = jsonDetails.getString(Sample.ORDER_ESTIMATED_PRICE);
                String date = jsonDetails.getString(Sample.ORDER_PICK_DATE);
                String time = jsonDetails.getString(Sample.ORDER_PICK_TIME);

                prepareOrder = new PrepareOrder();
                prepareOrder.orders_ref = orders_ref;
                prepareOrder.userId = userId;
                prepareOrder.username = username;
                prepareOrder.email = email;
                prepareOrder.name = name;
                prepareOrder.phone = phone;
                prepareOrder.photo = photo;
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
                prepareOrder.vTransmission = vTransmission;
                prepareOrder.years = years;
                prepareOrder.vId = vId;
                prepareOrder.vBrandId = vBrandId;
                prepareOrder.vModelId = vModelId;
                prepareOrder.vTransId = vTransId;
                prepareOrder.vYearsId = vYearsId;

                //order detail
                prepareOrder.price = Integer.parseInt(price);
                prepareOrder.perfumed = Integer.parseInt(perfumed);
                prepareOrder.perfumed_status = Integer.parseInt(perfumed_status);
                prepareOrder.interior_vaccum = Integer.parseInt(interior_vaccum);
                prepareOrder.interior_vaccum_status = Integer.parseInt(interior_vaccum_status);
                prepareOrder.estimated_price = Integer.parseInt(estimated_price);
                prepareOrder.pick_date = date;
                prepareOrder.pick_time = time;

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
            vehicleDescription.setText(prepareOrder.vBrand + "\n" + prepareOrder.models + " " + prepareOrder.vTransmission + " " + prepareOrder.years);
            vehicleDescription.setTextColor(ContextCompat.getColor(this, R.color.white));

            desDate.setText(prepareOrder.pick_date);
            desTime.setText(prepareOrder.pick_time);
            estimatedPrice.setText(Utils.Rupiah(prepareOrder.estimated_price));

            if (prepareOrder.vId.equalsIgnoreCase("1")) {
                additionalSelect.setVisibility(View.GONE);
                if (prepareOrder.perfumed_status != 0 || prepareOrder.interior_vaccum_status != 0) {
                    additionalSelect.setVisibility(View.VISIBLE);
                    if (prepareOrder.perfumed_status != 0) {
                        layoutPerfumed.setVisibility(View.VISIBLE);
                    } else
                        perfumed.setVisibility(View.GONE);
                    if (prepareOrder.interior_vaccum_status != 0) {
                        layoutInteriorVaccum.setVisibility(View.VISIBLE);
                    } else
                        interiorVaccum.setVisibility(View.GONE);
                }
            } else {
                additionalSelect.setVisibility(View.GONE);
            }


            PicassoLoader imageLoader = new PicassoLoader();
            imageLoader.loadImage(customerPhoto, Sample.BASE_URL_IMAGE + prepareOrder.photo, prepareOrder.name);
            customerName.setText(prepareOrder.name);
            customerPhone.setText(prepareOrder.phone);
            address.setText(prepareOrder.address);

            CheckWorkingStatus();
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
    protected void onDestroy() {
        super.onDestroy();
        stopPlaying();
        finish();
    }

    private void Deacline() {
        Prefs.putProgresWorking(context, Sample.CODE_NO_ORDER);

        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... params) {
                try {
                    JSONObject root = new JSONObject();
                    JSONArray jsonArray = new JSONArray();
                    jsonArray.put(prepareOrder.firebase_id);

                    JSONObject data = new JSONObject();
                    data.put(Sample.ACTION, Sample.CODE_DEACLINE);
                    data.put(Sample.MESSAGE, "deacline");

                    JSONObject washer = new JSONObject();
                    washer.put(Sample.ORDER_USERID, prepareOrder.userId);
                    washer.put(Sample.WASHER_FIREBASE_ID, Prefs.getFirebaseId(ProgressOrderActivity.this));

                    data.put(Sample.WASHER, washer);

                    root.put(Sample.DATA, data);
                    root.put(Sample.REGISTRATION_IDS, jsonArray);

                    String result = PushNotification.postToFCM(root.toString());

                    return result;
                } catch (Exception ex) {
                    ex.printStackTrace();
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(context, "You has decline a order", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                }
                return null;
            }

            @Override
            protected void onPostExecute(String result) {
                try {
                    JSONObject resultJson = new JSONObject(result);
                    int success, failure;
                    success = resultJson.getInt("success");
                    failure = resultJson.getInt("failure");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(context, "You has decline a order", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }
        }.execute();
    }

    // ======== AcceptOrder Order
    private void AcceptOrder() {
        {
            dialogProgress.show("AcceptOrder Wash ...", "Please wait...");
            Map<String, String> params = new HashMap<>();
            params.put(Sample.ORDERS_REF, prepareOrder.orders_ref);
            params.put(Sample.WASHER_ID, Prefs.getUserId(context));

            OrderService mService = ApiUtils.OrderService(this);
            mService.getPickOrderLink(params).enqueue(new Callback<PickOrder>() {
                @Override
                public void onResponse(Call<PickOrder> call, Response<PickOrder> response) {
                    dialogProgress.hide();
                    if (response.isSuccessful()) {
                        if (response.body().getStatus()) {
                            //terima orderan
                            Prefs.putProgresWorking(context, Sample.CODE_ACCEPT);
                            Prefs.putOrderedData(context, order_data);
                            CheckWorkingStatus();
                            PushNotifAcceptOrder();
                        }
                    } else {
                        int statusCode = response.code();
                        try {
                            JSONObject jsonObject = new JSONObject(response.errorBody().string());
                            String message = jsonObject.getString(Sample.MESSAGE);
                            // TODO
                            // kalo udah diterima orang finish();
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        } catch (JSONException | IOException e) {
                            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                        finish();
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
    }

    private void PushNotifAcceptOrder() {

        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... params) {
                try {
                    JSONObject root = new JSONObject();
                    JSONArray jsonArray = new JSONArray();
                    jsonArray.put(prepareOrder.firebase_id);

                    JSONObject data = new JSONObject();
                    data.put(Sample.ACTION, Sample.CODE_ACCEPT);
                    data.put(Sample.MESSAGE, "accept");

                    JSONObject washer = new JSONObject();
                    washer.put(Sample.ORDER_USERID, prepareOrder.userId);
                    washer.put(Sample.WASHER_FIREBASE_ID, Prefs.getFirebaseId(ProgressOrderActivity.this));
                    washer.put(Sample.WASHER_USER_ID, Prefs.getUserId(ProgressOrderActivity.this));
                    washer.put(Sample.WASHER_EMAIL, Prefs.getEmail(ProgressOrderActivity.this));
                    washer.put(Sample.WASHER_NAME, Prefs.getName(ProgressOrderActivity.this));
                    washer.put(Sample.WASHER_PHONE, Prefs.getPhone(ProgressOrderActivity.this));
                    washer.put(Sample.WASHER_PHOTO, Prefs.getPhoto(ProgressOrderActivity.this));
                    washer.put(Sample.WASHER_RATING, Prefs.getRating(ProgressOrderActivity.this));

                    data.put(Sample.WASHER, washer);

                    root.put(Sample.DATA, data);
                    root.put(Sample.REGISTRATION_IDS, jsonArray);

                    String result = PushNotification.postToFCM(root.toString());

                    return result;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String result) {
                try {
                    JSONObject resultJson = new JSONObject(result);
                    int success, failure;
                    success = resultJson.getInt("success");
                    failure = resultJson.getInt("failure");
                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        }.execute();
    }
    // ======== End AcceptOrder Order

    // ======== StartWash Order
    private void StartWash() {
        {
            dialogProgress.show("Start Wash ...", "Please wait...");
            Map<String, String> params = new HashMap<>();
            params.put(Sample.ORDERS_REF, prepareOrder.orders_ref);

            OrderService mService = ApiUtils.OrderService(this);
            mService.getOrderStartWashLink(params).enqueue(new Callback<OrderStartWash>() {
                @Override
                public void onResponse(Call<OrderStartWash> call, Response<OrderStartWash> response) {
                    dialogProgress.hide();
                    if (response.isSuccessful()) {
                        if (response.body().getStatus()) {
                            //start wash
                            Prefs.putProgresWorking(context, Sample.CODE_START);
                            Prefs.putOrderedData(context, order_data);
                            CheckWorkingStatus();
                            PushStartWash();
                        }
                    } else {
                        int statusCode = response.code();
                        try {
                            JSONObject jsonObject = new JSONObject(response.errorBody().string());
                            String message = jsonObject.getString(Sample.MESSAGE);
                            // TODO
                            // kalo udah diterima orang finish();
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        } catch (JSONException | IOException e) {
                            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                            finish();
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<OrderStartWash> call, Throwable t) {
                    String message = t.getMessage();
                    dialogProgress.hide();
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void PushStartWash() {
        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... params) {
                try {
                    JSONObject root = new JSONObject();
                    JSONArray jsonArray = new JSONArray();
                    jsonArray.put(prepareOrder.firebase_id);

                    JSONObject data = new JSONObject();
                    data.put(Sample.ACTION, Sample.CODE_START);
                    data.put(Sample.MESSAGE, "start");

                    JSONObject washer = new JSONObject();
                    washer.put(Sample.ORDER_USERID, prepareOrder.userId);
                    washer.put(Sample.WASHER_FIREBASE_ID, Prefs.getFirebaseId(ProgressOrderActivity.this));

                    data.put(Sample.WASHER, washer);

                    root.put(Sample.DATA, data);
                    root.put(Sample.REGISTRATION_IDS, jsonArray);

                    String result = PushNotification.postToFCM(root.toString());

                    return result;
                } catch (Exception ex) {
                    ex.printStackTrace();

                }
                return null;
            }

            @Override
            protected void onPostExecute(String result) {


                try {
                    JSONObject resultJson = new JSONObject(result);
                    int success, failure;
                    success = resultJson.getInt("success");
                    failure = resultJson.getInt("failure");
                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        }.execute();
    }
    // ======== End StartWash Order

    // ======== FinishWash Order
    private void FinishWash() {
        {
            dialogProgress.show("Finish Wash ...", "Please wait...");
            Map<String, String> params = new HashMap<>();
            params.put(Sample.ORDERS_REF, prepareOrder.orders_ref);
            params.put(Sample.WASHER_ID, Prefs.getUserId(context));
            params.put(Sample.SALDO, String.valueOf(prepareOrder.estimated_price));

            OrderService mService = ApiUtils.OrderService(this);
            mService.getOrderFinishLink(params).enqueue(new Callback<OrderFinish>() {
                @Override
                public void onResponse(Call<OrderFinish> call, Response<OrderFinish> response) {
                    dialogProgress.hide();
                    if (response.isSuccessful()) {
                        if (response.body().getStatus()) {
                            //finish wash
                            Prefs.putProgresWorking(context, Sample.CODE_NO_ORDER);
                            Prefs.putOrderedData(context, null);
                            CheckWorkingStatus();
                            PushNotifFinishWash();
                        }
                    } else {
                        int statusCode = response.code();
                        try {
                            JSONObject jsonObject = new JSONObject(response.errorBody().string());
                            String message = jsonObject.getString(Sample.MESSAGE);
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        } catch (JSONException | IOException e) {
                            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<OrderFinish> call, Throwable t) {
                    String message = t.getMessage();
                    dialogProgress.hide();
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void PushNotifFinishWash() {
        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... params) {
                try {
                    JSONObject root = new JSONObject();
                    JSONArray jsonArray = new JSONArray();
                    jsonArray.put(prepareOrder.firebase_id);

                    JSONObject data = new JSONObject();
                    data.put(Sample.ACTION, Sample.CODE_FINISH_WORKING);
                    data.put(Sample.MESSAGE, "finish");

                    JSONObject washer = new JSONObject();
                    washer.put(Sample.ORDER_USERID, prepareOrder.userId);
                    washer.put(Sample.WASHER_FIREBASE_ID, Prefs.getFirebaseId(ProgressOrderActivity.this));

                    data.put(Sample.WASHER, washer);

                    root.put(Sample.DATA, data);
                    root.put(Sample.REGISTRATION_IDS, jsonArray);

                    String result = PushNotification.postToFCM(root.toString());

                    return result;
                } catch (Exception ex) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(context, "You finaly has wash..", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                }
                return null;
            }

            @Override
            protected void onPostExecute(String result) {
                try {
                    JSONObject resultJson = new JSONObject(result);
                    int success, failure;
                    success = resultJson.getInt("success");
                    failure = resultJson.getInt("failure");
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(context, "You finaly has wash..", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(ProgressOrderActivity.this, "Message Failed, Unknown error occurred.", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    });

                }

            }
        }.execute();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageFireBase(MessageFireBase MessageFireBase) {
        try {
            JSONObject json = new JSONObject(MessageFireBase.getData());
            int action = json.getInt(Sample.ACTION);
            if (action == Sample.ACTION_CANCEL_ORDER) {
                Prefs.putProgresWorking(this, Sample.CODE_NO_ORDER);
                Toast.makeText(context, "You has decline a order", Toast.LENGTH_SHORT).show();
                finish();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

}
