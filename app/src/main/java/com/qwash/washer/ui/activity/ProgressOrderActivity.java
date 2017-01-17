package com.qwash.washer.ui.activity;

import android.app.NotificationManager;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.EntypoModule;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.joanzapata.iconify.fonts.MaterialCommunityModule;
import com.joanzapata.iconify.fonts.MaterialModule;
import com.joanzapata.iconify.fonts.SimpleLineIconsModule;
import com.qwash.washer.R;
import com.qwash.washer.Sample;
import com.qwash.washer.model.PrepareOrder;
import com.qwash.washer.ui.widget.RobotoBoldTextView;
import com.qwash.washer.utils.Prefs;
import com.qwash.washer.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;


public class ProgressOrderActivity extends AppCompatActivity {

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
    @BindView(R.id.btn_deacline)
    Button btnDeacline;
    @BindView(R.id.btn_accept)
    Button btnAccept;

    private PrepareOrder prepareOrder;
    private Context context;
    private String order_data;


    private OkHttpClient mClient = new OkHttpClient();
    private MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @OnClick(R.id.btn_deacline)
    void ActionDeacline() {
        Deacline();
    }

    @OnClick(R.id.btn_accept)
    void ActionAccept() {
        stopPlaying();
        ChangeWorkingStatus();
    }

    private void ChangeWorkingStatus() {
        if (Prefs.getProgresWorking(context) == 1) {
            Accept();
        } else if (Prefs.getProgresWorking(context) == 2) {
            //sedang mencuci
            Start();
        } else if (Prefs.getProgresWorking(context) == 3) {
            //finish pekerjaan mencuci
            Finish();
        }
    }


    private void CheckWorkingStatus() {
        if (Prefs.getProgresWorking(context) == 1) {
            btnDeacline.setVisibility(View.VISIBLE);
            btnAccept.setText("Accept");
        } else if (Prefs.getProgresWorking(context) == 2) {
            btnDeacline.setVisibility(View.VISIBLE);
            btnAccept.setText("Start");
        } else if (Prefs.getProgresWorking(context) == 3) {
            btnDeacline.setVisibility(View.GONE);
            btnAccept.setText("SELESAI");
        }
    }


    MediaPlayer mp = null;

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
        Bundle bundle = getIntent().getExtras();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(0);
        PlaySound();

        if (bundle != null) {
            order_data = bundle.getString(Sample.ORDER);
            Log.v("order_data", order_data);
            try {

                JSONObject json = new JSONObject(order_data);
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

                    String result = postToFCM(root.toString());
                    Log.d("RESULT", "Result: " + result);
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


    private void Start() {

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

                    String result = postToFCM(root.toString());
                    Log.d("RESULT", "Result: " + result);
                    return result;
                } catch (Exception ex) {
                    ex.printStackTrace();
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(context, "There has a mistakes. please contact Admin", Toast.LENGTH_SHORT).show();
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
                    if (success != 0) {
                        //start pekerjaan
                        Prefs.putProgresWorking(context, Sample.CODE_START);
                        Prefs.putOrderedData(context, order_data);
                        CheckWorkingStatus();
                    } else {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(ProgressOrderActivity.this, "Cannot start", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(ProgressOrderActivity.this, "Message Failed, Unknown error occurred.", Toast.LENGTH_LONG).show();
                        }
                    });

                }
            }
        }.execute();
    }


    private void Accept() {

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
                    washer.put(Sample.WASHER_RATING, Prefs.getRating(ProgressOrderActivity.this));

                    data.put(Sample.WASHER, washer);

                    root.put(Sample.DATA, data);
                    root.put(Sample.REGISTRATION_IDS, jsonArray);

                    String result = postToFCM(root.toString());
                    Log.d("RESULT", "Result: " + result);
                    return result;
                } catch (Exception ex) {
                    ex.printStackTrace();
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(context, "There has a mistakes. please contact Admin", Toast.LENGTH_SHORT).show();
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
                    if (success != 0) {
                        //terima orderan
                        Prefs.putProgresWorking(context, Sample.CODE_ACCEPT);
                        Prefs.putOrderedData(context, order_data);
                        CheckWorkingStatus();
                    } else {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(ProgressOrderActivity.this, "Cannot Aceppted", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(ProgressOrderActivity.this, "Message Failed, Unknown error occurred.", Toast.LENGTH_LONG).show();
                        }
                    });

                }
            }
        }.execute();
    }


    private void Finish() {
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

                    String result = postToFCM(root.toString());
                    Log.d("RESULT", "Result: " + result);
                    return result;
                } catch (Exception ex) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(context, "There has a mistakes. please contact Admin", Toast.LENGTH_SHORT).show();
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
                    if (success != 0) {
                        //finish pekerjaan
                        Prefs.putProgresWorking(context, Sample.CODE_NO_ORDER);
                        Prefs.putOrderedData(context, null);
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(context, "You finaly has wash..", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
                    }else {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(ProgressOrderActivity.this, "Cannot Finish", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(ProgressOrderActivity.this, "Message Failed, Unknown error occurred.", Toast.LENGTH_LONG).show();
                        }
                    });

                }

            }
        }.execute();
    }

    String postToFCM(String bodyString) throws IOException {
        RequestBody body = RequestBody.create(JSON, bodyString);
        Request request = new Request.Builder()
                .url(Sample.FCM_MESSAGE_URL)
                .post(body)
                .addHeader("Authorization", "key=" + Sample.SERVER_KEY_FIREBASE)
                .build();
        okhttp3.Response response = mClient.newCall(request).execute();
        return response.body().string();
    }


}
