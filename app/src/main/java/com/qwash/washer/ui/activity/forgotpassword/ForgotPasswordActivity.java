package com.qwash.washer.ui.activity.forgotpassword;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.EntypoModule;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.joanzapata.iconify.fonts.MaterialCommunityModule;
import com.joanzapata.iconify.fonts.MaterialIcons;
import com.joanzapata.iconify.fonts.MaterialModule;
import com.joanzapata.iconify.fonts.SimpleLineIconsModule;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.qwash.washer.R;
import com.qwash.washer.Sample;
import com.qwash.washer.api.ApiUtils;
import com.qwash.washer.api.client.forgotpassword.ForgotPasswordService;
import com.qwash.washer.api.model.forgotpassword.ForgotPassword;
import com.qwash.washer.ui.activity.BaseActivity;
import com.qwash.washer.ui.widget.RobotoRegularEditText;
import com.qwash.washer.utils.ProgressDialogBuilder;

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

public class ForgotPasswordActivity extends BaseActivity {


    @NotEmpty
    @Password(min = 5, messageResId = R.string.val_new_password_length)
    @BindView(R.id.new_password)
    RobotoRegularEditText newPassword;

    @NotEmpty
    @ConfirmPassword()
    @BindView(R.id.confirm_new_password)
    RobotoRegularEditText confirmNewPassword;
    @BindView(R.id.title_toolbar)
    TextView titleToolbar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private Context context;
    private String userId;

    @OnClick(R.id.btn_save)
    void Save() {
        validator.validate();
    }


    private Validator validator;
    private ProgressDialogBuilder dialogProgress;
    private String TAG = "ForgotPasswordActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Iconify
                .with(new FontAwesomeModule())
                .with(new EntypoModule())
                .with(new MaterialModule())
                .with(new MaterialCommunityModule())
                .with(new SimpleLineIconsModule());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        ButterKnife.bind(this);
        userId= getIntent().getStringExtra(Sample.USER_ID);
        context = getApplicationContext();
        dialogProgress = new ProgressDialogBuilder(this);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(
                new IconDrawable(this, MaterialIcons.md_arrow_back)
                        .colorRes(R.color.white)
                        .actionBarSize());
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setValidator();


    }

    private void setValidator() {
        validator = new Validator(this);
        validator.setValidationListener(new Validator.ValidationListener() {
            @Override
            public void onValidationSucceeded() {


                ResetPassword();

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


    private void ResetPassword() {
        dialogProgress.show(getString(R.string.reset_password_action), getString(R.string.please_wait));

        Map<String, String> params = new HashMap<>();
        params.put(Sample.USER_ID, userId);
        params.put(Sample.PASSWORD, newPassword.getText().toString());

        ForgotPasswordService mService = ApiUtils.ForgotPasswordService(this);
        mService.getForgotPasswordLink(params).enqueue(new Callback<ForgotPassword>() {
            @Override
            public void onResponse(Call<ForgotPassword> call, Response<ForgotPassword> response) {
                dialogProgress.hide();
                if (response.isSuccessful()) {
                    if (response.body().getStatus()) {
                        Toast.makeText(getApplicationContext(), response.body().getMessages(), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                } else {
                    int statusCode = response.code();
                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        String message = jsonObject.getString(Sample.MESSAGE);
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        newPassword.setText("");
                        confirmNewPassword.setText("");
                    } catch (JSONException | IOException e) {
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ForgotPassword> call, Throwable t) {
                String message = t.getMessage();
                dialogProgress.hide();
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();

    }


}