package com.vector.libtools.ui;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.vector.libtools.AppContext;

/**
 * Created by Vector on 2016/8/10 0010.
 */
public class FontUtils {

    private FontUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    private static Context getContext() {
        //context 的入口
        return AppContext.getInstance().getContext();
    }

    /**
     * 得到字体对象
     *
     * @param assetFontPath asset 下字体的路径
     * @return 字体对象
     */
    public static Typeface getTypeface(String assetFontPath) {
        return Typeface.createFromAsset(getContext().getAssets(), assetFontPath);
    }

    /**
     * 得到系统默认的字体,路径必须正确
     *
     * @return 默认的字体的对象
     */
    public static Typeface getDefaultTypeface() {
        String fontPath3 = "fonts/hanyi.ttf";
        return getTypeface(fontPath3);
    }

    /**
     * 更换viewgroup下全部的字体
     *
     * @param rootView 修改整个布局
     */
    public static void changeFont(ViewGroup rootView) {
        Typeface tf = getDefaultTypeface();
        for (int i = 0; i < rootView.getChildCount(); i++) {

            View v = rootView.getChildAt(i);
            if (v instanceof TextView) {
                ((TextView) v).setTypeface(tf);
            } else if (v instanceof Button) {
                ((Button) v).setTypeface(tf);
            } else if (v instanceof EditText) {
                ((EditText) v).setTypeface(tf);
            } else if (v instanceof ViewGroup) {
                changeFont((ViewGroup) v);
            }
            
        }
    }
}
