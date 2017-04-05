package com.qwash.washer.ui.activity.register;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.qwash.washer.R;
import com.qwash.washer.Sample;
import com.qwash.washer.api.ApiUtils;
import com.qwash.washer.api.client.sms.VerificationCodeService;
import com.qwash.washer.api.model.register.SendSms;
import com.qwash.washer.ui.activity.BaseActivity;
import com.qwash.washer.utils.Prefs;
import com.qwash.washer.utils.ProgressDialogBuilder;
import com.qwash.washer.utils.TextUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangeNumberActivity extends BaseActivity {

    private static final String TAG = "ChangeNumberActivity";

    @Length(min = 6, max = 20, trim = true, messageResId = R.string.lengt_phone_val)
    @NotEmpty
    @BindView(R.id.et_phone_number)
    EditText etPhoneNumber;

    private String prev_phone_number;
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
        setContentView(R.layout.activity_change_number);
        ButterKnife.bind(this);
        context = getApplicationContext();
        dialogProgress = new ProgressDialogBuilder(this);

        prev_phone_number = Prefs.getUsername(this);

        validator = new Validator(this);
        validator.setValidationListener(new Validator.ValidationListener() {
            @Override
            public void onValidationSucceeded() {
                String inputStr = etPhoneNumber.getText().toString().trim();
                String num = "+62" + TextUtils.ReplaceFirstCaracters(inputStr);
                if (num.equalsIgnoreCase(prev_phone_number))
                    etPhoneNumber.setError(getString(R.string.must_input_diff_number));
                else {
                    checkCode(num);
                }
            }

            @Override
            public void onValidationFailed(List<ValidationError> errors) {
                TextUtils.errorValidation(context, errors);
            }
        });

        etPhoneNumber.setText(prev_phone_number.replace("+62", ""));

    }

    private void checkCode(final String num) {
        dialogProgress.show(getString(R.string.change_number_loading), getString(R.string.please_wait));
        Map<String, String> params = new HashMap<>();
        params.put(Sample.USERNAME, num);
        params.put(Sample.USER_ID, Prefs.getUserId(this));

        VerificationCodeService mService = ApiUtils.VerificationCodeService(this);
        mService.getChangeNumberLink("Bearer " + Prefs.getToken(this), params).enqueue(new Callback<SendSms>() {
            @Override
            public void onResponse(Call<SendSms> call, Response<SendSms> response) {
                dialogProgress.hide();
                if (response.isSuccessful()) {
                    if (response.body().getStatus()) {
                        Prefs.putUsername(ChangeNumberActivity.this, num);
                        String msg = response.body().getMessages();
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                        Intent returnIntent = new Intent();
                        setResult(Activity.RESULT_OK, returnIntent);
                        finish();
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
