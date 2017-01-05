package com.ad.sample.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.ad.sample.R;
import com.ad.sample.Sample;
import com.ad.sample.ui.fragment.HistoryDetailFragment;

public class HistoryDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_detail);

        if (savedInstanceState == null) {
            Intent intent = getIntent();
            String mustahiqId = intent.getStringExtra(Sample.HISTORY_ID);
            loadHistoryDetailsOf(mustahiqId);
        }
    }

    private void loadHistoryDetailsOf(String mustahiqId) {
        HistoryDetailFragment fragment = new HistoryDetailFragment();
        Bundle args = new Bundle();
        args.putString(Sample.HISTORY_ID, mustahiqId);
        fragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().replace(R.id.content_detail_container, fragment).commit();
    }


}
