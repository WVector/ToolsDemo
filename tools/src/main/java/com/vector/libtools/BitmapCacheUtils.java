package com.vector.libtools;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.widget.ImageView;

import com.vector.libtools.ui.UIUtils;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by fengjunming_t on 2016/6/17 0017.
 */
public class BitmapCacheUtils {
    public static void saveImageToDCIM(final ImageView imageView, final Context context, final String fileName, final CallBack callBack) {
        ThreadManager.getThreadProxyPool().execute(new Runnable() {
            @Override
            public void run() {
                BitmapDrawable bmpDrawable = (BitmapDrawable) imageView.getDrawable();
                Bitmap bmp = bmpDrawable.getBitmap();
                if (DeviceUtils.isSDCardEnable()) {
                    String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "DCIM" + File.separator + "Camera";
                    File AlbumPath = new File(path);
                    if (!AlbumPath.exists()) {
                        AlbumPath.mkdirs();
                    }

                    final File file = new File(path, fileName + ".jpg");
                    try {
                        FileOutputStream fos = new FileOutputStream(file);
                        bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                        fos.flush();
                        fos.close();
                        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.getAbsolutePath())));
                        UIUtils.runInMainThread(new Runnable() {
                            @Override
                            public void run() {
                                if (callBack != null) {
                                    callBack.onFinish(file.getAbsolutePath());
                                }
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                        UIUtils.runInMainThread(new Runnable() {
                            @Override
                            public void run() {
                                if (callBack != null) {
                                    callBack.onError();
                                }
                            }
                        });
                    }


                }
            }
        });

    }

    public interface CallBack {
        void onFinish(String absolutePath);

        void onError();
    }
}
