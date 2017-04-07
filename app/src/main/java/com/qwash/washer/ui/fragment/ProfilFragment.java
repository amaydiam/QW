package com.qwash.washer.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.EntypoModule;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.joanzapata.iconify.fonts.MaterialCommunityModule;
import com.joanzapata.iconify.fonts.MaterialModule;
import com.joanzapata.iconify.fonts.SimpleLineIconsModule;
import com.qwash.washer.R;
import com.qwash.washer.Sample;
import com.qwash.washer.model.temporary.ChangePassword;
import com.qwash.washer.ui.widget.RobotoRegularTextView;
import com.qwash.washer.utils.Prefs;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import agency.tango.android.avatarview.loader.PicassoLoader;
import agency.tango.android.avatarview.views.AvatarView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfilFragment extends Fragment {


    @BindView(R.id.washer_photo)
    AvatarView washerPhoto;
    @BindView(R.id.full_name)
    RobotoRegularTextView fullName;
    @BindView(R.id.email)
    RobotoRegularTextView email;
    @BindView(R.id.phone)
    RobotoRegularTextView phone;

    public ProfilFragment() {
        // Required empty public constructor
    }


    @OnClick(R.id.layout_change_password)
    public void ChangePassword() {

        FragmentManager ft = getChildFragmentManager();
        DialogRequestNewPasswordFragment dialogRequestNewPasswordFragment = new DialogRequestNewPasswordFragment();
        dialogRequestNewPasswordFragment.show(ft, "request_password");
    }

    @OnClick(R.id.layout_change_language)
    public void ChangeLanguage() {

        FragmentManager ft = getChildFragmentManager();
        DialogChangeLanguageFragment dialogChangeLanguageFragment = new DialogChangeLanguageFragment();
        dialogChangeLanguageFragment.show(ft, "request_language");
    }

    @OnClick(R.id.layout_term_of_service)
    public void TermOfService() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Sample.URL_TERM_OF_SERVICE));
        startActivity(browserIntent);
    }


    @OnClick(R.id.layout_privacy_police)
    public void PrivacyPolice() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Sample.URL_PRIVACY_POLICE));
        startActivity(browserIntent);
    }



    @OnClick(R.id.layout_rate_qwash)
    public void RateQwash() {

        FragmentManager ft = getChildFragmentManager();
        DialogRateQwashFragment dialogRateQwashFragment = new DialogRateQwashFragment();
        dialogRateQwashFragment.show(ft, "rate_qwash");
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        Iconify
                .with(new FontAwesomeModule())
                .with(new EntypoModule())
                .with(new MaterialModule())
                .with(new MaterialCommunityModule())
                .with(new SimpleLineIconsModule());
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profil, container, false);
        ButterKnife.bind(this, view);
        setData();
        return view;
    }

    private void setData() {
        PicassoLoader imageLoader = new PicassoLoader();
        imageLoader.loadImage(washerPhoto, Prefs.getProfilePhoto(getActivity()), Prefs.getFullName(getActivity()));
        fullName.setText(Prefs.getFullName(getActivity()));
        email.setText(Prefs.getEmail(getActivity()));
        phone.setText(Prefs.getUsername(getActivity()));
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onChangePassword(ChangePassword cp) {
        if (cp.getStatus()) {
            FragmentManager ft = getChildFragmentManager();
            DialogChangePasswordFragment changePasswordFragment = new DialogChangePasswordFragment();
            changePasswordFragment.setPassword(cp.getPassword());
            changePasswordFragment.show(ft, "change_password");
        }

        ChangePassword stickyEvent = EventBus.getDefault().getStickyEvent(ChangePassword.class);
        if (stickyEvent != null) {
            EventBus.getDefault().removeStickyEvent(stickyEvent);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);

    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }


}
