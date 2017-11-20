package yummylau.common.net.download;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * 下载apk助手
 * Created by g8931 on 2017/11/20.
 */

public class DownloadApkHelper {

    private static final String TAG = DownloadApkHelper.class.getSimpleName();

    public static void downloadApk(Context context, ApkInfo apk) {
        if (apk == null) {
            Log.e(TAG, "apk info can't be null！");
            return;
        }
        Intent serviceIntent = new Intent(context, DownloadApkService.class);
        serviceIntent.putExtra(DownloadApkService.INTENT_UPDATE_APP, apk);
        context.startService(serviceIntent);
    }
}
