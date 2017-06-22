package com.vector.libtools;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;

import com.vector.libtools.filemanager.AppDirContext;
import com.vector.libtools.filemanager.DirectoryManager;

/**
 * Created by Vector on 2016/8/11 0011.
 */
public class AppContext {
    private static String APP_NAME = "tools";
    private static AppContext sAppContext = null;
    private Context sContext;
    private Thread sMainThread;
    private int sMainThreadId;
    private Handler sHandler;
    private static DirectoryManager sDirManager = null;

    private AppContext(Context context, Thread thread, int threadId, Handler handler, String folderName) {
        sContext = context;
        sMainThread = thread;
        sMainThreadId = threadId;
        sHandler = handler;
        APP_NAME = folderName;
        AppUtils.currentAppState = AppUtils.AppState.UNKNOWN;
    }

    public static synchronized void init(Application app, String folderName) {

        if (sAppContext == null) {
            synchronized (AppContext.class) {
                if (sAppContext == null) {

                    sAppContext = new AppContext(app.getApplicationContext(),
                            Thread.currentThread(),
                            Process.myTid(),
                            new Handler(Looper.getMainLooper()),

                            folderName
                    );
                }
            }
        }
    }

    public static AppContext getInstance() {
        if (sAppContext == null) {
            throw (new NullPointerException("AppContext必须在application中进行init初始化"));
        }
        return sAppContext;
    }


    /**
     * 初始化日志
     */

    public static DirectoryManager getDirManager() {
        //如果用户手动删除，而app未重新启动则会此时并不能初始化，可以检测file的状态
        if (sDirManager == null)
            initFileSystem();
        return sDirManager;
    }

    private synchronized static boolean initFileSystem() {
        if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            return false;
        }
        if (sDirManager == null)
            sDirManager = new DirectoryManager(new AppDirContext(APP_NAME));
        return sDirManager.createAll();
    }


    public Context getContext() {
        return sContext;
    }

    public Thread getMainThread() {
        return sMainThread;
    }

    public int getMainThreadId() {
        return sMainThreadId;
    }

    public Handler getHandler() {
        return sHandler;
    }

}
