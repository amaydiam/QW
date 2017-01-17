package com.qwash.washer.ui.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import com.qwash.washer.R;
import com.qwash.washer.Sample;
import com.qwash.washer.ui.fragment.FeedbackFragment;
import com.qwash.washer.ui.fragment.ProfilFragment;
import com.qwash.washer.ui.fragment.WalletFragment;
import com.qwash.washer.ui.fragment.WashHistoryFragment;
import com.qwash.washer.ui.widget.RobotoRegularTextView;
import com.qwash.washer.utils.Menus;
import com.qwash.washer.utils.Prefs;

import agency.tango.android.avatarview.loader.PicassoLoader;
import agency.tango.android.avatarview.views.AvatarView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ProfilFragment.OnProfilFragmentInteractionListener, WalletFragment.OnWalletInteractionListener {

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
        setSupportActionBar(toolbar);

        fab.setVisibility(View.GONE);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navView.setNavigationItemSelectedListener(this);
        SetMenuDrawer();
        SetDataUser();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {

                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setMessage("Are you sure, you wanted to logout?");
                alertDialogBuilder.setPositiveButton("yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                // Logout email
                                Prefs.Reset(HomeActivity.this);
                                // Google sign out
                                FirebaseAuth.getInstance().signOut();

                                Intent intent = new Intent(HomeActivity.this, LoginUserActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            }
                        });

                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == Menus.nav_profil) {
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
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    public void setSelectedDrawerItem(int id) {
        switch (id) {
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

                break;
            case Menus.nav_info:

                break;
            default:
                fragment = new ProfilFragment();
                setFragment(id, fragment);
                break;
        }

    }

    private void setFragment(int id, Fragment fragment) {
        Bundle args = new Bundle();
        MenuItem item = navView.getMenu().findItem(id);
        item.setChecked(true);
        toolbar.setTitle(item.getTitle());
        fragment.setArguments(args);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, fragment, Sample.TAG_FRAGMENT);
        transaction.commitAllowingStateLoss();
    }

    private void SetMenuDrawer() {

        // ============ header menu drawer ==============
        View header = navView.getHeaderView(0);
        washer_photo = (AvatarView) header.findViewById(R.id.washer_photo);
        washer_name = (RobotoRegularTextView) header.findViewById(R.id.washer_name);

        // ============ list menu drawer ==============
        Menu menu = navView.getMenu();
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

    }

    public void SetDataUser() {

        //  washer_name.setText(Prefs.getNamaLengkap(this));
        PicassoLoader imageLoader = new PicassoLoader();
        //  imageLoader.loadImage(washer_photo, url, Prefs.getNamaLengkap(this));
        imageLoader.loadImage(washer_photo, "ht", "Fachri");

    }

    @Override
    public void onProfilFragmentInteraction() {

    }

    @Override
    public void OnWalletInteraction(Uri uri) {

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
    }
}
