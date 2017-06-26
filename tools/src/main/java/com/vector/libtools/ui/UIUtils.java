package com.vector.libtools.ui;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.vector.libtools.AppContext;

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

    public static void postDelayed(long delayTime, Runnable runnable) {
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
     *
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
