package com.qwash.washer.ui.activity;

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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.EntypoModule;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.joanzapata.iconify.fonts.MaterialCommunityIcons;
import com.joanzapata.iconify.fonts.MaterialCommunityModule;
import com.joanzapata.iconify.fonts.MaterialIcons;
import com.joanzapata.iconify.fonts.MaterialModule;
import com.joanzapata.iconify.fonts.SimpleLineIconsModule;
import com.qwash.washer.R;
import com.qwash.washer.Sample;
import com.qwash.washer.ui.fragment.ProfilFragment;
import com.qwash.washer.ui.fragment.WalletFragment;
import com.qwash.washer.ui.widget.RobotoRegularTextView;
import com.qwash.washer.utils.Menus;

import agency.tango.android.avatarview.loader.PicassoLoader;
import agency.tango.android.avatarview.views.AvatarView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ProfilFragment.OnProfilFragmentInteractionListener,WalletFragment.OnWalletInteractionListener {

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
            return true;
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
        } else if (id == Menus.nav_top_up) {
            setSelectedDrawerItem(id);
        } else if (id == Menus.nav_wash_history) {
            setSelectedDrawerItem(id);
        } else if (id == Menus.nav_feedback_customer) {
            setSelectedDrawerItem(id);
        } else if (id == Menus.nav_pusat_bantuan) {
            setSelectedDrawerItem(id);
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    public void setSelectedDrawerItem(int id) {
        MenuItem item = navView.getMenu().findItem(id);
        item.setChecked(true);
        toolbar.setSubtitle(item.getTitle());
        Bundle args = new Bundle();
        switch (id) {
            case Menus.nav_profil:
                fragment = new ProfilFragment();
                break;
            case Menus.nav_wallet:
                fragment = new WalletFragment();
                break;
            case Menus.nav_top_up:
                fragment = new WalletFragment();
                break;
            case Menus.nav_wash_history:
                fragment = new WalletFragment();
                break;
            case Menus.nav_feedback_customer:
                fragment = new WalletFragment();
                break;
            case Menus.nav_pusat_bantuan:
                fragment = new WalletFragment();
                break;
            default:
                fragment = new ProfilFragment();
                break;
        }

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
        nav_profil.setIcon(new IconDrawable(this, MaterialCommunityIcons.mdi_calendar).actionBarSize());
        MenuItem nav_wallet = menu.findItem(R.id.nav_wallet);
        nav_wallet.setIcon(new IconDrawable(this, MaterialCommunityIcons.mdi_qrcode_scan).actionBarSize());
        MenuItem nav_top_up = menu.findItem(R.id.nav_top_up);
        nav_top_up.setIcon(new IconDrawable(this, MaterialCommunityIcons.mdi_comment_question_outline).actionBarSize());
        MenuItem nav_wash_history = menu.findItem(R.id.nav_wash_history);
        nav_wash_history.setIcon(new IconDrawable(this, MaterialCommunityIcons.mdi_map_marker).actionBarSize());
        MenuItem nav_feedback_customer = menu.findItem(R.id.nav_feedback_customer);
        nav_feedback_customer.setIcon(new IconDrawable(this, MaterialIcons.md_people).actionBarSize());
        MenuItem nav_pusat_bantuan = menu.findItem(R.id.nav_pusat_bantuan);
        nav_pusat_bantuan.setIcon(new IconDrawable(this, MaterialIcons.md_accessibility).actionBarSize());
               
    }

    public void SetDataUser() {

      //  washer_name.setText(Prefs.getNamaLengkap(this));
        PicassoLoader imageLoader = new PicassoLoader();
      //  imageLoader.loadImage(washer_photo, url, Prefs.getNamaLengkap(this));
        imageLoader.loadImage(washer_photo, "", "Fachri");

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
