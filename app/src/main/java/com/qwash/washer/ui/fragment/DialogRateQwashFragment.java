package com.qwash.washer.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.joanzapata.iconify.widget.IconTextView;
import com.qwash.washer.R;
import com.qwash.washer.Sample;
import com.qwash.washer.api.ApiUtils;
import com.qwash.washer.api.client.account.AccountService;
import com.qwash.washer.api.model.account.ChangePasswordRespone;
import com.qwash.washer.ui.widget.RobotoRegularButton;
import com.qwash.washer.utils.Prefs;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DialogRateQwashFragment extends DialogFragment {


    @BindView(R.id.btn_close)
    IconTextView btnClose;
    @BindView(R.id.title)
    IconTextView title;
    @BindView(R.id.btn_cancel)
    RobotoRegularButton btnCancel;
    @BindView(R.id.btn_rate)
    RobotoRegularButton btnRate;
    private Unbinder butterKnife;

    public DialogRateQwashFragment() {

    }

    @OnClick({R.id.btn_close, R.id.btn_cancel})
    void Close() {
        dismiss();
    }

    @OnClick(R.id.btn_rate)
    void Rate() {
        final String appPackageName = getActivity().getPackageName();
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
        }
        dismiss();
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
                R.layout.dialog_rate_qwash, container);
        butterKnife = ButterKnife.bind(this, view);


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

    @Override
    public void onStop() {
        super.onStop();
    }

}