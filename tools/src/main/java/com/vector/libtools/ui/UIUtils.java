package com.vector.libtools.ui;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.vector.libtools.AppContext;

import java.util.Random;

/**
 * Created by Vector on 2016/8/10 0010.
 */
public class UIUtils {
    private UIUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }


    private static Context getContext() {
        //context 的入口
        return AppContext.getInstance().getContext();
    }

    public static Thread getMainThread() {
        return AppContext.getInstance().getMainThread();
    }

    public static int getMainThreadId() {
        return AppContext.getInstance().getMainThreadId();
    }

    public static Handler getHandler() {
        return AppContext.getInstance().getHandler();
    }


    public static boolean isRunningInMainThread() {
        return getMainThreadId() == android.os.Process.myTid();
    }

    public static void runInMainThread(Runnable runnable) {
        if (isRunningInMainThread()) {
            runnable.run();
        } else {
            getHandler().post(runnable);
        }
    }

    /**
     * 延时执行任务
     *
     * @param runnable  任务
     * @param delayTime 延时
     */

    public static void postDelayed(long delayTime,Runnable runnable ) {
        getHandler().postDelayed(runnable, delayTime);
    }

    /**
     * 移除指定的任务
     *
     * @param runnable 任务
     */
    public static void removeCallback(Runnable runnable) {
        // 移除在当前handler中维护的任务(传递进来的任务)
        getHandler().removeCallbacks(runnable);

    }

    /**
     * 移除所有的任务和消息 一般要界面的销毁中吊销
     */
    public static void removeAllCallbacksAndMessages() {
        getHandler().removeCallbacksAndMessages(null);
    }


    /**
     * 这个也是合法的, 但是Inflate出来的View是根据你当前系统的默认主题(Theme)的, 而非你的Application所使用的主题.
     * @param layoutId
     * @return
     */
    public static View inflate(int layoutId) {
        return View.inflate(getContext(), layoutId, null);

    }

    public static View inflate(int layoutId, ViewGroup parent) {
        return LayoutInflater.from(getContext()).inflate(layoutId, parent, false);
    }

    public static View inflate(int layoutId, ViewGroup parent, boolean attachToRoot) {
        return LayoutInflater.from(getContext()).inflate(layoutId, parent, attachToRoot);
    }


    /**
     * 按条件的到随机颜色
     *
     * @param alpha 透明
     * @param lower 下边界
     * @param upper 上边界
     * @return 颜色值
     */

    public static int getRandomArgb(int alpha, int lower, int upper) {
        if (upper > 255) upper = 255;

        if (lower < 0) lower = 0;

        //随机数是前闭  后开

        int red = lower + new Random().nextInt(upper - lower + 1);
        int green = lower + new Random().nextInt(upper - lower + 1);
        int blue = lower + new Random().nextInt(upper - lower + 1);


        return Color.argb(alpha, red, green, blue);
    }

    /**
     * 根据颜色和圆角
     *
     * @param rgb 颜色
     * @param r   圆角
     * @return 圆角矩形
     */
    public static Drawable getRectDrawable(int rgb, int r) {
        //
        GradientDrawable gradientDrawable = new GradientDrawable();
        // 设置绘画图片色值
        gradientDrawable.setColor(rgb);
        // 绘画的是矩形
        gradientDrawable.setGradientType(GradientDrawable.RECTANGLE);
        // 设置矩形的圆角半径
        gradientDrawable.setCornerRadius(r);

        return gradientDrawable;
    }

    /**
     * 背景选择器
     *
     * @param pressDrawable  按下状态的Drawable
     * @param normalDrawable 正常状态的Drawable
     * @return 状态选择器
     */
    public static StateListDrawable getStateListDrawable(Drawable pressDrawable, Drawable normalDrawable) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_enabled, android.R.attr.state_pressed}, pressDrawable);
        stateListDrawable.addState(new int[]{android.R.attr.state_enabled}, normalDrawable);
        stateListDrawable.addState(new int[]{}, normalDrawable);
        return stateListDrawable;
    }

    /**
     * @param cornerRadius 圆角半径
     * @param pressedArgb  按下颜色
     * @param normalArgb   正常的颜色
     * @return 状态选择器
     */
    public static StateListDrawable getDrawable(int cornerRadius, int pressedArgb, int normalArgb) {

        return getStateListDrawable(getRectDrawable(pressedArgb, cornerRadius), getRectDrawable(normalArgb, cornerRadius));
    }


    /**
     * 得到随机背景色并且带选择器, 并且可以设置圆角
     *
     * @param cornerRadius 圆角
     * @param alpha        随机颜色的透明度
     * @param lower        随机颜色的下边界 lower>=0
     * @param upper        随机颜色的上边界 upper<=255
     * @param normalArgb   正常的颜色
     * @return 状态选择器
     */
    public static StateListDrawable getRandomDrawable(int cornerRadius, int alpha, int lower, int upper, int normalArgb) {

        return getDrawable(cornerRadius, new RandomColor(alpha, lower, upper).getColor(), normalArgb);

    }

    /**
     * 得到随机背景色并且带选择器, 并且可以设置圆角
     *
     * @param cornerRadius 圆角
     * @param randomColor  随机颜色
     * @param normalArgb   正常的颜色
     * @return 状态选择器
     */
    public static StateListDrawable getRandomDrawable(int cornerRadius, RandomColor randomColor, int normalArgb) {

        return getDrawable(cornerRadius, randomColor.getColor(), normalArgb);

    }

    /**
     * 随机颜色
     */
    public static class RandomColor {
        int alpha;
        int lower;
        int upper;

        public RandomColor(int alpha, int lower, int upper) {
            if (upper <= lower) {
                throw new IllegalArgumentException("must be lower < upper");
            }
            setAlpha(alpha);
            setLower(lower);
            setUpper(upper);

        }

        public int getColor() {

            //随机数是前闭  后开

            int red = getLower() + new Random().nextInt(getUpper() - getLower() + 1);
            int green = getLower() + new Random().nextInt(getUpper() - getLower() + 1);
            int blue = getLower() + new Random().nextInt(getUpper() - getLower() + 1);


            return Color.argb(getAlpha(), red, green, blue);
        }

        public int getAlpha() {
            return alpha;
        }

        public void setAlpha(int alpha) {
            if (alpha > 255) alpha = 255;
            if (alpha < 0) alpha = 0;
            this.alpha = alpha;
        }

        public int getLower() {
            return lower;
        }

        public void setLower(int lower) {
            if (lower < 0) lower = 0;
            this.lower = lower;
        }

        public int getUpper() {
            return upper;
        }

        public void setUpper(int upper) {
            if (upper > 255) upper = 255;
            this.upper = upper;
        }
    }

    /**
     * 颜色选择器
     *
     * @param pressedArgb 按下的颜色
     * @param normalArgb  正常的颜色
     * @return 颜色选择器
     */
    public static ColorStateList getColorStateList(int pressedArgb, int normalArgb) {

        return new ColorStateList(
                new int[][]{{android.R.attr.state_enabled, android.R.attr.state_pressed}, {android.R.attr.state_enabled}},
                new int[]{pressedArgb, normalArgb});
    }


    /**
     * 得到空心的效果，一般作为默认的效果
     *
     * @param cornerRadius 圆角半径
     * @param solidArgb    实心颜色
     * @param strokeArgb   边框颜色
     * @param strokeWidth  边框宽度
     * @return 得到空心效果
     */
    public static GradientDrawable getStrokeRectDrawable(int cornerRadius, int solidArgb, int strokeArgb, int strokeWidth) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setStroke(strokeWidth, strokeArgb);
        gradientDrawable.setColor(solidArgb);
        gradientDrawable.setCornerRadius(cornerRadius);
        gradientDrawable.setGradientType(GradientDrawable.RECTANGLE);
        return gradientDrawable;

    }

    /**
     * 得到实心的drawable, 一般作为选中，点中的效果
     *
     * @param cornerRadius 圆角半径
     * @param solidArgb    实心颜色
     * @return 得到实心效果
     */
    public static GradientDrawable getSolidRectDrawable(int cornerRadius, int solidArgb) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setCornerRadius(cornerRadius);
        gradientDrawable.setColor(solidArgb);
        gradientDrawable.setGradientType(GradientDrawable.RECTANGLE);
        return gradientDrawable;
    }

    /**
     * 得到带边框的状态选择器
     *
     * @param cornerRadiusPX 圆角半径
     * @param strokeWidthPX  边框宽度
     * @param strokeArgb     表框颜色
     * @param solidArgb      实心颜色
     * @return 状态选择器
     */
    public static StateListDrawable getDrawable(int cornerRadiusPX, int strokeWidthPX, int strokeArgb, int solidArgb) {
        return getStateListDrawable(getSolidRectDrawable(cornerRadiusPX, strokeArgb),
                getStrokeRectDrawable(cornerRadiusPX, solidArgb, strokeArgb, strokeWidthPX));
    }

    public static SpannableString getColorSpannableString(String msg, int color) {
        SpannableString spannableString = new SpannableString(msg);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(color);
        spannableString.setSpan(colorSpan, 0, msg.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }


    public static void setListViewHeightBasedOnChildren(ListView listView, ViewPager viewPager) {

        ListAdapter listAdapter = listView.getAdapter();

        if (listAdapter == null) {

            return;

        }

        int totalHeight = 0;

        for (int i = 0; i < listAdapter.getCount(); i++) {

            View listItem = listAdapter.getView(i, null, listView);

            listItem.measure(0, 0);

            totalHeight += listItem.getMeasuredHeight();

        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();

        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));


        ViewGroup.LayoutParams layoutParams = viewPager.getLayoutParams();
        layoutParams.height = totalHeight;
        viewPager.setLayoutParams(params);
        listView.setLayoutParams(params);

    }


    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        //
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            //重新绘画，获取到每个的ListView的高度
            totalHeight += listItem.getMeasuredHeight();

        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        //还要累加，分割线的高度
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // params.height += 5;// if without this statement,the listview will
        // be
        // a
        // little short
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);

    }



}
