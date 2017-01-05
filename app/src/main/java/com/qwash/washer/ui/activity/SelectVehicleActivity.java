package com.qwash.washer.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.qwash.washer.R;
import com.qwash.washer.Sample;
import com.qwash.washer.adapter.VehicleAdapter;
import com.qwash.washer.model.PrepareOrder;
import com.qwash.washer.model.Vehicle;
import com.qwash.washer.ui.fragment.PrepareOrderFragment;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.MaterialCommunityIcons;
import com.joanzapata.iconify.fonts.MaterialIcons;

import java.util.ArrayList;

import butterknife.BindBool;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by aan on 12/21/16.
 */

public class SelectVehicleActivity extends AppCompatActivity implements VehicleAdapter.OnVehicleItemClickListener {


    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    @BindBool(R.bool.is_tablet)
    boolean isTablet;


    @OnClick(R.id.btn_add_vehicle)
    public void onClickAddVehicle() {
        //new activity
        Intent intent = new Intent(SelectVehicleActivity.this, AddVehicleActivity.class);
        startActivityForResult(intent, 1);

    }

    private ArrayList<Vehicle> data = new ArrayList<>();
    private VehicleAdapter adapter;
    private MenuItem acOk;
    private Vehicle vehicle = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_vehicle_user);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        vehicle = (Vehicle) intent.getParcelableExtra(Sample.VEHICLE_OBJECT);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(
                new IconDrawable(this, MaterialIcons.md_arrow_back)
                        .colorRes(R.color.black_424242)
                        .actionBarSize());
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_CANCELED, returnIntent);
                finish();
            }
        });
        getSupportActionBar().setTitle("");
        toolbarTitle.setText(getResources().getString(R.string.select_vehicle));

        //inisial adapter
        adapter = new VehicleAdapter(this, data);
        adapter.setOnVehicleItemClickListener(this);

        //recyclerView
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, getNumberOfColumns()));

        // set adapter
        recyclerView.setAdapter(adapter);

        setDummyData();

    }

    private void setDummyData() {
        Vehicle v1 = new Vehicle("2", 2, "Honda", "Vario", "Matic", "2010");
        data.add(0, v1);
        adapter.notifyDataSetChanged();
        Vehicle v2 = new Vehicle("1", 1, "Honda", "Jazz", "Matic", "2009");
        data.add(0, v2);
        adapter.notifyDataSetChanged();
        if (vehicle != null)
            adapter.setSelectionByIdVehicle(vehicle.id_vehicle);
        else
            adapter.setSelection(0);

    }


    public int getNumberOfColumns() {
        // Get screen width
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float widthPx = displayMetrics.widthPixels;
        if (isTablet) {
            widthPx = widthPx / 3;
        }
        // Calculate desired width

        float desiredPx = getResources().getDimensionPixelSize(R.dimen.list_card_width);
        int columns = Math.round(widthPx / desiredPx);
        return columns > 1 ? columns : 2;
    }


    @Override
    public void onActionClick(View v, int position) {

    }

    @Override
    public void onRootClick(View v, int position) {

        adapter.setSelection(position);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.ok_menu, menu);
        acOk = menu.findItem(R.id.action_ok);
        acOk.setIcon(
                new IconDrawable(this, MaterialCommunityIcons.mdi_check)
                        .colorRes(R.color.black_424242)
                        .actionBarSize());
        acOk.setVisible(false);
        ShowHideAcOk();
        return true;
    }

    private void ShowHideAcOk() {
        try {

            if (adapter.getSelection() == -1) {
                acOk.setVisible(false);
            } else {
                acOk.setVisible(true);
            }
            supportInvalidateOptionsMenu();
        } catch (Exception e) {

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.action_ok:

                Intent intent = new Intent();
                intent.putExtra(Sample.VEHICLE_OBJECT, adapter.data.get(adapter.getSelection()));
                setResult(Activity.RESULT_OK, intent);
                finish();

                break;

            default:
                break;
        }

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {

                Vehicle vehicle = (Vehicle) data.getParcelableExtra(Sample.VEHICLE_OBJECT);
                if (vehicle != null) {
                    this.data.add(vehicle);
                    adapter.notifyDataSetChanged();
                }
            }

            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }
}
