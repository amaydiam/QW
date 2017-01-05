package com.qwash.washer.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.qwash.washer.R;
import com.qwash.washer.Sample;
import com.qwash.washer.api.ApiUtils;
import com.qwash.washer.api.client.AddressMapsFromGoogleApi;
import com.qwash.washer.api.model.Address;
import com.qwash.washer.api.model.AddressFromMapsResponse;
import com.qwash.washer.model.History;
import com.qwash.washer.model.PrepareOrder;
import com.qwash.washer.model.Vehicle;
import com.qwash.washer.ui.fragment.PrepareOrderFragment;
import com.qwash.washer.ui.fragment.WasherOrderFragment;
import com.qwash.washer.ui.widget.RobotoRegularEditText;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.EntypoIcons;
import com.joanzapata.iconify.fonts.EntypoModule;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.joanzapata.iconify.fonts.MaterialCommunityIcons;
import com.joanzapata.iconify.fonts.MaterialCommunityModule;
import com.joanzapata.iconify.fonts.MaterialIcons;
import com.joanzapata.iconify.fonts.MaterialModule;
import com.joanzapata.iconify.fonts.SimpleLineIconsIcons;
import com.joanzapata.iconify.fonts.SimpleLineIconsModule;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import top.wefor.circularanim.CircularAnim;

