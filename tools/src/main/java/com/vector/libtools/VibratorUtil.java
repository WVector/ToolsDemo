package com.vector.libtools;

import android.content.Context;
import android.os.Vibrator;

/**
 * Created by Vector on 2016/7/28 0028.
 */
public class VibratorUtil {

    /**
     * "android.permission.VIBRATE"
     */
    private VibratorUtil() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    private static Context getContext() {
        //context 的入口
        return AppContext.getInstance().getContext();
    }

    private static volatile Vibrator sVibrator;

    public static void vibrateDot() {
        getVibrator().vibrate(25);
    }

    public static void vibrate(long milliseconds) {
        getVibrator().vibrate(milliseconds);
    }

    public static void vibrate(long[] pattern, int repeat) {
        getVibrator().vibrate(pattern, repeat);
    }

    public static void vibratePattern() {
        long[] pattern = {50, 100, 50, 100};
        getVibrator().vibrate(pattern, -1);
    }

    private static Vibrator getVibrator() {

        Vibrator tmp = sVibrator;


        if (tmp == null) {

            synchronized (VibratorUtil.class) {

                tmp = sVibrator;

                if (tmp == null) {

                    tmp = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);

                    sVibrator = tmp;
                }

            }
        }
        return tmp;
    }
}
