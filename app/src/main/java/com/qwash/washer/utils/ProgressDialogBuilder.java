package com.qwash.washer.utils;

import android.app.Activity;
import android.app.ProgressDialog;

public class ProgressDialogBuilder {

    private final Activity activity;
    private ProgressDialog dialogProgress;

    public ProgressDialogBuilder(Activity activity) {
        this.activity = activity;
    }

    public void show(String title, String message) {
        dialogProgress = ProgressDialog.show(activity, title,
                message, true);
    }

    public void hide() {
        if (dialogProgress != null)
            dialogProgress.dismiss();
    }
}
