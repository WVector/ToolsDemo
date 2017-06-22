package com.vector.libtools.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.vector.libtools.AppContext;

/**
 * Created by fengjunming_t on 2016/7/9 0009.
 */
public class WebViewUtils {

    private WebViewUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    private static Context getContext() {
        //context 的入口
        return AppContext.getInstance().getContext();
    }

    @SuppressLint("SetJavaScriptEnabled")
    public static void initWebView(WebView webview) {
        WebSettings webSetting = webview.getSettings();


        webSetting.setAllowFileAccess(false);// 设置允许访问文件数据
        webSetting.setSavePassword(false); // 设置是否保存密码


        //webSetting.setPluginState(PluginState.ON);
        //webSetting.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //webSetting.setTextSize(TextSize.NORMAL);
        //webSetting.setDefaultFontSize(size);
        //webSetting.setDefaultZoom(ZoomDensity.FAR);
        //webSetting.setTextZoom(textZoom);

        //自适应屏幕
        //设置此属性，可任意比例缩放
        //webSetting.setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);
        //webSetting.setUseWideViewPort(true);
        //webSetting.setLoadWithOverviewMode(true);


        webview.setVerticalScrollBarEnabled(true);
        webview.setVerticalScrollbarOverlay(true);
        webview.setScrollBarStyle(WebView.SCROLLBARS_INSIDE_OVERLAY);
        webview.setLongClickable(true);
        webview.setOverScrollMode(WebView.OVER_SCROLL_NEVER);//
        // webview.setBackgroundColor(0xFFFFFFFF);


        webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);

        webSetting.setSupportZoom(false);
        webSetting.setBuiltInZoomControls(false);
        webSetting.setUseWideViewPort(true);
        //	  if (K9.autofitWidth()) {
        webSetting.setLoadWithOverviewMode(true);
        //	  }
        PackageManager pm = getContext().getPackageManager();
        boolean supportsMultiTouch = pm.hasSystemFeature(PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH)
                || pm.hasSystemFeature(PackageManager.FEATURE_FAKETOUCH_MULTITOUCH_DISTINCT);

        webSetting.setDisplayZoomControls(!supportsMultiTouch);

        webSetting.setJavaScriptEnabled(false);
        webSetting.setLoadsImagesAutomatically(true);
        webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);

        // TODO:  Review alternatives.  NARROW_COLUMNS is deprecated on KITKAT
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);


//	  	  webSettings.setTextZoom(K9.getFontSizes().getMessageViewContentAsPercent());

        // Disable network images by default.  This is overridden by preferences.

        webSetting.setBlockNetworkLoads(false);
    }
}
