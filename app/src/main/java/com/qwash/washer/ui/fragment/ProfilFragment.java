package com.qwash.washer.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.EntypoModule;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.joanzapata.iconify.fonts.MaterialCommunityModule;
import com.joanzapata.iconify.fonts.MaterialModule;
import com.joanzapata.iconify.fonts.SimpleLineIconsModule;
import com.qwash.washer.R;
import com.qwash.washer.Sample;
import com.qwash.washer.ui.widget.RobotoRegularTextView;
import com.qwash.washer.utils.Prefs;

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
        DialogChangePasswordFragment dialogChangePasswordFragment = new DialogChangePasswordFragment();
        dialogChangePasswordFragment.show(ft, "change_password");
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


}
