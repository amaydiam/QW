package com.ad.sample.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.ad.sample.R;
import com.ad.sample.ui.widget.RobotoRegularButton;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.EntypoIcons;
import com.joanzapata.iconify.fonts.EntypoModule;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.joanzapata.iconify.fonts.MaterialCommunityModule;
import com.joanzapata.iconify.fonts.MaterialModule;
import com.joanzapata.iconify.fonts.SimpleLineIconsModule;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginUserActivity extends AppCompatActivity {

    @BindView(R.id.btn_login)
    RobotoRegularButton btnLogin;
    @BindView(R.id.btn_facebook)
    FloatingActionButton btnFacebook;
    @BindView(R.id.btn_google_plus)
    FloatingActionButton btnGooglePlus;
    @BindView(R.id.register)
    TextView register;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Iconify
                .with(new FontAwesomeModule())
                .with(new EntypoModule());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_user);
        ButterKnife.bind(this);

        setIcon();

    }

    @OnClick(R.id.btn_login)
    void LoginEmail() {
       // Toast.makeText(this, "Test", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this,HomeActivity.class));
    }

    @OnClick(R.id.btn_facebook)
    void LoginFacebook() {
        Toast.makeText(this, "Facebook", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.btn_google_plus)
    void LoginGooglePlus() {
        Toast.makeText(this, "Gplus", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.register)
    void Register() {
        startActivity(new Intent(LoginUserActivity.this, RegisterUserActivity.class));
    }

    private void setIcon(){
        btnFacebook.setImageDrawable(new IconDrawable(this, FontAwesomeIcons.fa_facebook)
                .colorRes(R.color.blue_2196F3)
                .actionBarSize());

        btnGooglePlus.setImageDrawable(new IconDrawable(this, EntypoIcons.entypo_google)
                .colorRes(R.color.blue_2196F3)
                .actionBarSize());
    }

}