package com.qwash.washer.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.MaterialIcons;
import com.qwash.washer.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by binderbyte on 09/01/17.
 */

public class TopUpActivity extends BaseActivity {

    @BindView(R.id.checkbox20)
    RadioButton checkbox20;

    @BindView(R.id.checkbox100)
    RadioButton checkbox100;

    @BindView(R.id.checkbox500)
    RadioButton checkbox500;

    @BindView(R.id.checkbox50)
    RadioButton checkbox50;

    @BindView(R.id.checkbox250)
    RadioButton checkbox250;

    @BindView(R.id.checkbox750)
    RadioButton checkbox750;

    @BindView(R.id.checkbox1jt)
    RadioButton checkbox1jt;

    @BindView(R.id.totalharga)
    TextView totalharga;
    @BindView(R.id.checkbox)
    CheckBox checkbox;
    @BindView(R.id.topup)
    Button topup;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title_toolbar)
    TextView toolbarTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topup);
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
        toolbarTitle.setText(getResources().getString(R.string.title_activity_topup));

    }

    @OnClick({R.id.checkbox20, R.id.checkbox100, R.id.checkbox500, R.id.checkbox50, R.id.checkbox250, R.id.checkbox750, R.id.checkbox1jt, R.id.topup})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.checkbox20:
                totalharga.setText(checkbox20.getText().toString());
                checkbox20.setChecked(true);
                checkbox50.setChecked(false);
                checkbox100.setChecked(false);
                checkbox250.setChecked(false);
                checkbox500.setChecked(false);
                checkbox750.setChecked(false);
                checkbox1jt.setChecked(false);
                break;
            case R.id.checkbox50:
                totalharga.setText(checkbox50.getText().toString());
                checkbox20.setChecked(false);
                checkbox50.setChecked(true);
                checkbox100.setChecked(false);
                checkbox250.setChecked(false);
                checkbox500.setChecked(false);
                checkbox750.setChecked(false);
                checkbox1jt.setChecked(false);
                break;
            case R.id.checkbox100:
                totalharga.setText(checkbox100.getText().toString());
                checkbox20.setChecked(false);
                checkbox50.setChecked(false);
                checkbox100.setChecked(true);
                checkbox250.setChecked(false);
                checkbox500.setChecked(false);
                checkbox750.setChecked(false);
                checkbox1jt.setChecked(false);
                break;
            case R.id.checkbox250:
                totalharga.setText(checkbox250.getText().toString());
                checkbox20.setChecked(false);
                checkbox50.setChecked(false);
                checkbox100.setChecked(false);
                checkbox250.setChecked(true);
                checkbox500.setChecked(false);
                checkbox750.setChecked(false);
                checkbox1jt.setChecked(false);
                break;
            case R.id.checkbox500:
                totalharga.setText(checkbox500.getText().toString());
                checkbox20.setChecked(false);
                checkbox50.setChecked(false);
                checkbox100.setChecked(false);
                checkbox250.setChecked(false);
                checkbox500.setChecked(true);
                checkbox750.setChecked(false);
                checkbox1jt.setChecked(false);
                break;
            case R.id.checkbox750:
                totalharga.setText(checkbox750.getText().toString());
                checkbox20.setChecked(false);
                checkbox50.setChecked(false);
                checkbox100.setChecked(false);
                checkbox250.setChecked(false);
                checkbox500.setChecked(false);
                checkbox750.setChecked(true);
                checkbox1jt.setChecked(false);
                break;
            case R.id.checkbox1jt:
                totalharga.setText(checkbox1jt.getText().toString());
                checkbox20.setChecked(false);
                checkbox50.setChecked(false);
                checkbox100.setChecked(false);
                checkbox250.setChecked(false);
                checkbox500.setChecked(false);
                checkbox750.setChecked(false);
                checkbox1jt.setChecked(true);
                break;
            case R.id.topup:

                if (checkbox.isChecked()) {
                    String jadi = totalharga.getText().toString();
                    Toast.makeText(this, "Output " + jadi, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Checkbox TOS", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

}
