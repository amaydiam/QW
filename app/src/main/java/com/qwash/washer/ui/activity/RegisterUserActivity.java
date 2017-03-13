package com.qwash.washer.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.EntypoModule;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.joanzapata.iconify.fonts.MaterialCommunityModule;
import com.joanzapata.iconify.fonts.MaterialModule;
import com.joanzapata.iconify.fonts.SimpleLineIconsModule;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.qwash.washer.R;
import com.qwash.washer.Sample;
import com.qwash.washer.api.ApiUtils;
import com.qwash.washer.api.client.register.RegisterService;
import com.qwash.washer.model.register.Register;
import com.qwash.washer.ui.widget.RobotoRegularButton;
import com.qwash.washer.ui.widget.RobotoRegularEditText;
import com.qwash.washer.utils.Prefs;
import com.qwash.washer.utils.ProgressDialogBuilder;

import net.soulwolf.widget.materialradio.MaterialRadioButton;
import net.soulwolf.widget.materialradio.MaterialRadioGroup;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import agency.tango.android.avatarview.views.AvatarView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterUserActivity extends AppCompatActivity {

    private static final String TAG = "RegisterUserActivity";

    @NotEmpty
    @Length(min = 2, max = 30, trim = true, messageResId = R.string.val_full_name)
    @BindView(R.id.full_name)
    RobotoRegularEditText fullName;

    @NotEmpty
    @Length(min = 16, max = 16, trim = true, messageResId = R.string.val_ktp_length)
    @BindView(R.id.ktp)
    RobotoRegularEditText ktp;

    @BindView(R.id.washer_photo)
    AvatarView washerPhoto;

    @NotEmpty
    @Length(min = 1, max = 15, trim = true, messageResId = R.string.val_telp_length)
    @BindView(R.id.no_telp)
    RobotoRegularEditText noTelp;

    @NotEmpty
    @BindView(R.id.birthday)
    RobotoRegularEditText birthday;

    @NotEmpty
    @Length(min = 5, max = 100, trim = true, messageResId = R.string.val_email_length)
    @Email
    @BindView(R.id.email)
    RobotoRegularEditText email;

    @NotEmpty
    @Length(min = 5, max = 100, trim = true, messageResId = R.string.val_password_length)
    @BindView(R.id.password)
    RobotoRegularEditText password;

    @NotEmpty
    @Length(min = 1, max = 100, trim = true, messageResId = R.string.val_province_length)
    @BindView(R.id.province)
    RobotoRegularEditText province;

    @NotEmpty
    @Length(min = 1, max = 100, trim = true, messageResId = R.string.val_city_length)
    @BindView(R.id.city)
    RobotoRegularEditText city;
    @BindView(R.id.gender)
    MaterialRadioGroup gender;
    @BindView(R.id.male)
    MaterialRadioButton male;
    @BindView(R.id.female)
    MaterialRadioButton female;
    @BindView(R.id.btn_new_account)
    RobotoRegularButton btnContinue;

    private ProgressDialogBuilder dialogProgress;
    private Context context;
    private Validator validator;


    @OnClick(R.id.birthday)
    void BirthDate() {

    }

    @OnClick(R.id.btn_new_account)
    void Lock() {
        validator.validate();
    }

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
        context = getApplicationContext();
        dialogProgress = new ProgressDialogBuilder(this);

    }

    private void setValidator() {
        validator = new Validator(this);
        validator.setValidationListener(new Validator.ValidationListener() {
            @Override
            public void onValidationSucceeded() {
                remoteRegister();
            }

            @Override
            public void onValidationFailed(List<ValidationError> errors) {
                registerFailed(errors);
            }
        });
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


    private void remoteRegister() {
        dialogProgress.show("Register...", "Please wait...");

        String firebase_id = FirebaseInstanceId.getInstance().getToken();
        Map<String, String> params = new HashMap<>();
        params.put(Sample.AUTH_LEVEL, String.valueOf(10));
        params.put(Sample.FIREBASE_ID, firebase_id);


        params.put(Sample.NAME, fullName.getText().toString());
        params.put(Sample.NIK, ktp.getText().toString());
        params.put(Sample.PHONE, noTelp.getText().toString());
        params.put(Sample.BIRTHDATE, birthday.getText().toString());
        params.put(Sample.GENDER, male.isSelected() ? "Male" : "Female");
        params.put(Sample.EMAIL, email.getText().toString());
        params.put(Sample.PASSWORD, password.getText().toString());
        params.put(Sample.PROVINCE, province.getText().toString());
        params.put(Sample.CITY, city.getText().toString());

        RegisterService mService = ApiUtils.RegisterService(this);
        mService.getRegisterLink(params).enqueue(new Callback<Register>() {
            @Override
            public void onResponse(Call<Register> call, Response<Register> response) {
                dialogProgress.hide();
                if (response.isSuccessful()) {
                    if (response.body().getStatus()) {

                        Prefs.putLockMapRegister(context, true);
                        toLockRegitrasiActivity();

                    }
                } else {
                    int statusCode = response.code();
                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        String message = jsonObject.getString(Sample.MESSAGE);
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        password.setText("");
                    } catch (JSONException | IOException e) {
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Register> call, Throwable t) {
                String message = t.getMessage();
                dialogProgress.hide();
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void toLockRegitrasiActivity() {

        Intent intent = new Intent(this, LockWasherActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

}
