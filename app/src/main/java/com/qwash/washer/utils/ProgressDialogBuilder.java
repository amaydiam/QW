package com.qwash.washer.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.widget.TextView;

import com.qwash.washer.R;
import com.qwash.washer.ui.widget.RobotoRegularTextView;

public class ProgressDialogBuilder {

    private final Activity activity;
    private ProgressDialog dialogProgress;

    public ProgressDialogBuilder(Activity activity) {
        this.activity = activity;
    }

    public void show(String tt, String msg) {/*
        dialogProgress = ProgressDialog.show(activity, title,
                message, true);*/

        dialogProgress = new ProgressDialog(activity);
        dialogProgress.show();
        dialogProgress.setContentView(R.layout.custom_progressdialog);
        RobotoRegularTextView title = (RobotoRegularTextView) dialogProgress.findViewById(R.id.title);
        RobotoRegularTextView message = (RobotoRegularTextView) dialogProgress.findViewById(R.id.message);
        title.setText(tt);
        message.setText(msg);
    }

    public void hide() {
        if (dialogProgress != null)
            dialogProgress.dismiss();
    }
}
