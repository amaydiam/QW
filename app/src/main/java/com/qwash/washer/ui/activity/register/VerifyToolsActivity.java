package com.qwash.washer.ui.activity.register;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.qwash.washer.R;
import com.qwash.washer.Sample;
import com.qwash.washer.adapter.KategoriPostAdapter;
import com.qwash.washer.model.register.VerifyTools;
import com.qwash.washer.ui.activity.BaseActivity;
import com.qwash.washer.ui.widget.RobotoRegularButton;
import com.qwash.washer.utils.Prefs;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VerifyToolsActivity extends BaseActivity implements KategoriPostAdapter.OnKategoriPostItemClickListener {


    KategoriPostAdapter adapter;
    ArrayList<VerifyTools> list_data = new ArrayList<>();
    @BindView(R.id.title_toolbar)
    TextView titleToolbar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.grid_view)
    RecyclerView gridView;
    @BindView(R.id.btn_continue)
    RobotoRegularButton btnContinue;
    @BindView(R.id.scroll)
    NestedScrollView scroll;
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

    @OnClick(R.id.btn_continue)
    public void btnContinue() {
        Prefs.putActivityIndex(this, Sample.LOCK_MAP_AFTER_REGISTER_INDEX);
        Intent intent = new Intent(VerifyToolsActivity.this, LockWasherActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_tools);
        ButterKnife.bind(this);

        setInit();
    }


    private void setInit() {

        if (toolbar != null) {
            setSupportActionBar(toolbar);
         /*   ActionBar actionbar = getSupportActionBar();
            if (actionbar != null) {
                actionbar.setDisplayHomeAsUpEnabled(true);
                actionbar.setHomeButtonEnabled(true);
                actionbar.setTitle(getString(R.string.verify_tools));
            }*/
        }

        int[] rs = new int[]{
                R.drawable.placeholder,
                R.drawable.placeholder,
                R.drawable.placeholder,
                R.drawable.placeholder,
                R.drawable.placeholder,
                R.drawable.placeholder,
                R.drawable.placeholder,
                R.drawable.placeholder,
        };
        String[] des = new String[]{
                "CAR SHAMPO",
                "WATERCANON HOSE",
                "MICROFIBER",
                "PLAS CHAMOIS",
                "TIRE POLISH",
                "BRUSH",
                "WATERLESS",
                "CAR VACUUM",
        };

        String[] price = new String[]{
                "IDR 20.000 / Liter",
                "IDR 85.000",
                "IDR 15.000",
                "IDR 10.000",
                "IDR 13.000",
                "IDR 5.000",
                "IDR 30.000 / Liter",
                "IDR 50.000"
        };

        for (int i = 0; i < rs.length; i++) {
            VerifyTools verifyTools = new VerifyTools(rs[i], des[i], price[i]);
            list_data.add(verifyTools);
        }

        adapter = new KategoriPostAdapter(VerifyToolsActivity.this, list_data);
        adapter.setOnKategoriPostItemClickListener(this);
        gridView.setNestedScrollingEnabled(false);
        gridView.setHasFixedSize(true);
        gridView.setLayoutManager(new GridLayoutManager(this, 2));
        gridView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

  /*  private void setList(String response) {
        HashMap<String, String> map = new HashMap<>();
        map.put(Variabel.idTopik, idTopik);
        map.put(Variabel.namaTopik, namaTopik);
        map.put(Variabel.icon, icon);
        list_data.add(map);
        adapter.notifyDataSetChanged();
    }*/

    @Override
    public void onRootClick(View v, int position) {
        //adapter.setSelected(position);
    }

    @Override
    public void onBackPressed() {
        backActivity();
    }

    private void backActivity() {
        // super.onBackPressed();
        finish();
        overridePendingTransition(0, 0);

    }

}
