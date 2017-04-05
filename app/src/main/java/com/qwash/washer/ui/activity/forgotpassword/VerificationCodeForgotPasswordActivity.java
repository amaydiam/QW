package com.qwash.washer.ui.activity.forgotpassword;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.qwash.washer.R;
import com.qwash.washer.Sample;
import com.qwash.washer.api.ApiUtils;
import com.qwash.washer.api.client.forgotpassword.ForgotPasswordService;
import com.qwash.washer.api.model.forgotpassword.RequestForgotPassword;
import com.qwash.washer.api.model.forgotpassword.VerificationCodeForgotPassword;
import com.qwash.washer.ui.activity.BaseActivity;
import com.qwash.washer.ui.widget.RobotoRegularEditText;
import com.qwash.washer.ui.widget.RobotoRegularTextView;
import com.qwash.washer.utils.ProgressDialogBuilder;
import com.qwash.washer.utils.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerificationCodeForgotPasswordActivity extends BaseActivity {

    private static final String TAG = "VerificationCodeForgotPasswordActivity";


    @BindView(R.id.verification_desc)
    RobotoRegularTextView verificationDesc;

    @BindView(R.id.et_verification_code)
    RobotoRegularEditText etVerification;
    @BindView(R.id.toolbar)
    Toolbar toolbar;


    private ProgressDialogBuilder dialogProgress;
    private Context context;
    private String email;

    @OnClick({R.id.btn_verification_code, R.id.resend_the_text})
    void Click(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_verification_code:

                String inputStr = etVerification.getText().toString();
                if (!TextUtils.isNullOrEmpty(inputStr.toString().trim())) {
                    checkCode(inputStr);
                } else {
                    Toast.makeText(context, getString(R.string.please_input_verification_code), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.resend_the_text:
                resendCode();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_forgot_password);
        ButterKnife.bind(this);
        email= getIntent().getStringExtra(Sample.EMAIL);
        setSupportActionBar(toolbar);
        context = getApplicationContext();
        dialogProgress = new ProgressDialogBuilder(this);

        String desc = getString(R.string.verification_code_desc);
        verificationDesc.setText(desc);

    }


    private void resendCode() {
        dialogProgress.show(getString(R.string.resend_code_loading), getString(R.string.please_wait));
        ForgotPasswordService mService = ApiUtils.ForgotPasswordService(this);
        mService.getRequestForgotPasswordLink(email).enqueue(new Callback<RequestForgotPassword>() {
            @Override
            public void onResponse(Call<RequestForgotPassword> call, Response<RequestForgotPassword> response) {
                dialogProgress.hide();
                if (response.isSuccessful()) {
                    if (response.body().getStatus()) {
                        String msg = response.body().getMessages();
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    }

                } else {
                    int statusCode = response.code();

                    Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
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

    private void checkCode(String inputStr) {
        dialogProgress.show(getString(R.string.verify_code_loading), getString(R.string.please_wait));
        Map<String, String> params = new HashMap<>();

        params.put(Sample.CODE, inputStr);

        ForgotPasswordService mService = ApiUtils.ForgotPasswordService(this);
        mService.getSmsVerificationLink( params).enqueue(new Callback<VerificationCodeForgotPassword>() {
            @Override
            public void onResponse(Call<VerificationCodeForgotPassword> call, Response<VerificationCodeForgotPassword> response) {

                dialogProgress.hide();
                if (response.isSuccessful()) {
                    if (response.body().getStatus()) {
                        Intent intent = new Intent(VerificationCodeForgotPasswordActivity.this, ForgotPasswordActivity.class);
                        intent.putExtra(Sample.USER_ID, response.body().getUserId());
                        startActivity(intent);
                        finish();
                    }

                } else {
                    int statusCode = response.code();
                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        String message = jsonObject.getString(Sample.MESSAGE);
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        etVerification.setText("");
                    } catch (JSONException | IOException e) {
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<VerificationCodeForgotPassword> call, Throwable t) {
                String message = t.getMessage();

                dialogProgress.hide();
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        });

    }

}
