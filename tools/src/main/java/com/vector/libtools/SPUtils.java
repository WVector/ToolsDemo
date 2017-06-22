package com.vector.libtools;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/8/2
 *     desc  : SP读写工具类
 * </pre>
 */
public class SPUtils {

    private static SharedPreferences sSp;

    private SPUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    private static Context getContext() {
        //context 的入口
        return AppContext.getInstance().getContext();
    }

    /**
     * SP的name值
     * <p>可通过修改PREFERENCE_NAME变量修改SP的name值</p>
     */
    public static String PREFERENCE_NAME = "ANCROID_UTIL_CODE";

    /**
     * SP中写入String类型value
     *
     * @param key   键
     * @param value 值
     * @return true: 写入成功<br>false: 写入失败
     */
    public static boolean putString(String key, String value) {
        return getSP().edit().putString(key, value).commit();
    }

    /**
     * SP中读取String
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值null
     */
    public static String getString(String key) {
        return getString(key, null);
    }

    /**
     * SP中读取String
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值defaultValue
     */
    public static String getString(String key, String defaultValue) {
        return getSP().getString(key, defaultValue);
    }


    /**
     * SP中读取String
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值defaultValue
     */
    public static Set<String> getStringSet(String key, Set<String> defaultValue) {
        return getSP().getStringSet(key, defaultValue);
    }

    /**
     * SP中写入int类型value
     *
     * @param key   键
     * @param value 值
     * @return true: 写入成功<br>false: 写入失败
     */
    public static boolean setStringSet(String key, Set<String> value) {
        return getSP().edit().putStringSet(key, value).commit();
    }


    /**
     * SP中写入int类型value
     *
     * @param key   键
     * @param value 值
     * @return true: 写入成功<br>false: 写入失败
     */
    public static boolean putInt(String key, int value) {
        return getSP().edit().putInt(key, value).commit();
    }

    /**
     * SP中读取int
     *
     * @param context 上下文
     * @param key     键
     * @return 存在返回对应值，不存在返回默认值-1
     */
    public static int getInt(String key) {
        return getInt(key, -1);
    }

    /**
     * SP中读取int
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值defaultValue
     */
    public static int getInt(String key, int defaultValue) {
        return getSP().getInt(key, defaultValue);
    }

    /**
     * SP中写入long类型value
     *
     * @param key   键
     * @param value 值
     * @return true: 写入成功<br>false: 写入失败
     */
    public static boolean putLong(String key, long value) {
        return getSP().edit().putLong(key, value).commit();
    }

    /**
     * SP中读取long
     *
     * @param context 上下文
     * @param key     键
     * @return 存在返回对应值，不存在返回默认值-1
     */
    public static long getLong(String key) {
        return getLong(key, -1);
    }

    /**
     * SP中读取long
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值defaultValue
     */
    public static long getLong(String key, long defaultValue) {
        return getSP().getLong(key, defaultValue);
    }

    /**
     * SP中写入float类型value
     *
     * @param key   键
     * @param value 值
     * @return true: 写入成功<br>false: 写入失败
     */
    public static boolean putFloat(String key, float value) {
        return getSP().edit().putFloat(key, value).commit();
    }

    /**
     * SP中读取float
     *
     * @param context 上下文
     * @param key     键
     * @return 存在返回对应值，不存在返回默认值-1
     */
    public static float getFloat(String key) {
        return getFloat(key, -1);
    }

    /**
     * SP中读取float
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值defaultValue
     */
    public static float getFloat(String key, float defaultValue) {
        return getSP().getFloat(key, defaultValue);
    }

    /**
     * SP中写入boolean类型value
     *
     * @param key   键
     * @param value 值
     * @return true: 写入成功<br>false: 写入失败
     */
    public static boolean putBoolean(String key, boolean value) {
        return getSP().edit().putBoolean(key, value).commit();
    }

    /**
     * SP中读取boolean
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值false
     */
    public static boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    /**
     * SP中读取boolean
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值defaultValue
     */
    public static boolean getBoolean(String key, boolean defaultValue) {
        return getSP().getBoolean(key, defaultValue);
    }

    /**
     * 获取name为PREFERENCE_NAME的SP对象
     *
     * @return SP
     */
    private static SharedPreferences getSP() {
        if (sSp == null) {
            sSp = getContext().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        }
        return sSp;
    }

}
