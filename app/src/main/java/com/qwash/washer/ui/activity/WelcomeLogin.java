package com.qwash.washer.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.qwash.washer.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by binderbyte on 06/01/17.
 */

public class WelcomeLogin extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.sign_in_washer)
    public void signIn(View view) {
        startActivity(new Intent(this, LoginUserActivity.class));
    }

    @OnClick(R.id.create_account)
    public void createAccount(View view) {
        startActivity(new Intent(this, RegisterUserActivity.class));
    }
}
