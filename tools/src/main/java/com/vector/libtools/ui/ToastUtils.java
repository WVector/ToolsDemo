package com.vector.libtools.ui;

import android.content.Context;
import android.widget.Toast;

import com.vector.libtools.AppContext;

/**
 * Created by Vector on 2016/8/12 0012.
 */
public class ToastUtils {
    private ToastUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }
    private static Context getContext() {
        //context 的入口
        return AppContext.getInstance().getContext();
    }

    /**
     * 吐司
     */
    public static Toast mToast;

    /**
     * 吐司
     *
     * @param msg
     */
    public static void toast(String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(getContext(), "", Toast.LENGTH_SHORT);
        }
        mToast.setText(msg);
        mToast.show();
    }

}
