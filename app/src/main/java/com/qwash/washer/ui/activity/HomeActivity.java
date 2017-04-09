package com.qwash.washer.ui.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.EntypoIcons;
import com.joanzapata.iconify.fonts.EntypoModule;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.joanzapata.iconify.fonts.MaterialCommunityIcons;
import com.joanzapata.iconify.fonts.MaterialCommunityModule;
import com.joanzapata.iconify.fonts.MaterialModule;
import com.joanzapata.iconify.fonts.SimpleLineIconsModule;
import com.qwash.washer.BuildConfig;
import com.qwash.washer.R;
import com.qwash.washer.Sample;
import com.qwash.washer.service.MessageFireBase;
import com.qwash.washer.ui.fragment.FeedbackFragment;
import com.qwash.washer.ui.fragment.HomeFragment;
import com.qwash.washer.ui.fragment.ProfilFragment;
import com.qwash.washer.ui.fragment.WalletFragment;
import com.qwash.washer.ui.fragment.WashHistoryFragment;
import com.qwash.washer.ui.widget.RobotoRegularTextView;
import com.qwash.washer.utils.LanguageHelper;
import com.qwash.washer.utils.Menus;
import com.qwash.washer.utils.Prefs;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import agency.tango.android.avatarview.loader.PicassoLoader;
import agency.tango.android.avatarview.utils.StringUtils;
import agency.tango.android.avatarview.views.AvatarView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class HomeActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    private Fragment fragment;

    private AvatarView washer_photo;
    private RobotoRegularTextView washer_name;
    private MenuItem prevMenuItem;

    @OnClick(R.id.fab)
    void clickFab(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
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
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        setSupportActionBar(toolbar);

        fab.setVisibility(View.GONE);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navView.setNavigationItemSelectedListener(this);
        SetMenuDrawer();
        SetDataUser();
        if (bundle != null) {
            if (bundle.getInt(Sample.ACTION) == Sample.ACTION_OPEN_FEED_ORDER) {
                setSelectedDrawerItem(Menus.nav_feedback_customer);
                return;
            }
        }

        setSelectedDrawerItem(Menus.nav_profil);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == Menus.nav_home) {
            setSelectedDrawerItem(id);
        } else if (id == Menus.nav_profil) {
            setSelectedDrawerItem(id);
        } else if (id == Menus.nav_wallet) {
            setSelectedDrawerItem(id);
        } else if (id == Menus.nav_wash_history) {
            setSelectedDrawerItem(id);
        } else if (id == Menus.nav_feedback_customer) {
            setSelectedDrawerItem(id);
        } else if (id == Menus.nav_pusat_bantuan) {
            setSelectedDrawerItem(id);
        } else if (id == Menus.nav_info) {
            setSelectedDrawerItem(id);
        } else if (id == Menus.nav_logout) {
            setSelectedDrawerItem(id);
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    public void setSelectedDrawerItem(int id) {
        switch (id) {
            case Menus.nav_home:
                fragment = new HomeFragment();
                setFragment(id, fragment);
                break;
            case Menus.nav_profil:
                fragment = new ProfilFragment();
                setFragment(id, fragment);
                break;
            case Menus.nav_wallet:
                fragment = new WalletFragment();
                setFragment(id, fragment);
                break;
            case Menus.nav_wash_history:
                fragment = new WashHistoryFragment();
                setFragment(id, fragment);
                break;
            case Menus.nav_feedback_customer:
                fragment = new FeedbackFragment();
                setFragment(id, fragment);
                break;
            case Menus.nav_pusat_bantuan:
                Uri uriUrl = Uri.parse(Sample.URL_PUSAT_BANTUAN_WASHER);
                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                startActivity(launchBrowser);
                break;
            case Menus.nav_info:
                ShowAbout();
                checkItem();
                break;
            case Menus.nav_logout:
                Logout();
                checkItem();
                break;
            default:
                fragment = new HomeFragment();
                setFragment(id, fragment);
                break;
        }

    }


    private void setFragment(int id, Fragment fragment) {

        if (prevMenuItem != null) {
            prevMenuItem.setChecked(false);
        }
        MenuItem item = navView.getMenu().findItem(id);
        prevMenuItem = item;

        checkItem();

        Bundle args = new Bundle();
        fragment.setArguments(args);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, fragment, Sample.TAG_FRAGMENT);
        transaction.commitAllowingStateLoss();
    }

    private void checkItem() {
        if (prevMenuItem != null) {
            prevMenuItem.setChecked(true);
            toolbar.setTitle(prevMenuItem.getTitle());
        }
    }


    private void SetMenuDrawer() {

        // ============ header menu drawer ==============
        View header = navView.getHeaderView(0);
        washer_photo = (AvatarView) header.findViewById(R.id.washer_photo);
        washer_name = (RobotoRegularTextView) header.findViewById(R.id.washer_name);


        // ============ list menu drawer ==============
        Menu menu = navView.getMenu();
        MenuItem nav_home = menu.findItem(R.id.nav_home);
        nav_home.setIcon(new IconDrawable(this, EntypoIcons.entypo_home).actionBarSize());
        MenuItem nav_profil = menu.findItem(R.id.nav_profil);
        nav_profil.setIcon(new IconDrawable(this, EntypoIcons.entypo_user).actionBarSize());
        MenuItem nav_wallet = menu.findItem(R.id.nav_wallet);
        nav_wallet.setIcon(new IconDrawable(this, EntypoIcons.entypo_wallet).actionBarSize());
        MenuItem nav_wash_history = menu.findItem(R.id.nav_wash_history);
        nav_wash_history.setIcon(new IconDrawable(this, MaterialCommunityIcons.mdi_history).actionBarSize());
        MenuItem nav_feedback_customer = menu.findItem(R.id.nav_feedback_customer);
        nav_feedback_customer.setIcon(new IconDrawable(this, MaterialCommunityIcons.mdi_comment_alert).actionBarSize());
        MenuItem nav_pusat_bantuan = menu.findItem(R.id.nav_pusat_bantuan);
        nav_pusat_bantuan.setIcon(new IconDrawable(this, FontAwesomeIcons.fa_question_circle).actionBarSize());
        MenuItem nav_info = menu.findItem(R.id.nav_info);
        nav_info.setIcon(new IconDrawable(this, MaterialCommunityIcons.mdi_alert_circle).actionBarSize());
        MenuItem nav_logout = menu.findItem(R.id.nav_logout);
        nav_logout.setIcon(new IconDrawable(this, MaterialCommunityIcons.mdi_logout).actionBarSize());

    }

    public void SetDataUser() {

        PicassoLoader imageLoader = new PicassoLoader();
        imageLoader.loadImage(washer_photo, Prefs.getProfilePhoto(this), Prefs.getFullName(this));
        washer_name.setText(Prefs.getFullName(this));

        Log.v("avatar",Prefs.getProfilePhoto(this));

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
        SetMenuDrawer();
        SetDataUser();
        if (getIntent().getAction() != null) {
            String action = getIntent().getAction();
            if (action.equalsIgnoreCase(Sample.ACTION_FROM_NOTIFICATION)) {
                setSelectedDrawerItem(Menus.nav_home);
                HomeFragment fragment = (HomeFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame);
                if (fragment != null) {
                    fragment.setCheckedButtonStatus();
                }
            }
        }

        checkItem();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageFireBase(MessageFireBase MessageFireBase) {
        try {
            JSONObject json = new JSONObject(MessageFireBase.getData());
            int action = json.getInt(Sample.ACTION);
            if (action == Sample.ACTION_OPEN_FEED_ORDER) {
                setSelectedDrawerItem(Menus.nav_feedback_customer);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }


}
