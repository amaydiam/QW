package com.qwash.washer.ui.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.EntypoIcons;
import com.qwash.washer.BuildConfig;
import com.qwash.washer.R;
import com.qwash.washer.ui.widget.RobotoRegularTextView;
import com.qwash.washer.utils.LanguageHelper;
import com.qwash.washer.utils.Prefs;

/**
 * Created by Amay on 4/5/2017.
 */

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LanguageHelper.onAttach(base));
    }


    public void Logout() {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure, you wanted to logout?");
        alertDialogBuilder.setPositiveButton("yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        // Logout email
                        Prefs.Reset(getApplicationContext());
                        // Google sign out
                        FirebaseAuth.getInstance().signOut();

                        Intent intent = new Intent(getApplicationContext(), LoginUserActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void ShowAbout() {
        final Dialog dialog = new Dialog(this);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_about_application);

        RobotoRegularTextView versionApp = (RobotoRegularTextView) dialog.findViewById(R.id.version_app);
        FloatingActionButton btnTwitter = (FloatingActionButton) dialog.findViewById(R.id.btn_twitter);
        FloatingActionButton btnFacebook = (FloatingActionButton) dialog.findViewById(R.id.btn_facebook);
        FloatingActionButton btnInstagram = (FloatingActionButton) dialog.findViewById(R.id.btn_instagram);

        btnTwitter.setImageDrawable(
                new IconDrawable(this, EntypoIcons.entypo_twitter)
                        .colorRes(R.color.white)
                        .actionBarSize());
        btnFacebook.setImageDrawable(
                new IconDrawable(this, EntypoIcons.entypo_facebook)
                        .colorRes(R.color.white)
                        .actionBarSize());
        btnInstagram.setImageDrawable(
                new IconDrawable(this, EntypoIcons.entypo_instagram)
                        .colorRes(R.color.white)
                        .actionBarSize());

        btnTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialog != null) {
                    Intent intent = null;
                    try {
                        // get the Twitter app if possible
                        getPackageManager().getPackageInfo("com.twitter.android", 0);
                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?user_id=qwash_indonesia"));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    } catch (Exception e) {
                        // no Twitter app, revert to browser
                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/qwash_indonesia"));
                    }
                    startActivity(intent);
                    dialog.dismiss();
                }
            }
        });
        btnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialog != null) {
                    Intent intent;
                    try {
                        // get the Twitter app if possible
                        int versionCode = getPackageManager().getPackageInfo("com.facebook.katana", 0).versionCode;
                        String url_facebook;
                        if (versionCode >= 3002850) { //newer versions of fb app
                            url_facebook = "fb://facewebmodal/f?href=https://www.facebook.com/qwashindonesia";
                        } else { //older versions of fb app
                            url_facebook = "fb://page/qwashindonesia";
                        }
                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url_facebook));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    } catch (Exception e) {
                        // no Twitter app, revert to browser
                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/qwashindonesia"));
                    }
                    startActivity(intent);
                    dialog.dismiss();
                }
            }
        });

        btnInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialog != null) {
                    Intent intent;
                    try {
                        getPackageManager().getPackageInfo("com.instagram.android", 0);
                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://instagram.com/_u/qwash_indonesia"));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    } catch (Exception e) {
                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/qwash_indonesia"));
                    }
                    startActivity(intent);
                    dialog.dismiss();
                }
            }
        });

        versionApp.setText("v" + BuildConfig.VERSION_NAME);


        dialog.show();
    }
}
