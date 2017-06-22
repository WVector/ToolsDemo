package com.vector.libtools.ui;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;

import com.vector.libtools.AppContext;

/**
 * Created by Vector on 2016/8/10 0010.
 */
public class ResUtils {
    private static Resources sResources;

    private ResUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    private static Context getContext() {
        //context 的入口
        return AppContext.getInstance().getContext();
    }


    public static Resources getResources() {
        if (sResources == null) {
            sResources = getContext().getResources();
        }
        return sResources;
    }


    public static int getColor(@ColorRes int ResId) {
        return ContextCompat.getColor(getContext(), ResId);
    }

    public static String getString(int resId) {
        return getResources().getString(resId);
    }

    public static String[] getStringArray(int resId) {
        return getResources().getStringArray(resId);
    }

    public static ColorStateList getColorStateList(@ColorRes int resId) {
        return ContextCompat.getColorStateList(getContext(), resId);
    }

    public static Drawable getDrawable(@DrawableRes int resId) {
        return ContextCompat.getDrawable(getContext(), resId);
    }


}
