package com.example.yummylau.rapiddvpt.util.common;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * UncaughtException处理类,当程序发生Uncaught异常的时候,有该类来接管程序,并记录发送错误报告.
 * Created by yummyLau on 17-4-30
 * Email: yummyl.lau@gmail.com
 */

public class CrashUtils implements Thread.UncaughtExceptionHandler {

    public static final String TAG = CrashUtils.class.getSimpleName();
    private Context mContext;
    private static CrashUtils instance;                                             //CrashHandler实例
    private Thread.UncaughtExceptionHandler mDefaultHandler;                        //系统默认的UncaughtException处理类
    private Map<String, String> mInfoMap = new HashMap<>();                         //用来存储设备信息和异常信息
    private DateFormat mDateFormat =
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());       //用于格式化日期,作为日志文件名的一部分

    private CrashUtils() {}

    /**
     * 获取CrashHandler实例 ,单例模式
     */
    public static CrashUtils getInstance() {
        if (instance == null) {
            instance = new CrashUtils();
        }
        return instance;
    }

    /**
     *  获取系统默认的UncaughtException处理器
     *  设置该CrashHandler为程序的默认处理器
     *
     * @param context 上下文
     */
    public void init(Context context) {
        mContext = context;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     *
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Log.e(TAG, "error : ");
            }
            // 退出程序
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false.
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        //使用Toast来显示异常信息
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                try {
                    Toast.makeText(mContext, "系统君不在状态？重启精彩继续", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Looper.loop();
            }
        }.start();
        //收集设备参数信息
        collectDeviceInfo(mContext);
        //保存日志文件
        saveCrashInfo2File(ex);
        return true;
    }

    /**
     * 收集设备参数信息
     *
     * @param ctx
     */
    public void collectDeviceInfo(Context ctx) {
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";
                mInfoMap.put("VersionName", versionName);
                mInfoMap.put("VersionCode", versionCode);
            }
            long timestamp = System.currentTimeMillis();
            String time = mDateFormat.format(new Date());
            mInfoMap.put("CrashTime", time + "-" + timestamp);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "an error occurred when collect package info");
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                mInfoMap.put(field.getName(), field.get(null).toString());
                Log.d(TAG, field.getName() + " : " + field.get(null));
            } catch (Exception e) {
                Log.e(TAG, "an error occurred when collect crash info");
            }
        }
    }

    /**
     * 保存错误信息到文件中
     *
     * @param ex
     * @return 返回文件名称, 便于将文件传送到服务器
     */
    private String saveCrashInfo2File(Throwable ex) {

        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : mInfoMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key).append("=").append(value).append("\n");
        }

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        //在控制台中打印出log
        Log.e(TAG, result);

        sb.append(result);
        try {
            long timestamp = System.currentTimeMillis();
            String time = mDateFormat.format(new Date());
            String fileName = "crash-" + time + "-" + timestamp + ".txt";

            //log的保存路径
			String crashCachePath = mContext.getCacheDir().toString() + "/crash/";
//            String crashCachePath = FileUtils.getCrashLogDir(mContext);
            File crashCacheDir = new File(crashCachePath);
            // 如果文件夹不存在则创建
            if (!crashCacheDir.exists()) {
                crashCacheDir.mkdirs();
            }

            FileOutputStream fos = new FileOutputStream(crashCachePath + "/" + fileName);
            fos.write(sb.toString().getBytes());
            fos.close();

            return fileName;
        } catch (Exception e) {
            Log.e(TAG, "an error occurred while writing file...");
        }
        return null;
    }
}
