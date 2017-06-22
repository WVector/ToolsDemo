/*
 * Copyright (C) 2016 Facishare Technology Co., Ltd. All Rights Reserved.
 */
package com.vector.libtools;

import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Description:
 *
 * @author zhaozp
 * @since 2016-05-23
 */
public class RomUtils {
    private static final String TAG = RomUtils.class.getSimpleName();

    /**
     * 获取 emui 版本号
     *
     * @return
     */
    public static double getEmuiVersion() {
        try {
            String emuiVersion = getSystemProperty("ro.build.version.emui");
            String version = emuiVersion.substring(emuiVersion.indexOf("_") + 1);
            return Double.parseDouble(version);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 4.0;
    }

    /**
     * 只返回大版本号
     *
     * @return
     */
    public static String getEmotionUiVersionString() {
        try {
            String str = getSystemProperty("ro.build.version.emui");
            if (str != null) {
                return str.substring(str.indexOf('_') + 1, str.indexOf('.'));
            }
        } catch (Exception e) {
            Log.e(TAG, "get emui version code error");
        }
        return "unknown";
    }

    /**
     * 获取小米 rom 版本号，获取失败返回 -1
     *
     * @return miui rom version code, if onFailure , return -1
     */
    public static String getMiuiVersion() {
        String version = getSystemProperty("ro.miui.ui.version.name");
        if (version != null) {
            try {
                String s = version.toLowerCase();
                return s.substring(s.indexOf('v') + 1);
            } catch (Exception e) {
            }
        }
        return "UNKNOWN";
    }

    public static String getSystemProperty(String propName) {
        String line;
        BufferedReader input = null;
        try {
            Process p = Runtime.getRuntime().exec("getprop " + propName);
            input = new BufferedReader(new InputStreamReader(p.getInputStream()), 1024);
            line = input.readLine();
            input.close();
        } catch (IOException ex) {
            return null;
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                }
            }
        }
        return line;
    }

    public static boolean checkIsHuaweiRom() {
        return Build.MANUFACTURER.contains("HUAWEI");
    }

    /**
     * check if is miui ROM
     */
    public static boolean checkIsMiuiRom() {
        return !TextUtils.isEmpty(getSystemProperty("ro.miui.ui.version.name"));
    }

    public static boolean checkIsMeizuRom() {
        //return Build.MANUFACTURER.contains("Meizu");
        String meizuFlymeOSFlag = getSystemProperty("ro.build.display.id");
        if (TextUtils.isEmpty(meizuFlymeOSFlag)) {
            return false;
        } else if (meizuFlymeOSFlag.contains("flyme") || meizuFlymeOSFlag.toLowerCase().contains("flyme")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean checkIs360Rom() {
        //fix issue https://github.com/zhaozepeng/FloatWindowPermission/issues/9
        return Build.MANUFACTURER.contains("QiKU") || Build.MANUFACTURER.contains("360");
    }

    public static boolean checkIsOppoRom() {
        String a = getSystemProperty("ro.product.brand");
        return !TextUtils.isEmpty(a) && a.toLowerCase().contains("oppo");
    }

    /**
     * 判断是否为Vivo系统
     */
    public static boolean checkIsVivoRom() {
        String a = getSystemProperty("ro.vivo.os.name");
        return !TextUtils.isEmpty(a) && a.toLowerCase().contains("funtouch");
    }

    /**
     * 判断是否为中兴系统
     */
    public static boolean checkIsZTERom() {
        String manufacturer = Build.MANUFACTURER;
        if (!TextUtils.isEmpty(manufacturer)) {
            return manufacturer.toLowerCase().contains("nubia") || manufacturer.toLowerCase().contains("zte");
        }
        String fingerPrint = Build.FINGERPRINT;
        return !TextUtils.isEmpty(fingerPrint) && (fingerPrint.toLowerCase().contains("nubia") || fingerPrint.toLowerCase().contains("zte"));
    }

    /**
     * 判断是否是三星
     *
     * @return
     */
    public static boolean checkIsSM() {
        String manufacturer = Build.MANUFACTURER;
        return !TextUtils.isEmpty(manufacturer) && "samsung".equals(manufacturer.toLowerCase());

    }

    /**
     * 判断是否是三星
     *
     * @return
     */
    public static boolean checkIsCMDC() {
        String manufacturer = Build.MANUFACTURER;
        return !TextUtils.isEmpty(manufacturer) && "cmdc".equals(manufacturer.toLowerCase());

    }
}
