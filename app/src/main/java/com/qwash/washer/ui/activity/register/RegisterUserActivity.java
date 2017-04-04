package com.qwash.washer.ui.activity.register;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.gun0912.tedpermission.PermissionListener;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.EntypoModule;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.joanzapata.iconify.fonts.MaterialCommunityModule;
import com.joanzapata.iconify.fonts.MaterialModule;
import com.joanzapata.iconify.fonts.SimpleLineIconsModule;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.qwash.washer.R;
import com.qwash.washer.Sample;
import com.qwash.washer.api.ApiUtils;
import com.qwash.washer.api.client.register.RegisterService;
import com.qwash.washer.api.model.washer.DataWasher;
import com.qwash.washer.api.model.register.Register;
import com.qwash.washer.ui.widget.RobotoRegularButton;
import com.qwash.washer.ui.widget.RobotoRegularEditText;
import com.qwash.washer.utils.Prefs;
import com.qwash.washer.utils.ProgressDialogBuilder;
import com.qwash.washer.utils.TextUtils;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import net.soulwolf.widget.materialradio.MaterialRadioButton;
import net.soulwolf.widget.materialradio.MaterialRadioGroup;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.aprilapps.easyphotopicker.EasyImage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterUserActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private static final String TAG = "RegisterUserActivity";

    @NotEmpty
    @Length(min = 2, max = 30, trim = true, messageResId = R.string.val_full_name)
    @BindView(R.id.full_name)
    RobotoRegularEditText fullName;

    @NotEmpty
    @Length(min = 16, max = 16, trim = true, messageResId = R.string.val_ktp_length)
    @BindView(R.id.ktp)
    RobotoRegularEditText ktp;

    @NotEmpty
    @Length(min = 1, max = 15, trim = true, messageResId = R.string.val_telp_length)
    @BindView(R.id.no_telp)
    RobotoRegularEditText noTelp;

    @NotEmpty
    @BindView(R.id.birthday)
    RobotoRegularEditText birthday;

    @NotEmpty
    @Length(min = 1, max = 100, trim = true, messageResId = R.string.val_province_length)
    @BindView(R.id.province)
    RobotoRegularEditText province;

    @NotEmpty
    @Length(min = 1, max = 100, trim = true, messageResId = R.string.val_city_length)
    @BindView(R.id.city)
    RobotoRegularEditText city;

    @NotEmpty
    @Length(min = 5, max = 100, trim = true, messageResId = R.string.val_email_length)
    @Email
    @BindView(R.id.email)
    RobotoRegularEditText email;

    @NotEmpty
    @Password(min = 5, messageResId = R.string.val_password_length)
    @BindView(R.id.password)
    RobotoRegularEditText password;

    @NotEmpty
    @ConfirmPassword()
    @BindView(R.id.confirm_password)
    RobotoRegularEditText confirmPassword;

    @BindView(R.id.gender)
    MaterialRadioGroup gender;
    @BindView(R.id.male)
    MaterialRadioButton male;
    @BindView(R.id.female)
    MaterialRadioButton female;
    @BindView(R.id.btn_new_account)
    RobotoRegularButton btnContinue;

    @BindView(R.id.cb_agree)
    CheckBox cbAgree;

    private ProgressDialogBuilder dialogProgress;
    private Context context;
    private Validator validator;
    private String date_original;

    @OnClick(R.id.sign_in)
    void Login() {
        finish();
    }


    PermissionListener permissionGetFotoListener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {

            EasyImage.openChooserWithGallery(RegisterUserActivity.this, getString(R.string.pick_source), 0);
        }

        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {

            String message = String.format(Locale.getDefault(), getString(R.string.message_denied), "WRITE STORAGE EKSTERNAL");
            Toast.makeText(RegisterUserActivity.this, message, Toast.LENGTH_SHORT).show();
        }


    };

    @OnClick(R.id.birthday)
    void BirthDate() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                RegisterUserActivity.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)

        );

        dpd.show(getFragmentManager(), "Datepickerdialog");
    }

    @OnClick(R.id.btn_new_account)
    void Lock() {
        validator.validate();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Iconify
                .with(new FontAwesomeModule())
                .with(new EntypoModule())
                .with(new MaterialModule())
                .with(new MaterialCommunityModule())
                .with(new SimpleLineIconsModule());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_user);
        ButterKnife.bind(this);

        context = getApplicationContext();
        dialogProgress = new ProgressDialogBuilder(this);


        EasyImage.configuration(this)
                .setImagesFolderName(getString(R.string.app_name))
                .saveInAppExternalFilesDir()
                .setCopyExistingPicturesToPublicLocation(true);
        setValidator();

    }

    private void setValidator() {
        validator = new Validator(this);
        validator.setValidationListener(new Validator.ValidationListener() {
            @Override
            public void onValidationSucceeded() {

                if (!cbAgree.isChecked()) {
                    showTermNotif();
                } else {
                    RegisterAction();
                }
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


    private void RegisterAction() {
        dialogProgress.show("Register...", getString(R.string.please_wait));

        String firebaseId = FirebaseInstanceId.getInstance().getToken();

        String inputStr = noTelp.getText().toString().trim();
        String num = "+62" + TextUtils.ReplaceFirstCaracters(inputStr);

        Map<String, String> params = new HashMap<>();

        params.put(Sample.FULL_NAME, fullName.getText().toString());
        params.put(Sample.EMAIL, email.getText().toString());
        params.put(Sample.PASSWORD, password.getText().toString());
        params.put(Sample.USERNAME, num);
        params.put(Sample.FIREBASE_ID, firebaseId);
        params.put(Sample.LAT, "0");
        params.put(Sample.LONG, "0");
        params.put(Sample.BIRTHDATE, date_original);
        params.put(Sample.GENDER, male.isSelected() ? "Male" : "Female");
        params.put(Sample.PROVINCE, province.getText().toString());
        params.put(Sample.CITY, city.getText().toString());
        params.put(Sample.NIK, ktp.getText().toString());

        RegisterService mService = ApiUtils.RegisterService(this);
        mService.getRegisterLink(params).enqueue(new Callback<Register>() {
            @Override
            public void onResponse(Call<Register> call, Response<Register> response) {
                dialogProgress.hide();
                if (response.isSuccessful()) {
                    if (response.body().getStatus()) {
                        DataWasher data = response.body().getDataWasher();

                        Prefs.putToken(context, response.body().getToken());
                        Prefs.putUserId(context, data.getUserId());
                        Prefs.putEmail(context, data.getEmail());
                        Prefs.putUsername(context, data.getUsername());
                        Prefs.putType(context, data.getType());
                        Prefs.putFullName(context, data.getFullName());
                        Prefs.putSaldo(context, String.valueOf(data.getSaldo()));
                        Prefs.putFirebaseId(context, data.getFirebaseId());
                        Prefs.putGeometryLat(context, data.getGeometryLat());
                        Prefs.putGeometryLong(context, data.getGeometryLong());
                        Prefs.putProfileGender(context, data.getProfileGender());
                        Prefs.putProfilePhoto(context, data.getProfilePhoto());
                        Prefs.putProfileProvince(context, data.getProfileProvince());
                        Prefs.putProfileCity(context, data.getProfileCity());
                        Prefs.putProfileNik(context, data.getProfileNik());
                        Prefs.putOnline(context, data.getOnline());
                        Prefs.putStatus(context, data.getStatus());
                        Prefs.putCreatedAt(context, data.getCreatedAt());
                        Prefs.putUpdatedAt(context, data.getUpdatedAt());
                        Prefs.putActivityIndex(context, Sample.ACTIVATION_CODE_INDEX);

                        toVerificationActivity();
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

    private void toVerificationActivity() {
        Intent intent = new Intent(this, VerificationCodeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }



/*
    private void checkEmailExist(final int triggerOrigin) {
        msgEmail.setText("checking...");
        Map<String, String> params = new HashMap<>();
        params.put(Sample.EMAIL_ADDRESS, etEmail.getText().toString().trim());

        RegisterService mService = ApiUtils.Register(this);
        mService.getCheckEmailLink(params).enqueue(new Callback<CheckEmail>() {
            @Override
            public void onResponse(Call<CheckEmail> call, Response<CheckEmail> response) {

                if (response.isSuccessful()) {
                    if (response.body().getStatus().equalsIgnoreCase(Statics.SUCCESS)) {
                        if (triggerOrigin == FROM_BUTTON_NEXT) {
                            new TedPermission(AccountDataActivity.this)
                                    .setPermissionListener(permissionReadStatePhoneListener)
                                    .setDeniedMessage(getString(R.string.next_read_phone_permission))
                                    .setPermissions(Manifest.permission.READ_PHONE_STATE)
                                    .check();
                        } else {
                            msgEmail.setText("");
                        }
                    } else
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();

                } else {
                    int statusCode = response.code();

                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        String message = jsonObject.getString(Statics.MESSAGE);
                        JSONObject jsonObjectMessage = new JSONObject(message);
                        JSONArray jsonArray = ((JSONArray) jsonObjectMessage.get(Statics.EMAIL_ADDRESS));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            String val = jsonArray.get(i).toString();
                            msgEmail.setText(Html.fromHtml("<font color='red'>" + val + "</font>"));
                        }

                    } catch (JSONException | IOException e) {
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<CheckEmail> call, Throwable t) {
                String message = t.getMessages();

                msgEmail.setText("");
            }
        });

    }*/

    public void showTermNotif() {
        final Dialog dialog = new Dialog(RegisterUserActivity.this);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_agreement_register);

        dialog.findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        dialog.show();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        date_original = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
        String date = String.valueOf(dayOfMonth) + " " + String.valueOf(TextUtils.getBulan(getBaseContext(), monthOfYear + 1)) + " " + String.valueOf(year);
        birthday.setText(date);
        birthday.setError(null);
    }


}
