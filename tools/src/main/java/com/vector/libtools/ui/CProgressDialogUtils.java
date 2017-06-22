package com.vector.libtools.ui;

import android.app.Activity;
import android.app.ProgressDialog;

/**
 * Created by Vector on 2016/8/12 0012.
 */
public class CProgressDialogUtils {
    private static final String TAG = CProgressDialogUtils.class.getSimpleName();

    private CProgressDialogUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    private static ProgressDialog sCircleProgressDialog;

    public static void showProgressDialog(Activity activity) {
        showProgressDialog(activity, "加载中", false);
    }


    public static void showProgressDialog(Activity activity, String msg) {
        showProgressDialog(activity, msg, false);
    }

    public static void showProgressDialog(final Activity activity, String msg, boolean cancelable) {
        if (activity == null || activity.isFinishing()) {
            return;
        }


        if (sCircleProgressDialog == null) {
            sCircleProgressDialog = new ProgressDialog(activity);
            sCircleProgressDialog.setMessage(msg);
            sCircleProgressDialog.setCancelable(cancelable);
        } else {
            if (activity.equals(sCircleProgressDialog.getOwnerActivity())) {
                sCircleProgressDialog.setMessage(msg);
                sCircleProgressDialog.setCancelable(cancelable);

            } else {
                //不相等
                cancelProgressDialog();
                sCircleProgressDialog = new ProgressDialog(activity);
                sCircleProgressDialog.setMessage(msg);
                sCircleProgressDialog.setCancelable(cancelable);
            }
        }

        if (!sCircleProgressDialog.isShowing()) {
            sCircleProgressDialog.show();
        }

//        sCircleProgressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
//            @Override
//            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
//                if (KeyEvent.KEYCODE_BACK == keyEvent.getKeyCode()) {
//                    if (!activity.isFinishing()) {
//                        cancelProgressDialog();
//                        activity.finish();
//                        return true;
//                    }
//                }
//                return false;
//            }
//        });


    }


    public static void cancelProgressDialog() {

        if (sCircleProgressDialog != null && sCircleProgressDialog.isShowing()) {
            sCircleProgressDialog.cancel();
            sCircleProgressDialog = null;
        }
    }

}
