package com.qwash.washer.ui.activity.register;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.EntypoModule;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.joanzapata.iconify.fonts.MaterialCommunityModule;
import com.joanzapata.iconify.fonts.MaterialModule;
import com.joanzapata.iconify.fonts.SimpleLineIconsModule;
import com.qwash.washer.R;
import com.qwash.washer.Sample;
import com.qwash.washer.api.ApiUtils;
import com.qwash.washer.api.client.PartFormString;
import com.qwash.washer.api.client.register.DocumentService;
import com.qwash.washer.api.client.register.UploadDocument;
import com.qwash.washer.api.model.GlobalError;
import com.qwash.washer.ui.activity.BaseActivity;
import com.qwash.washer.ui.fragment.DialogViewSinggleImageFragment;
import com.qwash.washer.ui.widget.RobotoRegularButton;
import com.qwash.washer.utils.Prefs;
import com.qwash.washer.utils.ProgressDialogBuilder;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import agency.tango.android.avatarview.loader.PicassoLoader;
import agency.tango.android.avatarview.views.AvatarView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.zelory.compressor.Compressor;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyDocumentActivity extends BaseActivity {

    private int TYPE_IMG;
    private int AVATAR = 1;
    private int KTP = 2;
    private int SKCK = 3;
    @BindView(R.id.title_toolbar)
    TextView titleToolbar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.washer_photo)
    AvatarView washerPhoto;
    @BindView(R.id.img_ktp)
    ImageView imgKtp;
    @BindView(R.id.layout_img_ktp)
    LinearLayout layoutImgKtp;
    @BindView(R.id.img_skck)
    ImageView imgSkck;
    @BindView(R.id.layout_img_skck)
    LinearLayout layoutImgSkck;
    @BindView(R.id.btn_continue)
    RobotoRegularButton btnContinue;
    private Context context;
    private ProgressDialogBuilder dialogProgress;
    private File pathAvatar;
    private File pathKtp;
    private File pathSkck;
    private PicassoLoader imageLoader;

    @OnClick(R.id.washer_photo)
    void PHOTO() {
        if (pathAvatar != null) {
            OpenDialog(AVATAR);
        } else {
            getFoto(AVATAR);
        }
    }

    @OnClick(R.id.img_ktp)
    void KTP() {
        if (pathKtp != null) {
            OpenDialog(KTP);
        } else {
            getFoto(KTP);
        }
    }

    @OnClick(R.id.img_skck)
    void SKCK() {
        if (pathSkck != null) {
            OpenDialog(SKCK);
        } else {
            getFoto(SKCK);
        }
    }

    void OpenDialog(final int type) {
        final String[] option = new String[]{"View", "Change"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, option);
        AlertDialog.Builder builder = new AlertDialog.Builder(VerifyDocumentActivity.this);
        builder.setTitle("Select Option");
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) { // TODO Auto-generated method stub
                if (which == 0)
                    viewFoto(type);
                else if (which == 1)
                    getFoto(type);
            }
        });
        final AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void viewFoto(int type) {
        FragmentManager ft = getSupportFragmentManager();
        DialogViewSinggleImageFragment newFragment = DialogViewSinggleImageFragment.newInstance(type == AVATAR ? pathAvatar.getAbsolutePath() : (type == KTP ? pathKtp.getAbsolutePath() : pathSkck.getAbsolutePath()));
        newFragment.show(ft, "slideshow");
    }

    private void getFoto(int type) {

        TYPE_IMG = type;
        new TedPermission(this)
                .setPermissionListener(permissionGetFotoListener)
                .setDeniedMessage(String.format(getString(R.string.upload_document_permission), type == AVATAR ? "AVATAR" : (type == KTP ? "KTP" : "SKCK")))
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();
    }


    PermissionListener permissionGetFotoListener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {

            EasyImage.openChooserWithGallery(VerifyDocumentActivity.this, getString(R.string.pick_source), 0);
        }

        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {

            String message = String.format(Locale.getDefault(), getString(R.string.message_denied), "WRITE STORAGE EKSTERNAL");
            Toast.makeText(VerifyDocumentActivity.this, message, Toast.LENGTH_SHORT).show();
        }


    };

    @OnClick(R.id.btn_continue)
    void LockWasher() {

        if (!Prefs.getAvatar(context) || !Prefs.getKtp(context) || !Prefs.getKtp(context)) {
            Toast.makeText(context, getString(R.string.please_complete_document), Toast.LENGTH_SHORT).show();
            return;
        }

        showNotif();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Iconify
                .with(new FontAwesomeModule())
                .with(new EntypoModule())
                .with(new MaterialModule())
                .with(new MaterialCommunityModule())
                .with(new SimpleLineIconsModule());
        setContentView(R.layout.activity_verify_document);
        ButterKnife.bind(this);
        context = getApplicationContext();
        dialogProgress = new ProgressDialogBuilder(this);
        setSupportActionBar(toolbar);

        EasyImage.configuration(this)
                .setImagesFolderName(getString(R.string.app_name))
                .saveInAppExternalFilesDir()
                .setCopyExistingPicturesToPublicLocation(true);

        imageLoader = new PicassoLoader();

        checkAvatar();
        checkKTP();
        checkSKCK();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                //Some error handling
            }

            @Override
            public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {
                //Handle the image
                setImage(imageFile);
            }

            @Override
            public void onCanceled(EasyImage.ImageSource source, int type) {
                //Cancel handling, you might wanna remove taken photo if it was canceled
                if (source == EasyImage.ImageSource.CAMERA) {
                    File photoFile = EasyImage.lastlyTakenButCanceledPhoto(VerifyDocumentActivity.this);
                    if (photoFile != null) photoFile.delete();
                }
            }
        });
    }

    private void setImage(File imageFile) {
        if (TYPE_IMG == AVATAR) {
            pathAvatar = imageFile;
            checkAvatar();
        } else if (TYPE_IMG == KTP) {
            pathKtp = imageFile;
            checkKTP();
        } else {
            pathSkck = imageFile;
            checkSKCK();
        }

        Upload();

    }


    private void checkAvatar() {
        if (pathAvatar != null) {
            Glide.with(this)
                    .load(pathAvatar)
                    .asBitmap()
                    .override(200, 200)
                    .centerCrop()
                    .into(washerPhoto);
        } else if (Prefs.getAvatar(context)) {
            Glide.with(this)
                    .load(Prefs.getAvatarFile(context))
                    .asBitmap()
                    .override(200, 200)
                    .centerCrop()
                    .into(washerPhoto);
        } else {
            imageLoader.loadImage(washerPhoto, Prefs.getProfilePhoto(this), Prefs.getFullName(this));
        }

    }

    private void checkKTP() {
        if (pathKtp != null) {
            layoutImgKtp.setVisibility(View.GONE);
            Picasso.with(this)
                    .load(pathKtp)
                    .resize(200, 200)
                    .centerCrop()
                    .into(imgKtp);
        } else if (Prefs.getKtp(context)) {
            layoutImgKtp.setVisibility(View.GONE);
            Glide.with(this)
                    .load(Prefs.getKTPFile(context))
                    .asBitmap()
                    .override(200, 200)
                    .centerCrop()
                    .into(imgKtp);
        } else {
            layoutImgKtp.setVisibility(View.VISIBLE);
            Picasso.with(this)
                    .load(R.drawable.placeholder)
                    .resize(200, 200)
                    .centerCrop()
                    .into(imgKtp);
        }

    }

    private void checkSKCK() {
        if (pathSkck != null) {
            layoutImgSkck.setVisibility(View.GONE);
            Picasso.with(this)
                    .load(pathSkck)
                    .resize(200, 200)
                    .centerCrop()
                    .into(imgSkck);
        } else if (Prefs.getSkck(context)) {
            layoutImgSkck.setVisibility(View.GONE);
            Glide.with(this)
                    .load(Prefs.getSkckFile(context))
                    .asBitmap()
                    .override(200, 200)
                    .centerCrop()
                    .into(imgSkck);
        } else {
            layoutImgSkck.setVisibility(View.VISIBLE);
            Picasso.with(this)
                    .load(R.drawable.placeholder)
                    .resize(200, 200)
                    .centerCrop()
                    .into(imgSkck);
        }

    }

    void Upload() {

        if (TYPE_IMG == AVATAR) {
            if (pathAvatar == null) {
                Toast.makeText(getApplicationContext(), getString(R.string.notif_select_file_avatar), Toast.LENGTH_LONG).show();
                return;
            }
        } else if (TYPE_IMG == KTP) {
            if (pathKtp == null) {
                Toast.makeText(getApplicationContext(), getString(R.string.notif_select_file_ktp), Toast.LENGTH_LONG).show();
                return;
            }
        } else {
            if (pathSkck == null) {
                Toast.makeText(getApplicationContext(), getString(R.string.notif_select_file_skck), Toast.LENGTH_LONG).show();
                return;
            }
        }


        dialogProgress.show(TYPE_IMG == AVATAR ? getString(R.string.upload_avatar) : (TYPE_IMG == KTP ? getString(R.string.upload_ktp) : getString(R.string.upload_skck)), getString(R.string.please_wait));


        PartFormString partFormString = new PartFormString(VerifyDocumentActivity.this);

        DocumentService paymentService = ApiUtils.DocumentService(getApplicationContext());

        Call<UploadDocument> ur;
        if (TYPE_IMG == AVATAR) {
            File compressedImageAvatar = Compressor.getDefault(VerifyDocumentActivity.this).compressToFile(pathAvatar);
            MultipartBody.Part bodyFileAvatar = partFormString.prepareFilePart(Sample.AVATAR, compressedImageAvatar);
            ur = paymentService.getUploadDocumentAvatarLink("Bearer " + Prefs.getToken(this), bodyFileAvatar, Prefs.getUserId(this));
        } else if (TYPE_IMG == KTP) {
            File compressedImageKtp = Compressor.getDefault(VerifyDocumentActivity.this).compressToFile(pathKtp);
            MultipartBody.Part bodyFileKtp = partFormString.prepareFilePart(Sample.KTP, compressedImageKtp);
            ur = paymentService.getUploadDocumentKtpLink("Bearer " + Prefs.getToken(this), bodyFileKtp, Prefs.getUserId(this));
        } else {
            File compressedImageSkck = Compressor.getDefault(VerifyDocumentActivity.this).compressToFile(pathSkck);
            MultipartBody.Part bodyFileSkck = partFormString.prepareFilePart(Sample.SKCK, compressedImageSkck);
            ur = paymentService.getUploadDocumentSkckLink("Bearer " + Prefs.getToken(this), bodyFileSkck, Prefs.getUserId(this));
        }

        ur.enqueue(new Callback<UploadDocument>() {
            @Override
            public void onResponse(Call<UploadDocument> call, Response<UploadDocument> response) {
                dialogProgress.hide();
                if (response.isSuccessful()) {
                    if (response.body().getStatus()) {

                        if (TYPE_IMG == AVATAR) {
                            Prefs.putProfilePhoto(VerifyDocumentActivity.this, response.body().getFileName());
                            Prefs.putAvatarFile(VerifyDocumentActivity.this, response.body().getFileName());
                            Prefs.putAvatar(VerifyDocumentActivity.this, true);
                        } else if (TYPE_IMG == KTP) {
                            Prefs.putKtpFile(VerifyDocumentActivity.this, response.body().getFileName());
                            Prefs.putKtp(VerifyDocumentActivity.this, true);
                        } else {
                            Prefs.putSkckFile(VerifyDocumentActivity.this, response.body().getFileName());
                            Prefs.putSkck(VerifyDocumentActivity.this, true);
                        }
                        Toast.makeText(context, response.body().getMessages(), Toast.LENGTH_SHORT).show();


                    }
                } else {
                    int statusCode = response.code();
                    try {
                        String json = response.errorBody().string();
                        GlobalError globalError = new Gson().fromJson(json, GlobalError.class);
                        if (!globalError.getStatus()) {
                            Toast.makeText(getApplicationContext(), globalError.getMessages(), Toast.LENGTH_LONG).show();
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                    }

                    if (TYPE_IMG == AVATAR) {
                        pathAvatar = null;
                        checkAvatar();
                    } else if (TYPE_IMG == KTP) {
                        pathKtp = null;
                        checkKTP();
                    } else {
                        pathSkck = null;
                        checkSKCK();
                    }

                }

            }

            @Override
            public void onFailure(Call<UploadDocument> call, Throwable t) {
                dialogProgress.hide();
                Toast.makeText(getApplicationContext(), "Failed sent", Toast.LENGTH_LONG).show();
                if (TYPE_IMG == AVATAR) {
                    pathAvatar = null;
                    checkAvatar();
                } else if (TYPE_IMG == KTP) {
                    pathKtp = null;
                    checkKTP();
                } else {
                    pathSkck = null;
                    checkSKCK();
                }
            }
        });

    }

    public void showNotif() {
        final Dialog dialog = new Dialog(this);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_complete_verify_document);
        dialog.findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });
        dialog.findViewById(R.id.btn_continue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialog != null) {
                    Prefs.putActivityIndex(context, Sample.VERIFY_TOOLS_INDEX);
                    toVerifyToolsActivity();
                    dialog.dismiss();
                }
            }
        });

        dialog.show();
    }


    private void toVerifyToolsActivity() {
        Intent intent = new Intent(this, VerifyToolsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
