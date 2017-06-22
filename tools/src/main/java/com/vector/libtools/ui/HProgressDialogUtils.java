package com.vector.libtools.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.text.TextUtils;

/**
 * Created by Vector on 2016/8/12 0012.
 */
public class HProgressDialogUtils {
    private static ProgressDialog sHorizontalProgressDialog;

    private HProgressDialogUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    @SuppressLint("NewApi")
    public static void showHorizontalProgressDialog(Activity context, String msg) {
        cancel();

        if (sHorizontalProgressDialog == null) {
            sHorizontalProgressDialog = new ProgressDialog(context);
            sHorizontalProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            sHorizontalProgressDialog.setCanceledOnTouchOutside(false);
            sHorizontalProgressDialog.setProgressNumberFormat("%2dMB/%1dMB");

        }
        if (!TextUtils.isEmpty(msg)) {
            sHorizontalProgressDialog.setMessage(msg);
        }
        sHorizontalProgressDialog.show();

    }

    public static void setMax(long total) {
        if (sHorizontalProgressDialog != null) {
            sHorizontalProgressDialog.setMax(((int) total) / (1024 * 1024));
        }
    }

    public static void cancel() {
        if (sHorizontalProgressDialog != null) {
            sHorizontalProgressDialog.dismiss();
            sHorizontalProgressDialog = null;
        }
    }

    public static void onLoading(long total, long current) {
        if (sHorizontalProgressDialog == null) {
            return;
        }
        if (current == 0) {
            sHorizontalProgressDialog.setMax(((int) total) / (1024 * 1024));
        }
        sHorizontalProgressDialog.setProgress(((int) current) / (1024 * 1024));
        if (sHorizontalProgressDialog.getProgress() >= sHorizontalProgressDialog.getMax()) {
            sHorizontalProgressDialog.dismiss();
            sHorizontalProgressDialog = null;
        }
    }
}
