package com.vector.libtools.log;


import com.vector.libtools.log.logger.LogLevel;
import com.vector.libtools.log.logger.Printer;
import com.vector.libtools.log.logger.Settings;

/**
 * Created by Vector
 * on 2016/9/1 0001.
 */
public class L {

    private static Settings settings;

    private L() {

    }

    public static Settings init() {
        settings = LoggerWrapper.init();
        return settings;
    }

    public static Settings init(String tag) {
        settings = LoggerWrapper.init(tag);
        return settings;
    }

    public static Printer t(String tag) {
        return LoggerWrapper.t(tag);
    }

    public static Printer t(int methodCount) {
        return LoggerWrapper.t(methodCount);
    }

    public static Printer t(String tag, int methodCount) {
        return LoggerWrapper.t(tag, methodCount);
    }

    private static boolean condition() {
        return settings == null || settings.getLogLevel() == LogLevel.NONE;
    }


    public static void d(String message, Object... args) {
        if (condition()) {
            return;
        }
        LoggerWrapper.d(message, args);
    }

    public static void e(String message, Object... args) {
        if (condition()) {
            return;
        }
        LoggerWrapper.e(message, args);
    }

    public static void e(Throwable throwable, String message, Object... args) {
        if (condition()) {
            return;
        }
        LoggerWrapper.e(throwable, message, args);
    }


    public static void i(String message, Object... args) {
        if (condition()) {
            return;
        }
        LoggerWrapper.i(message, args);
    }

    public static void v(String message, Object... args) {
        if (condition()) {
            return;
        }
        LoggerWrapper.v(message, args);
    }

    public static void w(String message, Object... args) {
        if (condition()) {
            return;
        }
        LoggerWrapper.w(message, args);
    }

    public static void wtf(String message, Object... args) {
        if (condition()) {
            return;
        }
        LoggerWrapper.wtf(message, args);
    }

    public static void json(String json) {
        if (condition()) {
            return;
        }
        LoggerWrapper.json(json);
    }

    public static void xml(String xml) {
        if (condition()) {
            return;
        }
        LoggerWrapper.xml(xml);
    }

    public static void object(Object object) {
        if (condition()) {
            return;
        }
        LoggerWrapper.object(object);
    }
}
