package com.vector.libtools;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import java.util.List;

/**
 * Created by Vector on 2016/8/16 0016.
 */
public class ShortCutUtil {
    private ShortCutUtil() {

    }

    public static void createShortCut(Context context, Class<?> clazz, String name, int appResId) {

        removeShortcut(context, clazz, name, appResId);


        Intent shortcutIntent = new Intent();
        shortcutIntent.setAction(Intent.ACTION_MAIN);
        ComponentName localComponentName = new ComponentName(context.getPackageName(), clazz.getName());
        shortcutIntent.setComponent(localComponentName);

        Intent sendShortcutIntent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
        sendShortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, name);
        sendShortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        sendShortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
                Intent.ShortcutIconResource.fromContext(context.getApplicationContext(), appResId));
        sendShortcutIntent.putExtra("duplicate", false);
        context.sendBroadcast(sendShortcutIntent);
    }

    public static void removeShortcut(Context context, Class<?> clazz, String name, int appResId) {
        Intent shortcutIntent = new Intent();
        shortcutIntent.setAction(Intent.ACTION_MAIN);
        ComponentName localComponentName = new ComponentName(context.getPackageName(), clazz.getName());
        shortcutIntent.setComponent(localComponentName);
        Intent sendShortcutIntent = new Intent("com.android.launcher.action.UNINSTALL_SHORTCUT");
        sendShortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, name);
        sendShortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        context.sendBroadcast(sendShortcutIntent);
    }

    /**
     * 为PackageName的App添加快捷方式
     *
     * @param context context
     * @param pkg     待添加快捷方式的应用包名
     * @return 返回true为正常执行完毕
     */
    public static boolean addShortcutByPackageName(Context context, String pkg) {
        // 快捷方式名
        String title = "unknown";
        // MainActivity完整名
        String mainAct = null;
        // 应用图标标识
        int iconIdentifier = 0;
        // 根据包名寻找MainActivity
        PackageManager pkgMag = context.getPackageManager();
        Intent queryIntent = new Intent(Intent.ACTION_MAIN, null);
        queryIntent.addCategory(Intent.CATEGORY_LAUNCHER);// 重要，添加后可以进入直接已经打开的页面
        queryIntent.setFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        queryIntent.addFlags(Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY);

        List<ResolveInfo> list = pkgMag.queryIntentActivities(queryIntent,
                PackageManager.GET_ACTIVITIES);
        for (int i = 0; i < list.size(); i++) {
            ResolveInfo info = list.get(i);
            if (info.activityInfo.packageName.equals(pkg)) {
                title = info.loadLabel(pkgMag).toString();
                mainAct = info.activityInfo.name;
                iconIdentifier = info.activityInfo.applicationInfo.icon;
                break;
            }
        }
        if (mainAct == null) {
            // 没有启动类
            return false;
        }
        Intent shortcut = new Intent(
                "com.android.launcher.action.INSTALL_SHORTCUT");
        // 快捷方式的名称
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, title);
        // 不允许重复创建
        shortcut.putExtra("duplicate", false);
        ComponentName comp = new ComponentName(pkg, mainAct);
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT,
                queryIntent.setComponent(comp));
        // 快捷方式的图标
        Context pkgContext = null;
        if (context.getPackageName().equals(pkg)) {
            pkgContext = context;
        } else {
            // 创建第三方应用的上下文环境，为的是能够根据该应用的图标标识符寻找到图标文件。
            try {
                pkgContext = context.createPackageContext(pkg,
                        Context.CONTEXT_IGNORE_SECURITY
                                | Context.CONTEXT_INCLUDE_CODE);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (pkgContext != null) {
            Intent.ShortcutIconResource iconRes = Intent.ShortcutIconResource
                    .fromContext(pkgContext, iconIdentifier);
            shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconRes);
        }
        // 发送广播，让接收者创建快捷方式
        // 需权限<uses-permission
        // android:name="com.android.launcher.permission.INSTALL_SHORTCUT" />
        context.sendBroadcast(shortcut);
        return true;
    }


}
