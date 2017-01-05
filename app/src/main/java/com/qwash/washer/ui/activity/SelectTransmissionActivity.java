package com.ad.sample.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.ad.sample.R;
import com.ad.sample.adapter.RecyclerAdapter3;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.MaterialIcons;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by binderbyte on 24/12/16.
 */

public class SelectTransmissionActivity extends AppCompatActivity {
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view_select_transmission)
    RecyclerView recyclerViewSelectTransmission;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_transmission_user);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(
                new IconDrawable(this, MaterialIcons.md_arrow_back)
                        .colorRes(R.color.black_424242)
                        .actionBarSize());
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getSupportActionBar().setTitle("");
        toolbarTitle.setText(getResources().getString(R.string.select_transmission));

        RecyclerAdapter3 adapter = new RecyclerAdapter3(this);
        recyclerViewSelectTransmission.setAdapter(adapter);
        recyclerViewSelectTransmission.setHasFixedSize(true);

        //Layout manager for Recycler view
        recyclerViewSelectTransmission.setLayoutManager(new LinearLayoutManager(this));

    }
}
