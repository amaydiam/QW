package com.qwash.washer.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.qwash.washer.R;
import com.qwash.washer.Sample;
import com.qwash.washer.api.ApiUtils;
import com.qwash.washer.api.client.account.AccountService;
import com.qwash.washer.api.model.account.RequestNewPassword;
import com.qwash.washer.model.temporary.ChangePassword;
import com.qwash.washer.ui.widget.RobotoRegularEditText;
import com.qwash.washer.utils.Prefs;
import com.qwash.washer.utils.ProgressDialogBuilder;
import com.qwash.washer.utils.TextUtils;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DialogRequestNewPasswordFragment extends DialogFragment {

    @NotEmpty
    @Password(min = 5, messageResId = R.string.val_new_password_length)
    @BindView(R.id.new_password)
    RobotoRegularEditText newPassword;

    @NotEmpty
    @ConfirmPassword()
    @BindView(R.id.confirm_new_password)
    RobotoRegularEditText confirmNewPassword;

    private Unbinder butterKnife;
    private Validator validator;
    private ProgressDialogBuilder dialogProgress;

    public DialogRequestNewPasswordFragment() {

    }

    @OnClick(R.id.btn_close)
    void Close() {
        dismiss();
    }

    @OnClick(R.id.btn_change_password)
    void ChangePassword() {
        validator.validate();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        butterKnife.unbind();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialogProgress= new ProgressDialogBuilder(getActivity());

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {

        View view = inflater.inflate(
                R.layout.dialog_request_new_password, container);
        butterKnife = ButterKnife.bind(this, view);
        validator = new Validator(this);
        validator.setValidationListener(new Validator.ValidationListener() {
            @Override
            public void onValidationSucceeded() {

              ActionRequestChangePassword();
            }

            @Override
            public void onValidationFailed(List<ValidationError> errors) {
                TextUtils.errorValidation(getContext(), errors);
            }
        });


        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        return view;
    }



    private void ActionRequestChangePassword() {
        dialogProgress.show(getString(R.string.request_new_password_action), getString(R.string.please_wait));

        AccountService mService = ApiUtils.AccountService(getActivity());
        mService.getRequestNewPasswordLink("Bearer " + Prefs.getToken(getActivity()), Prefs.getUserId(getActivity())).enqueue(new Callback<RequestNewPassword>() {
            @Override
            public void onResponse(Call<RequestNewPassword> call, Response<RequestNewPassword> response) {
                dialogProgress.hide();
                if (response.isSuccessful()) {
                    if (response.body().getStatus()) {
                        Toast.makeText(getActivity(),response.body().getMessages(), Toast.LENGTH_SHORT).show();
                        EventBus.getDefault().postSticky(new ChangePassword(true,newPassword.getText().toString()));
                        dismiss();
                    }
                } else {
                    int statusCode = response.code();
                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        String message = jsonObject.getString(Sample.MESSAGE);
                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                    } catch (JSONException | IOException e) {
                        Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }

                    newPassword.setText("");
                    confirmNewPassword.setText("");
                }
            }

            @Override
            public void onFailure(Call<RequestNewPassword> call, Throwable t) {
                String message = t.getMessage();
                dialogProgress.hide();
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

    }

    @Override
    public void onStop() {
        super.onStop();
    }



}