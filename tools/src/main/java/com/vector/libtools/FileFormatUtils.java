package com.vector.libtools;

import android.content.Context;
import android.text.format.Formatter;

import java.util.Locale;

/**
 * Created by Vector on 2016/8/10 0010.
 */
public class FileFormatUtils {

    private FileFormatUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    private static Context getContext() {
        //context 的入口
        return AppContext.getInstance().getContext();
    }

    /**
     * 格式化文件尺寸
     *
     * @param size
     * @return
     */
    public static String format(long size) {

        return Formatter.formatFileSize(getContext(), size);
    }


    public static String format_1(long size) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;

        if (size >= gb) {
            return String.format(Locale.CHINA, "%.1f GB", (float) size / gb);
        } else if (size >= mb) {
            float f = (float) size / mb;
            return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
        } else if (size >= kb) {
            float f = (float) size / kb;
            return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
        } else
            return String.format(Locale.CHINA, "%d B", size);
    }

}
