package yummylau.common.net.download;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.util.SparseIntArray;
import android.widget.RemoteViews;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import yummylau.common.R;
import yummylau.common.net.NetworkUtils;
import yummylau.common.util.FileUtils;

/**
 * 使用时在xml配置使用
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2017/12/11.
 */

public class DownloadApkService extends Service {

    public final static int NOTIFICATION_ID_OF_UPDATE_APP = 1;    // 更新App新版本时的notificationid

    public final static String INTENT_UPDATE_APP = "intent_update_app";

    private final static String TAG = "DownloadApkService";

    private static NotificationManager mNotificationManager;
    private static Notification mNotification;
    private static MyHandler mHandler;

    private static ExecutorService mExecutorService = Executors.newFixedThreadPool(5);

    private final static int MSG_DOWNLOAD_CANCEL = 0;             // 取消下载
    private final static int MSG_DOWNLOAD_SUCCEED = 1;            // 下载成功
    private final static int MSG_DOWNLOAD_UPDATE = 2;             // 下载进度更新
    private final static int MSG_DOWNLOAD_FAILED = 3;             // 下载失败
    private final static int MSG_DOWNLOAD_FILE_SUCCEED = 4;       // 下载文件成功，不安装

    private static SparseIntArray mDownload = new SparseIntArray();

    private static Context mContext;
    private static boolean mIsCancel = false;


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mHandler = new MyHandler(Looper.myLooper(), this);
        mContext = this;
        Log.d(TAG, "--------------onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "--------------onStartCommand");
        ApkInfo item = intent.getParcelableExtra(INTENT_UPDATE_APP);
        if (item != null) {
            DownloadApkService.downloadApk(
                    item.url,
                    item.notificationId,
                    item.name,
                    item.pkgName,
                    item.apkName);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "--------------onDestroy");
        super.onDestroy();
    }

    /**
     * 下载apk接口
     *
     * @param url
     * @param notificationId
     * @param name
     * @param pkgName
     */
    public static void downloadApk(final String url, final int notificationId, final String name, String pkgName, String appPkgName) {
        // 服务尚未开启
        if (mContext == null) {
            Log.d(TAG, "Download service is not start");
            return;
        }
        // 是否已经在下载
        if (mDownload.get(notificationId) != 0) {
            return;
        }
        // 设置任务栏提醒
        setNotification(notificationId, name, pkgName);
        // 记录进下载数组
        mDownload.put(notificationId, notificationId);
        // 将下载任务添加到任务栏中
        mNotificationManager.notify(notificationId, mNotification);
        // 启动线程开始执行下载任务
        download2(url, notificationId, name, appPkgName);
    }