public class HomeActivity extends AppCompatActivity implements
        OnMapReadyCallback,
        PrepareOrderFragment.OnOrderedListener,
        WasherOrderFragment.OnWasherOrderListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    @BindView(R.id.search)
    RobotoRegularEditText search;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.view_toolbar)
    LinearLayout viewToolbar;
    private AddressMapsFromGoogleApi mService;

    @OnTextChanged(value = R.id.search,
            callback = OnTextChanged.Callback.TEXT_CHANGED)
    void onChanged(Editable editable) {
        ShowHideAcSearch();
    }

    private void ShowHideAcSearch() {
        try {
            String val_search = search.getText().toString().trim();
            if (val_search.length() == 0) {
                acSearch.setVisible(false);
            } else {
                acSearch.setVisible(true);
            }
            supportInvalidateOptionsMenu();
        } catch (Exception e) {

        }
    }

    @BindView(R.id.pick_location)
    ImageView pickLocation;

    @BindView(R.id.btn_work)
    FloatingActionButton btnWork;
    @BindView(R.id.btn_home)
    FloatingActionButton btnHome;
    @BindView(R.id.btn_my_location)
    FloatingActionButton btnMyLocation;

    @BindView(R.id.btn_menu_home)
    FloatingActionButton btnMenuHome;

    @BindView(R.id.layout_menu_home)
    View layoutMenuHome;

    @BindView(R.id.img_menu_home)
    ImageView imgMenuHome;
    @BindView(R.id.img_menu_notification)
    ImageView imgMenuNotification;
    @BindView(R.id.img_menu_my_balance)
    ImageView imgMenuMyBalance;
    @BindView(R.id.img_menu_history)
    ImageView imgMenuHistory;
    @BindView(R.id.img_menu_help)
    ImageView imgMenuHelp;
    @BindView(R.id.img_menu_my_account)
    ImageView imgMenuMyAccount;

    @OnClick({R.id.menu_my_account,
            R.id.menu_help,
            R.id.menu_history,
            R.id.menu_my_balance,
            R.id.menu_notification,
            R.id.menu_home})
    void ClickMenu(View v) {
        ShowMenuHome();
        int id = v.getId();
        switch (id) {
            case R.id.menu_home:
                break;
            case R.id.menu_notification:
                startActivity(new Intent(this, NotificationActivity.class));
                break;
            case R.id.menu_my_balance:
             startActivity(new Intent(this, MyBalanceActivity.class));
                break;
            case R.id.menu_history:
                startActivity(new Intent(this, HistoryActivity.class));
                break;
            case R.id.menu_help:
                startActivity(new Intent(this, HelpActivity.class));
                break;
            case R.id.menu_my_account:
                startActivity(new Intent(this, MyAccountActivity.class));
                break;
            default:
                break;
        }
    }

    @OnClick(R.id.btn_menu_home)
    void ActionMenuHome() {
        ShowMenuHome();
    }

    @OnClick(R.id.btn_work)
    void ActionWork() {
        if (!isHidden)
            ShowMenuHome();
        startActivity(new Intent(this, SelectLocationActivity.class));
        //Toast.makeText(this, "Work CLikced!!", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.btn_home)
    void ActionHome() {
        if (!isHidden)
            ShowMenuHome();
        LoadPrepareOrderFragment();
        //  Toast.makeText(this, "Home CLikced!!", Toast.LENGTH_SHORT).show();
    }

    /*@OnClick(R.id.btn_my_location)
    void ActionMyLocation() {
        Toast.makeText(this, "My Location CLikced!!", Toast.LENGTH_SHORT).show();
    }*/

    Fragment current_fragment = null;
    private double current_latitude, current_longitude;
    private View mapView;
    private boolean isHidden = true;
    private MenuItem acSearch;
    private GoogleMap mMap;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Iconify
                .with(new FontAwesomeModule())
                .with(new EntypoModule())
                .with(new MaterialModule())
                .with(new MaterialCommunityModule())
                .with(new SimpleLineIconsModule());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        mService = ApiUtils.getAddressMapsFromGoogleApi();
        //set Toolbar 
        setSupportActionBar(toolbar);/*
        toolbar.setNavigationIcon(
                new IconDrawable(this, MaterialIcons.md_search)
                        .colorRes(R.color.black_424242)
                        .actionBarSize());
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeActivity.this, "Menu Clicked!!", Toast.LENGTH_SHORT).show();
            }
        });
*/
        imgMenuHome.setImageDrawable(new IconDrawable(this, SimpleLineIconsIcons.icon_home).colorRes(R.color.white).actionBarSize());
        imgMenuNotification.setImageDrawable(new IconDrawable(this, MaterialIcons.md_notifications_none).colorRes(R.color.white).actionBarSize());
        imgMenuMyBalance.setImageDrawable(new IconDrawable(this, EntypoIcons.entypo_wallet).colorRes(R.color.white).actionBarSize());
        imgMenuHistory.setImageDrawable(new IconDrawable(this, MaterialCommunityIcons.mdi_history).colorRes(R.color.white).actionBarSize());
        imgMenuHelp.setImageDrawable(new IconDrawable(this, MaterialIcons.md_help_outline).colorRes(R.color.white).actionBarSize());
        imgMenuMyAccount.setImageDrawable(new IconDrawable(this, EntypoIcons.entypo_user).colorRes(R.color.white).actionBarSize());

        if (isHidden) {
            layoutMenuHome.setVisibility(View.INVISIBLE);
            btnMenuHome.setImageDrawable(
                    new IconDrawable(this, MaterialIcons.md_menu)
                            .colorRes(R.color.black_424242)
                            .actionBarSize());
            //    viewToolbar.setVisibility(View.VISIBLE);
        }

        pickLocation.setImageDrawable(
                new IconDrawable(this, EntypoIcons.entypo_location_pin)
                        .colorRes(R.color.colorAccent)
                        .sizeDp(48));

        int paddingBottomInDp = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, (48 / 4) * 3, getResources()
                        .getDisplayMetrics());

        pickLocation.setPadding(0, 0, 0, paddingBottomInDp);

        btnWork.setImageDrawable(
                new IconDrawable(this, SimpleLineIconsIcons.icon_bag)
                        .colorRes(R.color.black_424242)
                        .actionBarSize());
        btnHome.setImageDrawable(
                new IconDrawable(this, SimpleLineIconsIcons.icon_home)
                        .colorRes(R.color.black_424242)
                        .actionBarSize());
        btnMyLocation.setImageDrawable(
                new IconDrawable(this, MaterialIcons.md_my_location)
                        .colorRes(R.color.black_424242)
                        .actionBarSize());

        pickLocationShow(false);


        new TedPermission(this)
                .setPermissionListener(permissionMapsListener)
                .setDeniedMessage("Jika kamu menolak permission, Anda tidak dapat mendeteksi lokasi Anda \nHarap hidupkan permission ACCESS_FINE_LOCATION pada [Setting] > [Permission]")
                .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
                .check();

    }


    private void LoadPrepareOrderFragment() {
        current_fragment = new PrepareOrderFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_bottom, current_fragment).commit();
    }

    private void LoadWasherOrderFragment() {
        current_fragment = new WasherOrderFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_bottom, current_fragment).commitAllowingStateLoss();
    }

    private void RemoveBottomFragment() {
        getSupportFragmentManager().beginTransaction().remove(current_fragment).commit();
        current_fragment = null;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.setMyLocationEnabled(true);
        View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
        locationButton.setVisibility(View.GONE);

        //Initialize Google Play Services
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
            }
        } else {
            buildGoogleApiClient();
        }

    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }

        btnMyLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isHidden)
                    ShowMenuHome();
                if (ContextCompat.checkSelfPermission(HomeActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    float zoomLevel = mMap.getCameraPosition().zoom;
                    Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                    if (location == null) {
                        new AlertDialog.Builder(HomeActivity.this)
                                .setTitle("Please activate location")
                                .setMessage("Click ok to goto settings.")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                        startActivity(intent);
                                    }
                                })
                                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                    } else {
                        double Lat = location.getLatitude();
                        double Long = location.getLongitude();
                        LoadAddress(Lat + "," + Long);
                        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(Lat, Long), zoomLevel);
                        mMap.animateCamera(cameraUpdate);
                    }
                }
            }
        });

        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                CameraPosition position = mMap.getCameraPosition();
                current_latitude = position.target.latitude;
                current_longitude = position.target.longitude;
            }
        });

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {

        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        // current location
        current_latitude = location.getLatitude();
        current_longitude = location.getLongitude();
        LoadAddress(current_latitude + "," + current_longitude);

        pickLocationShow(true);

        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(current_latitude, current_longitude)));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));

        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }

    }

    private void pickLocationShow(boolean show) {
        if (show)
            pickLocation.setVisibility(View.VISIBLE);
        else
            pickLocation.setVisibility(View.GONE);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted. Do the
                    // contacts-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other permissions this app might request.
            // You can add here other case statements according to your requirement.
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


    @Override
    public void onCancelOrder() {
        RemoveBottomFragment();
    }


    private void ShowMenuHome() {

        if (isHidden) {
            btnMenuHome.setImageDrawable(
                    new IconDrawable(this, MaterialIcons.md_close)
                            .colorRes(R.color.black_424242)
                            .actionBarSize());
            viewToolbar.setVisibility(View.GONE);
            CircularAnim.show(layoutMenuHome).duration(300).triggerView(btnMenuHome).go();
            isHidden = false;

        } else {
            btnMenuHome.setImageDrawable(
                    new IconDrawable(this, MaterialIcons.md_menu)
                            .colorRes(R.color.black_424242)
                            .actionBarSize());
            CircularAnim.hide(layoutMenuHome).duration(300).triggerView(btnMenuHome).go(new CircularAnim.OnAnimationEndListener() {
                @Override
                public void onAnimationEnd() {
                    viewToolbar.setVisibility(View.VISIBLE);
                }
            });
            isHidden = true;

        }
    }

    private void HideKeboard() {

        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                PrepareOrder prepareOrder = (PrepareOrder) data.getParcelableExtra(Sample.PREPARE_ORDER_OBJECT);
                if (prepareOrder != null) {

                    LoadWasherOrderFragment();
                }

                Vehicle vehicle = (Vehicle) data.getParcelableExtra(Sample.VEHICLE_OBJECT);
                if (vehicle != null) {
                    PrepareOrderFragment fragment = (PrepareOrderFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_bottom);
                    if (fragment != null) {
                        fragment.setSelectedVehicle(vehicle);
                    }
                }
            }

            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);
        acSearch = menu.findItem(R.id.action_search);
        acSearch.setIcon(
                new IconDrawable(this, MaterialIcons.md_search)
                        .colorRes(R.color.black_424242)
                        .actionBarSize());
        acSearch.setVisible(false);
        ShowHideAcSearch();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.action_search:
                HideKeboard();
                search.setText(null);
                startActivity(new Intent(this, SelectLocationActivity.class));
                break;

            default:
                break;
        }

        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    PermissionListener permissionMapsListener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {

            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapView = mapFragment.getView();
            mapFragment.getMapAsync(HomeActivity.this);
        }

        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {

            String message = String.format(Locale.getDefault(), getString(R.string.message_denied), "ACCESS_FINE_LOCATION");
            Toast.makeText(HomeActivity.this, message, Toast.LENGTH_SHORT).show();
        }


    };

    public void LoadAddress(String LatLong) {
        mService.getAddress(LatLong).enqueue(new Callback<AddressFromMapsResponse>() {
            @Override
            public void onResponse(Call<AddressFromMapsResponse> call, Response<AddressFromMapsResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body().getStatus().equalsIgnoreCase("OK")) {
                        List<Address> address = response.body().getResults();
                        search.setText(address.get(0).getFormattedAddress());
                    }

                    Log.d("MainActivity", "posts loaded from API");

                } else {
                    int statusCode = response.code();
                    // handle request errors depending on status code
                    Log.d("MainActivity", "error loading from API, status: " + statusCode);
                }
            }

            @Override
            public void onFailure(Call<AddressFromMapsResponse> call, Throwable t) {
                showErrorMessage();
                Log.d("MainActivity", "error loading from API");

            }


        });
    }

    private void showErrorMessage() {

    }

}
