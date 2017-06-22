package com.vector.libtools;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import com.vector.libtools.filemanager.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.UnsupportedEncodingException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.UUID;


public class DeviceUtils {
    private static final boolean DEBUG = BuildConfig.DEBUG;
    private static final String TAG = DeviceUtils.class.getSimpleName();
    private static final String UNKNOWN = "UNKNOWN";


    //      Build.BOARD // 主板
    //      Build.BRAND // Android系统定制商
    //      Build.CPU_ABI // cpu指令集
    //      Build.DEVICE // 设备参数
    //      Build.DISPLAY // 显示屏参数
    //      Build.FINGERPRINT // 硬件名称
    //      Build.HOST
    //      Build.ID // 修订版本列表
    //      Build.MANUFACTURER // 硬件制造商
    //      Build.MODEL // 版本
    //      Build.PRODUCT // 手机制造商
    //      Build.TAGS // 描述build的标签
    //      Build.TIME
    //      Build.TYPE // builder类型
    //      Build.USER


    private DeviceUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    private static Context getContext() {
        //context 的入口
        return AppContext.getInstance().getContext();
    }

    /**
     * android.permission.INTERNET
     *
     * @return
     */
    public static String getMacAddress() {
        try {
            NetworkInterface NIC = NetworkInterface.getByName("wlan0");
            byte[] b = NIC.getHardwareAddress();
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < b.length; i++) {
                if (i != 0) {
                    buffer.append(':');
                }
                String str = Integer.toHexString(b[i] & 0xFF);
                buffer.append(str.length() == 1 ? 0 + str : str);
            }
            return buffer.toString().toUpperCase();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return UNKNOWN;
    }

    /**
     * 获取设备MAC地址 <23
     * <p>需添加权限 android.permission.ACCESS_WIFI_STATE</p>
     * 早在 Android M 预览版发布时就有人发现，通过WifiInfo.getMacAddress()获取的MAC地址是一个“假”的固定值，其值为 “02:00:00:00:00:00”。对于这个，官方的说法当然不外乎“保护用户隐私数据”。
     *
     * @return MAC地址
     */
    @Deprecated
    private static String getMacAddress2() {
        WifiManager wifi = (WifiManager) getContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        String macAddress = info.getMacAddress();
        return TextUtils.isEmpty(macAddress) || "02:00:00:00:00:00".equals(macAddress) ? getMacAddress1() : macAddress;
    }

