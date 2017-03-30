package com.qwash.washer.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.qwash.washer.R;
import com.qwash.washer.Sample;
import com.qwash.washer.ui.activity.register.LockWasherActivity;
import com.qwash.washer.ui.activity.register.RegisterUserActivity;
import com.qwash.washer.ui.activity.register.VerificationCodeActivity;
import com.qwash.washer.ui.activity.register.VerifyDocumentActivity;
import com.qwash.washer.ui.activity.register.VerifyToolsActivity;
import com.qwash.washer.utils.Prefs;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by binderbyte on 06/01/17.
 */

public class WelcomeLogin extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.sign_in_washer)
    public void signIn(View view) {
        startActivity(new Intent(this, LoginUserActivity.class));
    }

    @OnClick(R.id.create_account)
    public void createAccount(View view) {
        startActivity(new Intent(this, RegisterUserActivity.class));
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


}
