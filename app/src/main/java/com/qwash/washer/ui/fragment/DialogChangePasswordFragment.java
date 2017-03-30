package com.qwash.washer.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.qwash.washer.R;
import com.qwash.washer.ui.widget.RobotoRegularEditText;
import com.qwash.washer.utils.TextUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class DialogChangePasswordFragment extends DialogFragment {

    @NotEmpty
    @Length(min = 5, max = 10, trim = true, messageResId = R.string.val_password_length)
    @BindView(R.id.password)
    RobotoRegularEditText password;

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

    public DialogChangePasswordFragment() {

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

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {

        View view = inflater.inflate(
                R.layout.dialog_change_password, container);
        butterKnife = ButterKnife.bind(this, view);
        validator = new Validator(this);
        validator.setValidationListener(new Validator.ValidationListener() {
            @Override
            public void onValidationSucceeded() {

                dismiss();
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

    @Override
    public void onStart() {
        super.onStart();

        getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }


}