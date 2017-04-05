package com.qwash.washer.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.joanzapata.iconify.widget.IconTextView;
import com.qwash.washer.R;
import com.qwash.washer.ui.activity.LoginUserActivity;
import com.qwash.washer.ui.activity.register.RegisterUserActivity;
import com.qwash.washer.ui.activity.register.VerificationCodeActivity;
import com.qwash.washer.ui.widget.RobotoRegularButton;
import com.qwash.washer.utils.LanguageHelper;
import com.qwash.washer.utils.Prefs;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class DialogChangeLanguageFragment extends DialogFragment {


    @BindView(R.id.btn_close)
    IconTextView btnClose;
    @BindView(R.id.title)
    IconTextView title;
    @BindView(R.id.badge_english)
    IconTextView badgeEnglish;
    @BindView(R.id.layout_english)
    RelativeLayout layoutEnglish;
    @BindView(R.id.badge_indonesia)
    IconTextView badgeIndonesia;
    @BindView(R.id.layout_indonesia)
    RelativeLayout layoutIndonesia;
    @BindView(R.id.btn_change_language)
    RobotoRegularButton btnChangeLanguage;
    private Unbinder butterKnife;
    private String selected;

    public DialogChangeLanguageFragment() {

    }

    @OnClick(R.id.btn_close)
    void Close() {
        dismiss();
    }

    @OnClick(R.id.btn_change_language)
    void ChangePassword() {
        LanguageHelper.setLocale(getActivity(), selected);
        //   dismiss();
        Intent intent = new Intent(getActivity(), LoginUserActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        getActivity().finish();
    }

    @OnClick(R.id.layout_english)
    void English() {
        selected = "en";
        CheckSelected();
    }

    @OnClick(R.id.layout_indonesia)
    void Indonesia() {
        selected = "in";
        CheckSelected();
    }

    private void CheckSelected() {
        if (selected.equalsIgnoreCase("in")) {
            badgeIndonesia.setVisibility(View.VISIBLE);
            badgeEnglish.setVisibility(View.GONE);
        } else {
            badgeIndonesia.setVisibility(View.GONE);
            badgeEnglish.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        butterKnife.unbind();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selected = Prefs.getLanguage(getActivity());
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {

        View view = inflater.inflate(
                R.layout.dialog_change_language, container);
        butterKnife = ButterKnife.bind(this, view);
        CheckSelected();
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