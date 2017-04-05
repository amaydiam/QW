package com.qwash.washer.ui.activity.register;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.qwash.washer.R;
import com.qwash.washer.Sample;
import com.qwash.washer.api.ApiUtils;
import com.qwash.washer.api.client.sms.VerificationCodeService;
import com.qwash.washer.api.model.register.SendSms;
import com.qwash.washer.api.model.register.VerificationCode;
import com.qwash.washer.ui.activity.BaseActivity;
import com.qwash.washer.ui.activity.LoginUserActivity;
import com.qwash.washer.ui.widget.RobotoRegularEditText;
import com.qwash.washer.ui.widget.RobotoRegularTextView;
import com.qwash.washer.utils.Prefs;
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

public class VerificationCodeActivity extends BaseActivity {

    private static final String TAG = "VerificationCodeActivity";


    @BindView(R.id.verification_desc)
    RobotoRegularTextView verificationDesc;

    @BindView(R.id.et_verification_code)
    RobotoRegularEditText etVerification;
    @BindView(R.id.toolbar)
    Toolbar toolbar;


    private ProgressDialogBuilder dialogProgress;
    private Context context;

    @OnClick({R.id.btn_verification_code, R.id.resend_the_text, R.id.change_number})
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
            case R.id.change_number:
                Intent intent = new Intent(this, ChangeNumberActivity.class);
                //  intent.putExtra("signup", signup_personal);
                startActivityForResult(intent, 1);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        context = getApplicationContext();

        context = getApplicationContext();
        dialogProgress = new ProgressDialogBuilder(this);

        String desc = getString(R.string.verification_desc);
        desc = String.format(desc, Prefs.getUsername(this));

        verificationDesc.setText(desc);

    }


    private void resendCode() {
        dialogProgress.show(getString(R.string.resend_code_loading), getString(R.string.please_wait));

        VerificationCodeService mService = ApiUtils.VerificationCodeService(this);
        mService.getResendSmsLink("Bearer " + Prefs.getToken(this), Prefs.getUserId(this)).enqueue(new Callback<SendSms>() {
            @Override
            public void onResponse(Call<SendSms> call, Response<SendSms> response) {
                dialogProgress.hide();
                if (response.isSuccessful()) {
                    if (response.body().getStatus()) {
                        String msg = response.body().getMessages();
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                        etVerification.setText("");
                    }

                } else {
                    int statusCode = response.code();

                }
            }

            @Override
            public void onFailure(Call<SendSms> call, Throwable t) {
                String message = t.getMessage();

                dialogProgress.hide();
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void checkCode(String inputStr) {
        dialogProgress.show(getString(R.string.verify_code_loading), getString(R.string.please_wait));
        Map<String, String> params = new HashMap<>();

        params.put(Sample.USER_ID, Prefs.getUserId(this));
        params.put(Sample.CODE, inputStr);

        VerificationCodeService mService = ApiUtils.VerificationCodeService(this);
        mService.getSmsVerificationLink("Bearer " + Prefs.getToken(this), params).enqueue(new Callback<VerificationCode>() {
            @Override
            public void onResponse(Call<VerificationCode> call, Response<VerificationCode> response) {

                dialogProgress.hide();
                if (response.isSuccessful()) {
                    if (response.body().getStatus()) {
                        Prefs.putActivityIndex(context, Sample.NO_INDEX);
                        toLoginActivity();
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
            public void onFailure(Call<VerificationCode> call, Throwable t) {
                String message = t.getMessage();

                dialogProgress.hide();
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                String desc = getString(R.string.verification_desc);
                desc = String.format(desc, Prefs.getUsername(this));
                etVerification.setText("");
                verificationDesc.setText(desc);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
            }
        }
    }


    private void toLoginActivity() {
        Intent intent = new Intent(this, LoginUserActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

}
