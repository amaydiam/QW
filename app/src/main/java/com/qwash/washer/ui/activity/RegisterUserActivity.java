package com.qwash.washer.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.EntypoModule;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.joanzapata.iconify.fonts.MaterialCommunityModule;
import com.joanzapata.iconify.fonts.MaterialIcons;
import com.joanzapata.iconify.fonts.MaterialModule;
import com.joanzapata.iconify.fonts.SimpleLineIconsModule;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.qwash.washer.R;
import com.qwash.washer.ui.widget.RobotoRegularButton;
import com.qwash.washer.ui.widget.RobotoRegularEditText;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterUserActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @NotEmpty
    @Length(min = 2, max = 30, trim = true, messageResId = R.string.val_first_length)
    @BindView(R.id.first_name)
    RobotoRegularEditText firstName;

    @NotEmpty
    @Length(min = 2, max = 30, trim = true, messageResId = R.string.val_last_length)
    @BindView(R.id.last_name)
    RobotoRegularEditText lastName;

    @NotEmpty
    @Length(min = 5, max = 100, trim = true, messageResId = R.string.val_email_length)
    @Email
    @BindView(R.id.email)
    RobotoRegularEditText email;

    @NotEmpty
    @Length(min = 1, max = 15, trim = true, messageResId = R.string.val_telp_length)
    @BindView(R.id.no_telp)
    RobotoRegularEditText noTelp;

    @NotEmpty
    @Length(min = 5, max = 100, trim = true, messageResId = R.string.val_password_length)
    @BindView(R.id.password)
    RobotoRegularEditText password;

    @NotEmpty
    @Length(min = 1, max = 100, trim = true, messageResId = R.string.val_city_length)
    @BindView(R.id.city)
    RobotoRegularEditText city;

    @OnClick(R.id.btn_continue)
    void Lock() {
        validator.validate();
    }

    @BindView(R.id.pick_code_country)
    ImageView pickCodeCountry;

    private Validator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_user);
        ButterKnife.bind(this);
        setValidator();

        Iconify
                .with(new FontAwesomeModule())
                .with(new EntypoModule())
                .with(new MaterialModule())
                .with(new MaterialCommunityModule())
                .with(new SimpleLineIconsModule());

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(
                new IconDrawable(this, MaterialIcons.md_arrow_back)
                        .colorRes(R.color.black_424242)
                        .actionBarSize());
        toolbar.setTitleTextColor(Color.GRAY);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getSupportActionBar().setTitle("Create Account");

    }

    private void setValidator() {
        validator = new Validator(this);
        validator.setValidationListener(new Validator.ValidationListener() {
            @Override
            public void onValidationSucceeded() {
                registerSucces();
            }

            @Override
            public void onValidationFailed(List<ValidationError> errors) {
                registerFailed(errors);
            }
        });
    }

    private void registerSucces() {
        startActivity(new Intent(this, LockWasherActivity.class));
    }

    private void registerFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(getApplicationContext());

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }

}
