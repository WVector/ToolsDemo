package com.vector.libtools;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.Toast;

import java.util.List;

/**
 * 2016-4-27上午11:27:09
 *
 * @author Vector
 */
public class EmailUtil {
    public static void send(Context context, String sendTo) {
        send(context, sendTo, null, null, null);
    }

    public static void send(Context context, String sendTo, String sendCc, String subject, String content) {
        Uri receiver = Uri.parse("mailto:"+sendTo);

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, receiver);

        if (!TextUtils.isEmpty(sendCc)) {
            String[] email = {sendCc};
            emailIntent.putExtra(Intent.EXTRA_CC, email); // 抄送人
        }
        if (!TextUtils.isEmpty(subject)) {
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject); // 主题
        }
        if (!TextUtils.isEmpty(content)) {

            emailIntent.putExtra(Intent.EXTRA_TEXT, content); // 正文
        }

        List<ResolveInfo> resolveInfos = context.getPackageManager().queryIntentActivities(emailIntent, 0);
        if (resolveInfos.size() > 0) {
            context.startActivity(emailIntent);
        } else {
            Toast.makeText(context, "发送失败", Toast.LENGTH_SHORT).show();
        }
    }

}