    /**
     * 获取设备MAC地址
     * <p>需添加权限 android.permission.ACCESS_WIFI_STATE</p>
     * 当手机未开启wifi的时候不能获取到
     * 小米5 android 7.0 获取不到
     *
     * @return MAC地址
     */
    @Deprecated
    private static String getMacAddress1() {
        try {
            WifiManager wifi = (WifiManager) getContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            if (!wifi.isWifiEnabled()) {
                wifi.setWifiEnabled(true);
                //开启并不会立刻生效
            }
            Process pp = Runtime.getRuntime().exec("cat /sys/class/net/wlan0/address");
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader reader = new LineNumberReader(ir);
            String mac = reader.readLine();
            reader.close();
            return TextUtils.isEmpty(mac) ? UNKNOWN : mac;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return UNKNOWN;
    }

    /**
     * 获取设备厂商，如Xiaomi
     *
     * @return 设备厂商
     */
    public static String getManufacturer() {
        return Build.MANUFACTURER;
    }

    /**
     * 获取设备型号，如MI2SC
     *
     * @return 设备型号
     */
    public static String getModel() {
        String model = Build.MODEL;
        return model == null ? "" : model.trim().replaceAll("\\s*", "");
    }

    /**
     * 获取Android的系统版本号
     */
    public static String getAndroidOsVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * Android系统2.3版本以上可以通过下面的方法得到Serial Number
     */
    public static String getSerialNumber() {
        return Build.SERIAL;
    }

    /**
     * 判断当前设备是否为手机，如果是平板或是机顶盒其他不具备通话功能的设备，测返回false
     * <p>需添加权限 android.permission.READ_PHONE_STATE</p>
     *
     * @return 是否为手机
     */
    public static boolean isPhone() {
        TelephonyManager telephony = (TelephonyManager) getContext().getSystemService(Context.TELEPHONY_SERVICE);
        return !(telephony.getPhoneType() == TelephonyManager.PHONE_TYPE_NONE);
    }

    /**
     * 获取手机的imei,有的手机可能不让获取imei
     * <p>需添加权限 android.permission.READ_PHONE_STATE</p>
     *
     * @return
     */
    public static String getDeviceIMEI() {
        try {
            if (isPhone()) {
                TelephonyManager telephony = (TelephonyManager) getContext().getSystemService(Context.TELEPHONY_SERVICE);
                String deviceId = telephony.getDeviceId();
                if (!TextUtils.isEmpty(deviceId)) {
                    return deviceId;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 设备首次启动时，系统会随机生成一个64位的数字，并把这个数字以16进制字符串的形式保存下来，这个16进制的字符串就是ANDROID_ID
     */
    public static String getAndroidId() {
        String androidId = Settings.Secure.getString(getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        return TextUtils.isEmpty(androidId) || "9774d56d682e549c".equals(androidId) ? "" : androidId;
    }


    /**
     * 获取设备SD卡是否可用
     *
     * @return true : 可用<br>false : 不可用
     */
    public static boolean isSDCardEnable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }

    /**
     * 获取设备SD卡路径
     * <p>一般是/storage/emulated/0/</p>
     *
     * @return SD卡路径
     */
    public static String getSDCardPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
    }

    /**
     * 获取手机状态信息
     * <p>需添加权限 android.permission.READ_PHONE_STATE</p>
     *
     * @return DeviceId(IMEI) = 99000311726612<br>
     * DeviceSoftwareVersion = 00<br>
     * Line1Number =<br>
     * NetworkCountryIso = cn<br>
     * NetworkOperator = 46003<br>
     * NetworkOperatorName = 中国电信<br>
     * NetworkType = 6<br>
     * honeType = 2<br>
     * SimCountryIso = cn<br>
     * SimOperator = 46003<br>
     * SimOperatorName = 中国电信<br>
     * SimSerialNumber = 89860315045710604022<br>
     * SimState = 5<br>
     * SubscriberId(IMSI) = 460030419724900<br>
     * VoiceMailNumber = *86<br>
     */
    public static String getPhoneStatus() {
        TelephonyManager tm = (TelephonyManager) getContext().getSystemService(Context.TELEPHONY_SERVICE);
        String str = "";
        str += "DeviceId(IMEI) = " + tm.getDeviceId() + "\n";
        str += "DeviceSoftwareVersion = " + tm.getDeviceSoftwareVersion() + "\n";
        str += "Line1Number = " + tm.getLine1Number() + "\n";
        str += "NetworkCountryIso = " + tm.getNetworkCountryIso() + "\n";
        str += "NetworkOperator = " + tm.getNetworkOperator() + "\n";
        str += "NetworkOperatorName = " + tm.getNetworkOperatorName() + "\n";
        str += "NetworkType = " + tm.getNetworkType() + "\n";
        str += "honeType = " + tm.getPhoneType() + "\n";
        str += "SimCountryIso = " + tm.getSimCountryIso() + "\n";
        str += "SimOperator = " + tm.getSimOperator() + "\n";
        str += "SimOperatorName = " + tm.getSimOperatorName() + "\n";
        str += "SimSerialNumber = " + tm.getSimSerialNumber() + "\n";
        str += "SimState = " + tm.getSimState() + "\n";
        str += "SubscriberId(IMSI) = " + tm.getSubscriberId() + "\n";
        str += "VoiceMailNumber = " + tm.getVoiceMailNumber() + "\n";
        return str;
    }

    private static final String PREFS_FILE = "device_id.xml";
    private static final String PREFS_DEVICE_ID = "device_id";

    /**
     * 得到设置的唯一id
     * <p>需添加权限 android.permission.READ_PHONE_STATE</p>
     */
    public static String getDeviceId() {
        String deviceId = "";

        //先从sp中取
        deviceId = getDeviceIdFromSP();
        if (DEBUG) {
            Log.d(TAG, "device Id from sp deviceId : " + deviceId);
        }
        //相互Cache 最大可能保证唯一性
        if (!TextUtils.isEmpty(deviceId)) {
            cacheDeviceId2ExternalStorageInChildThread(deviceId);
            return deviceId;
        }

        //失败则，sd中取
        deviceId = getDeviceIdFromExternalStorage();
        if (DEBUG) {
            Log.d(TAG, "device Id from sd deviceId:" + deviceId);
        }
        if (!TextUtils.isEmpty(deviceId)) {
            cacheDeviceId2SPInChildThread(deviceId);
            return deviceId;
        }

        //AndroidId
        deviceId = uuidFormat(getAndroidId());
        if (DEBUG) {
            Log.d(TAG, "device Id from androidId deviceId:" + deviceId);
        }
        if (!TextUtils.isEmpty(deviceId)) {
            cacheDeviceId2SPInChildThread(deviceId);
            cacheDeviceId2ExternalStorageInChildThread(deviceId);
            return deviceId;
        }

        //androidIMEI
        deviceId = uuidFormat(getDeviceIMEI());
        if (DEBUG) {
            Log.d(TAG, "device Id from androidIMEI deviceId:" + deviceId);
        }
        if (!TextUtils.isEmpty(deviceId)) {
            cacheDeviceId2SPInChildThread(deviceId);
            cacheDeviceId2ExternalStorageInChildThread(deviceId);
            return deviceId;
        }

        //UUID
        deviceId = getDeviceIdUUID();
        if (DEBUG) {
            Log.d(TAG, "device Id from uuid deviceId:" + deviceId);
        }
        if (!TextUtils.isEmpty(deviceId)) {
            cacheDeviceId2SPInChildThread(deviceId);
            cacheDeviceId2ExternalStorageInChildThread(deviceId);
            return deviceId;
        }

        return deviceId;
    }

    /**
     * @return 生成随机数
     */
    private static String getDeviceIdUUID() {
        return UUID.randomUUID().toString();
    }


    private static String uuidFormat(String temp) {
        if (!TextUtils.isEmpty(temp)) {
            try {
                return UUID.nameUUIDFromBytes(temp.getBytes("UTF-8")).toString();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    private static void cacheDeviceId2ExternalStorageInChildThread(@Nullable final String deviceId) {
        ThreadManager.getThreadProxyPool().execute(new Runnable() {
            @Override
            public void run() {
                cacheDeviceId2ExternalStorage(deviceId);
            }
        });
    }

    private static boolean cacheDeviceId2ExternalStorage(@Nullable String deviceId) {

        if (TextUtils.isEmpty(deviceId) || !isSDCardEnable()) {
            return false;
        }


        File dir = new File(getExternalStorageDeviceIdPath());


        if (!dir.exists()) {
            dir.mkdirs();
        }


        FileWriter fw = null;
        try {
            File file = new File(dir, PREFS_FILE);
            if (!file.exists()) {
                file.getParentFile().mkdir();
                file.createNewFile();
            }


            fw = new FileWriter(file);

            fw.write(PREFS_DEVICE_ID + "=" + deviceId);
            fw.flush();
            fw.close();
            if (DEBUG) {
                Log.d(TAG, "cache to sd deviceId: " + deviceId);
            }
            return true;

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            try {
                if (fw != null) {
                    fw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return false;

    }

    private static void cacheDeviceId2SPInChildThread(@Nullable final String deviceId) {
        ThreadManager.getThreadProxyPool().execute(new Runnable() {
            @Override
            public void run() {
                cacheDeviceId2SP(deviceId);
            }
        });
    }

    private static boolean cacheDeviceId2SP(@Nullable String deviceId) {
        if (TextUtils.isEmpty(deviceId)) {
            return false;
        }
        try {
            getSP().edit().putString(PREFS_DEVICE_ID, deviceId).apply();
            if (DEBUG) {
                Log.d(TAG, "cache to sp deviceId:" + deviceId);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private static String getExternalStorageDeviceIdPath() {
        return FileUtils.getConfigPath();
    }

    private static String getDeviceIdFromExternalStorage() {


        if (!isSDCardEnable()) {
            return "";
        }


        File file = new File(getExternalStorageDeviceIdPath(), PREFS_FILE);

        if (file.exists() && file.isFile()) {

            //从外部文件中读取deviceId
            BufferedReader br = null;
            try {

                br = new BufferedReader(new FileReader(file));
                String s = "";
                String buffer;
                while ((buffer = br.readLine()) != null) {
                    s += buffer;
                }

                if (s.startsWith(PREFS_DEVICE_ID)) {
                    //读取并且保存到sp中
                    return s.replaceAll(PREFS_DEVICE_ID + "=", "").trim();
                }

                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (br != null) {
                        br.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }

    private static String getDeviceIdFromSP() {
        return getSP().getString(PREFS_DEVICE_ID, "");
    }

    private static SharedPreferences getSP() {
        return getContext().getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
    }

}
