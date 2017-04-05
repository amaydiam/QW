package com.qwash.washer.ui.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.qwash.washer.utils.LanguageHelper;

/**
 * Created by Amay on 4/5/2017.
 */

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LanguageHelper.onAttach(base));
    }
}
