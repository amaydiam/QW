package com.qwash.washer.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
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
import com.mobsandgeeks.saripaar.annotation.Password;
import com.qwash.washer.R;
import com.qwash.washer.Sample;
import com.qwash.washer.api.ApiUtils;
import com.qwash.washer.api.client.auth.LoginService;
import com.qwash.washer.api.model.login.Login;
import com.qwash.washer.api.model.washer.DataWasher;
import com.qwash.washer.ui.activity.forgotpassword.ForgotPasswordActivity;
import com.qwash.washer.ui.activity.forgotpassword.RequestForgotPasswordActivity;
import com.qwash.washer.ui.activity.forgotpassword.VerificationCodeForgotPasswordActivity;
import com.qwash.washer.ui.activity.register.LockWasherActivity;
import com.qwash.washer.ui.activity.register.RegisterUserActivity;
import com.qwash.washer.ui.activity.register.VerificationCodeActivity;
import com.qwash.washer.ui.activity.register.VerifyDocumentActivity;
import com.qwash.washer.ui.activity.register.VerifyToolsActivity;
import com.qwash.washer.ui.widget.RobotoRegularEditText;
import com.qwash.washer.ui.widget.RobotoRegularTextView;
import com.qwash.washer.utils.Prefs;
import com.qwash.washer.utils.ProgressDialogBuilder;
import com.txusballesteros.PasswordEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginUserActivity extends BaseActivity {


    @NotEmpty
    @Length(min = 5, max = 100, trim = true, messageResId = R.string.val_email_length)
    @Email
    @BindView(R.id.email)
    RobotoRegularEditText email;

    @NotEmpty
    @Password(min = 5, messageResId = R.string.val_password_length)
    @BindView(R.id.password)
    PasswordEditText password;

    @BindView(R.id.btn_forgot_password)
    RobotoRegularTextView btnForgotPassword;
    @BindView(R.id.register)
    RobotoRegularTextView register;
    private Context context;

    @OnClick(R.id.btn_login)
    void Login() {
        validator.validate();
    }

    @OnClick(R.id.btn_forgot_password)
    void ForgotPassword() {
        Intent intent = new Intent(this, RequestForgotPasswordActivity.class);
        startActivityForResult(intent, 1);
    }

    @OnClick(R.id.register)
    void Register() {
        startActivity(new Intent(LoginUserActivity.this, RegisterUserActivity.class));
    }

    private Validator validator;
    private ProgressDialogBuilder dialogProgress;
    private String TAG = "LoginUserActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Iconify
                .with(new FontAwesomeModule())
                .with(new EntypoModule())
                .with(new MaterialModule())
                .with(new MaterialCommunityModule())
                .with(new SimpleLineIconsModule());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_user);
        ButterKnife.bind(this);
        context = getApplicationContext();
        dialogProgress = new ProgressDialogBuilder(this);
        setValidator();
        password.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    hideKeyboard();
                    validator.validate();
                    return true;
                }
                return false;
            }
        });

    }

    private void setValidator() {
        validator = new Validator(this);
        validator.setValidationListener(new Validator.ValidationListener() {
            @Override
            public void onValidationSucceeded() {


                remoteLogin();

            }

            @Override
            public void onValidationFailed(List<ValidationError> errors) {
                loginFailed(errors);
            }
        });
    }

    private void loginFailed(List<ValidationError> errors) {
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


    private void remoteLogin() {
        dialogProgress.show("LoginService ...", "Please wait...");

        final String firebase_id = FirebaseInstanceId.getInstance().getToken();
        Map<String, String> params = new HashMap<>();
        params.put(Sample.EMAIL, email.getText().toString());
        params.put(Sample.PASSWORD, password.getText().toString());
        params.put(Sample.FIREBASE_ID, firebase_id);

        Log.v("sebelum_login", firebase_id);

        LoginService mService = ApiUtils.LoginService(this);
        mService.getLoginLink(params).enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                dialogProgress.hide();
                if (response.isSuccessful()) {
                    if (response.body().getStatus()) {

                        DataWasher data = response.body().getDataWasher();
                        Prefs.putToken(context, response.body().getToken());


                        Log.v("setelah_login", data.getFirebaseId());

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

                        toHomeActivity();

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
            public void onFailure(Call<Login> call, Throwable t) {
                String message = t.getMessage();
                dialogProgress.hide();
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }


    void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (Prefs.isLogedIn(this)) {
            if (Prefs.getOrderedData(this) != null
                    && (Prefs.getProgresWorking(this) != Sample.CODE_NO_ORDER
                    || Prefs.getProgresWorking(this) != Sample.CODE_GET_ORDER)) {
                ProgressOrderedrActiivity();
            } else
                toHomeActivity();
        } else if (Prefs.getActivityIndex(this) == Sample.ACTIVATION_CODE_INDEX) {
            ActivationCodeActivity();
        } else if (Prefs.getActivityIndex(this) == Sample.VERIFY_DOCUMENT_INDEX) {
            VerifyDocumentActivity();
        } else if (Prefs.getActivityIndex(this) == Sample.VERIFY_TOOLS_INDEX) {
            VerifyToolsActivity();
        } else if (Prefs.getActivityIndex(this) == Sample.LOCK_MAP_AFTER_REGISTER_INDEX) {
            LockMapRegisterActivity();
        }
    }

    private void toHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void ActivationCodeActivity() {
        Intent intent = new Intent(this, VerificationCodeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void VerifyDocumentActivity() {
        Intent intent = new Intent(this, VerifyDocumentActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void VerifyToolsActivity() {
        Intent intent = new Intent(this, VerifyToolsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void LockMapRegisterActivity() {
        Intent intent = new Intent(this, LockWasherActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void ProgressOrderedrActiivity() {
        Bundle args = new Bundle();
        args.putString(Sample.ORDER, Prefs.getOrderedData(this));
        Intent intent = new Intent(this, ProgressOrderActivity.class);
        intent.putExtras(args);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                String email=data.getStringExtra(Sample.EMAIL);
                Intent intent = new Intent(this, VerificationCodeForgotPasswordActivity.class);
                intent.putExtra(Sample.EMAIL, email);
                startActivity(intent);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
            }
        }
    }

}