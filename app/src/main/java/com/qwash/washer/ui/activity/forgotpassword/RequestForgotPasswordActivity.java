package com.qwash.washer.ui.activity.forgotpassword;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.MaterialIcons;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.qwash.washer.R;
import com.qwash.washer.Sample;
import com.qwash.washer.api.ApiUtils;
import com.qwash.washer.api.client.forgotpassword.ForgotPasswordService;
import com.qwash.washer.api.model.forgotpassword.RequestForgotPassword;
import com.qwash.washer.ui.widget.RobotoRegularEditText;
import com.qwash.washer.utils.ProgressDialogBuilder;
import com.qwash.washer.utils.TextUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestForgotPasswordActivity extends AppCompatActivity {

    private static final String TAG = "RequestForgotPasswordActivity";

    @NotEmpty
    @Length(min = 5, max = 100, trim = true, messageResId = R.string.val_email_length)
    @Email
    @BindView(R.id.email)
    RobotoRegularEditText email;

    @BindView(R.id.title_toolbar)
    TextView titleToolbar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private ProgressDialogBuilder dialogProgress;
    private Context context;
    private Validator validator;

    @OnClick({R.id.btn_save})
    void Click(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_save:
                validator.validate();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_forgot_password);
        ButterKnife.bind(this);
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

        validator = new Validator(this);
        validator.setValidationListener(new Validator.ValidationListener() {
            @Override
            public void onValidationSucceeded() {
                String inputStr = email.getText().toString().trim();
                RequestForgotPassword(inputStr);
            }

            @Override
            public void onValidationFailed(List<ValidationError> errors) {
                TextUtils.errorValidation(context, errors);
            }
        });


    }

    private void RequestForgotPassword(final String email) {
        dialogProgress.show(getString(R.string.request_forgot_password_action), getString(R.string.please_wait));
        ForgotPasswordService mService = ApiUtils.ForgotPasswordService(this);
        mService.getRequestForgotPasswordLink(email).enqueue(new Callback<RequestForgotPassword>() {
            @Override
            public void onResponse(Call<RequestForgotPassword> call, Response<RequestForgotPassword> response) {
                dialogProgress.hide();
                if (response.isSuccessful()) {
                    if (response.body().getStatus()) {
                        String msg = response.body().getMessages();
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                        Intent returnIntent = new Intent();
                        returnIntent.putExtra(Sample.EMAIL,email);
                        setResult(Activity.RESULT_OK, returnIntent);
                        finish();
                    }

                } else {
                    int statusCode = response.code();

                }
            }

            @Override
            public void onFailure(Call<RequestForgotPassword> call, Throwable t) {
                String message = t.getMessage();

                dialogProgress.hide();
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void finishActivity() {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
    }

    @Override
    public void onBackPressed() {
        finishActivity();
    }
}