    /**
     * 开启新线程下载
     *
     * @param url
     * @param notificationId
     * @param name
     * @param pkgName
     */
    private static void download2(final String url, final int notificationId, final String name, final String pkgName) {
        mExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                File tempFile = null;
                URL getUrl;
                HttpURLConnection httpURLConnection = null;
                InputStream is = null;
                BufferedInputStream bis = null;
                FileOutputStream fos = null;
                BufferedOutputStream bos = null;
                try {
                    getUrl = new URL(url);
                    NetworkUtils.trustAllHosts();
                    httpURLConnection = (HttpURLConnection) getUrl.openConnection();
                    httpURLConnection.setUseCaches(false);  //不使用缓存
                    is = new BufferedInputStream(httpURLConnection.getInputStream());
                    long length = httpURLConnection.getContentLength();
                    Log.d(TAG, "size:" + length + "B");
                    int responseCode = httpURLConnection.getResponseCode();

                    if (responseCode == 200 && is != null) {
                        File rootFile = new File(FileUtils.getDownloadDir());
                        if (!rootFile.exists() && !rootFile.isDirectory()) {
                            rootFile.mkdirs();
                        }
                        tempFile = new File(rootFile, pkgName + ".apk");

                        if (tempFile.exists()) {
                            tempFile.delete();
                        }
                        tempFile.createNewFile();

                        // 通过读入流创建缓冲输出流
                        bis = new BufferedInputStream(is);
                        // 通过文件写入流将读取到的数据写入到文件中
                        fos = new FileOutputStream(tempFile);
                        // 将文件写入流作为参数创建一个缓冲写入流
                        bos = new BufferedOutputStream(fos);

                        int read;
                        long count = 0;
                        int percent;
                        byte[] buffer = new byte[1024];
                        while ((read = bis.read(buffer)) != -1 && !mIsCancel) {
                            bos.write(buffer, 0, read);
                            count += read;
                            percent = (int) (((double) count / length) * 100);

                            // 每下载完成1%就通知任务栏进行修改下载进度
                            if (percent - mDownload.get(notificationId) >= 1) {
                                mDownload.put(notificationId, percent);
                                Message message = mHandler.obtainMessage(MSG_DOWNLOAD_UPDATE, percent);
                                Bundle bundle = new Bundle();
                                bundle.putString("name", name);
                                message.setData(bundle);
                                message.arg1 = notificationId;
                                mHandler.sendMessage(message);
                            }
                        }
                        bos.flush();
                        bos.close();
                        fos.flush();
                        fos.close();
                        is.close();
                        bis.close();
                    }

                    if (!mIsCancel) {
                        Message message = mHandler.obtainMessage(MSG_DOWNLOAD_SUCCEED, tempFile);
                        message.arg1 = notificationId;
                        Bundle bundle = new Bundle();
                        bundle.putString("name", name);
                        message.setData(bundle);
                        mHandler.sendMessage(message);
                    } else {
                        Message message = mHandler.obtainMessage();
                        message.what = MSG_DOWNLOAD_CANCEL;
                        message.arg1 = notificationId;
                        if (tempFile != null) {
                            tempFile.delete();
                        }
                        mHandler.sendMessage(message);
                    }
                } catch (MalformedURLException e) {
                    Log.d(TAG, e.toString());
                    if (tempFile != null && tempFile.exists()) {
                        tempFile.delete();
                    }
                    Message message = mHandler.obtainMessage(MSG_DOWNLOAD_FAILED, name + "下载失败:网络异常");
                    message.arg1 = notificationId;
                    mHandler.sendMessage(message);
                } catch (IOException e) {
                    Log.d(TAG, e.toString());
                    if (tempFile != null && tempFile.exists()) {
                        tempFile.delete();
                    }
                    Message message = mHandler.obtainMessage(MSG_DOWNLOAD_FAILED, name + "下载失败:文件传输异常");
                    message.arg1 = notificationId;
                    mHandler.sendMessage(message);
                } catch (Exception e) {
                    Log.d(TAG, e.toString());
                    if (tempFile != null && tempFile.exists()) {
                        tempFile.delete();
                    }
                    Message message = mHandler.obtainMessage(MSG_DOWNLOAD_FAILED, name + "下载失败," + e.getMessage());
                    message.arg1 = notificationId;
                    mHandler.sendMessage(message);
                } finally {
                    if (bos != null) {
                        try {
                            bos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fos != null) {
                        try {
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (is != null) {
                        try {
                            is.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                }
            }
        });
    }

    /**
     * 设置通知
     *
     * @param notificationId
     * @param name
     * @param pkgName
     */
    private static void setNotification(final int notificationId, final String name, String pkgName) {
        mNotification = new Notification();
        mNotification.icon = android.R.drawable.stat_sys_download;
        mNotification.tickerText = name + "开始下载";
        mNotification.when = System.currentTimeMillis();
        //自定义Notification布局
        RemoteViews contentView = new RemoteViews(pkgName, R.layout.library_net_notification_download_layout);
        contentView.setTextViewText(R.id.notificationTitle, name);
        contentView.setTextViewText(R.id.notificationPercent, "0%");
        contentView.setProgressBar(R.id.notificationProgress, 100, 0, false);
        mNotification.contentView = contentView;
    }

    class MyHandler extends Handler {
        private Context context;

        public MyHandler(Looper looper, Context c) {
            super(looper);
            this.context = c;
        }

        @Override
        public void handleMessage(Message msg) {
            PendingIntent contentIntent = null;
            RemoteViews contentView;
            super.handleMessage(msg);
            if (msg != null) {
                switch (msg.what) {
                    case MSG_DOWNLOAD_CANCEL:
                        mNotificationManager.cancel(msg.arg1);
                        mDownload.delete(msg.arg1);
                        if (isDone()) {
                            stopSelf();
                        }
                        break;

                    case MSG_DOWNLOAD_SUCCEED:
                        contentView = new RemoteViews(getPackageName(), R.layout.library_net_notification_download_layout);
                        contentView.setTextViewText(R.id.notificationTitle, getString(R.string.library_net_download_finish));
                        mNotification.contentView = contentView;
                        mNotificationManager.notify(msg.arg1, mNotification);
                        mDownload.delete(msg.arg1);
                        mNotificationManager.cancel(msg.arg1);
                        install((File) msg.obj, context);
                        mDownload.delete(msg.arg1);
                        if (isDone()) {
                            stopSelf();
                        }
                        break;

                    case MSG_DOWNLOAD_FILE_SUCCEED:
                        contentView = new RemoteViews(getPackageName(), R.layout.library_net_notification_download_layout);
                        contentView.setTextViewText(R.id.notificationTitle, getString(R.string.library_net_download_finish));
                        mNotification.contentView = contentView;
                        mNotificationManager.notify(msg.arg1, mNotification);
                        mDownload.delete(msg.arg1);
                        mNotificationManager.cancel(msg.arg1);
                        if (isDone()) {
                            stopSelf();
                        }
                        break;

                    case MSG_DOWNLOAD_UPDATE:
                        contentView = new RemoteViews(getPackageName(), R.layout.library_net_notification_download_layout);
                        contentView.setTextViewText(R.id.notificationTitle, msg.getData().getString("name") + getString(R.string.library_net_downloaing));
                        contentView.setTextViewText(R.id.notificationPercent, mDownload.get(msg.arg1) + "%");
                        contentView.setProgressBar(R.id.notificationProgress, 100, mDownload.get(msg.arg1), false);
                        mNotification.contentView = contentView;
                        mNotificationManager.notify(msg.arg1, mNotification);
                        break;

                    case MSG_DOWNLOAD_FAILED:
                        mDownload.delete(msg.arg1);
                        contentView = new RemoteViews(getPackageName(), R.layout.library_net_notification_download_layout);
                        contentView.setTextViewText(R.id.notificationTitle, msg.obj.toString());
                        mNotification.contentView = contentView;
                        mNotificationManager.notify(msg.arg1, mNotification);
                        if (isDone()) {
                            stopSelf();
                        }
                        break;
                }
            }
        }
    }


    /**
     * 查看是否有下载任务
     *
     * @return
     */
    public static boolean isDone() {
        return mDownload.size() == 0;
    }

    // 下载完成后安装
    private void install(File file, Context context) {
        if (file == null) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        context.startActivity(intent);
    }
}
