package yummylau.common.util.util;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;


import java.util.List;

import yummylau.common.R;
import yummylau.common.util.util.common.StringUtils;

/**
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2017/12/11.
 */

public class AppUtils {

    private final static String TAG = AppUtils.class.getSimpleName();

    /**
     * 双击退出App
     *
     * @param context 上下文
     * @param tips    退出提示
     */
    public static long sLastExitPressed = 0;


    public static void exitApp(Context context) {
        if ((System.currentTimeMillis() - sLastExitPressed) > 2000) {
            Toast.makeText(context, context.getString(R.string.ask_for_exit_app), Toast.LENGTH_SHORT).show();
            sLastExitPressed = System.currentTimeMillis();
        } else {
            Log.d(TAG, "exitApp");
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        }
    }

    public static String getUserAgent(Context context) {
        String deviceType = DeviceUtils.getDeviceType();
        try {
            StringUtils.checkNonAscii(deviceType);
        } catch (Exception e) {
            Log.e(TAG,e.toString());
            deviceType = "";
        }
//        String userAgent = Constants.USERAGENT +
//                "/" + getProductVersion(context) +
//                "/(" + deviceType + "; " + SystemUtil.getOsName() + " " + SystemUtil.getOsVersion() + "; " + DeviceUtil.getResolution(context) + ")" +
//                "/" + DeviceUtil.getAndroidId(context) +
//                "/" + ChannelUtil.getChannel(context);
        String userAgent = "";
        return userAgent;
    }

    /**
     * 获取应用的版本号
     *
     * @param context context
     * @return 版本号
     */
    public static String getAppVersionName(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * App是否在前台
     *
     * @param context 上下文
     * @return 是否在前台
     */
    public static boolean isAppOnForeground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null) {
            return false;
        }
        final String packageName = context.getPackageName();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND && appProcess.processName.equals(packageName)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 保存到剪切板
     *
     * @param context 上下文
     * @param content 内容
     */
    @SuppressLint("NewApi")
    public static void copyClipboard(Context context, String content) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setPrimaryClip(ClipData.newPlainText(null, content));
        } else {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(content);
        }
    }

    /**
     * 获取包名
     *
     * @param context
     * @param template
     * @return
     */
    public static String getStringOfPackageName(Context context, String template) {
        return String.format(template, context.getPackageName());
    }

    /**
     * 获取IMEI
     *
     * @param context
     * @return
     */
    public static String getIMEI(Context context) {
        try {
            return ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 获取手机系统名
     *
     * @return
     */
    public static String getOsName() {
        return "Android";
    }


    /**
     * 获取系统版本
     *
     * @return
     */
    public static String getOsVersion() {
        return Build.VERSION.RELEASE;
    }


    /**
     * 获取分辨率
     *
     * @param context
     * @return
     */
    public static Display getResolution(Context context) {
        return ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
    }


    /**
     * 获取设备型号
     *
     * @return
     */
    public static String getDeviceType() {
        return Build.MODEL;
    }

    /**
     * 获取产品名
     *
     * @param context
     * @return
     */
    public static String getProductName(Context context) {
        return context.getPackageManager().getApplicationLabel(context.getApplicationInfo()).toString();
    }


    /**
     * 获取产品版本
     *
     * @param context
     * @return
     */
    public static String getProductVersion(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取versionCode
     *
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取当前系统每个app的内存等级，即最大使用内存
     *
     * @param context
     * @return
     */
    public static int getMemoryClass(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        return activityManager.getMemoryClass();
    }


    /**
     * 根据IMSI码判断当前用户是否是中国用户
     *
     * @param ctx
     * @return
     */
    public static String getTelephonyServiceProvider(Context ctx) {
        String imsi = ((TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE)).getSubscriberId();
        if (imsi != null && imsi.length() > 0) {
            // 因为移动网络编号46000下的IMSI已经用完,
            // 所以虚拟了一个46002编号，134/159号段使用了此编号
            return getTelephonyServiceProviderThroughImsi(imsi);
        }
        return "" + imsi;
    }

    public static String getTelephonyServiceProviderThroughImsi(String imsi) {

        // 中国大陆部分
        if (imsi.startsWith("46000") || imsi.startsWith("46002")) {
            // 中国移动
            return "中国移动";
        }
        if (imsi.startsWith("46001")) {
            // 中国联通
            return "中国联通";
        }
        if (imsi.startsWith("46003")) {
            // 中国电信
            return "中国联通";
        }
        // 台湾地区部分
        if (imsi.startsWith("46601")) {
            // 46601, "Far EasTone", "TW" 远传电讯
            return "远传电讯";
        }
        if (imsi.startsWith("46605")) {
            // 46605, 亚太电信
            return "亚太电信";
        }
        if (imsi.startsWith("46606")) {
            // 46606, "TUNTEX", "TW"东荣电讯
            return "东荣电讯";
        }
        if (imsi.startsWith("46668")) {
            // 46668, "ACeS", "TW"
            return "ACeS";
        }
        if (imsi.startsWith("46688")) {
            // 46688, "KGT", "TW" 和信电讯
            return "和信电讯";
        }
        if (imsi.startsWith("46689")) {
            // 46689 威宝电信
            return "威宝电信";
        }
        if (imsi.startsWith("46692")) {
            // 46692, "Chunghwa", "TW" 中华电信
            return "中华电信";
        }
        if (imsi.startsWith("46693")) {
            // 46693, "MobiTai", "TW" 东信电讯
            return "东信电讯";
        }
        if (imsi.startsWith("46697")) {
            // 46697, "TWN GSM", "TW" 台湾大哥大
            return "台湾大哥大";
        }
        if (imsi.startsWith("46699")) {
            // 46699, "TransAsia", "TW" 泛亚电讯
            return "泛亚电讯";
        }

        // 中国香港部分
        if (imsi.startsWith("45400")) {
            return "香港流动通讯CSL";
        }
        if (imsi.startsWith("45406")) {
            return "香港数码通";
        }
        if (imsi.startsWith("45404")) {
            return "香港和记";
        }
        if (imsi.startsWith("45416")) {
            return "香港汇亚";
        }
        if (imsi.startsWith("45412")) {
            return "中国移动万众";
        }
        if (imsi.startsWith("45410")) {
            return "香港新世界";
        }

        // 中国澳门部分
        if (imsi.startsWith("45501")) {
            return "澳门电讯CTM";
        }
        if (imsi.startsWith("45503")) {
            return "澳门和记电讯";
        }
        if (imsi.startsWith("45500")) {
            return "澳门数码通";
        }
        if (imsi.startsWith("45502")) {
            return "中国电信（澳门）";
        }

        return imsi;
    }

    /**
     * app是否存在
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean checkApkExist(Context context, String packageName) {
        if (packageName == null || "".equals(packageName))
            return false;
        try {
            ApplicationInfo info = context.getPackageManager().getApplicationInfo(packageName,
                    PackageManager.MATCH_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }


    /**
     * 获取当前进程名
     *
     * @param context
     * @return
     */
    public static String getCurrentProcessName(Context context) {
        String currentProcessName = "";
        int pid = android.os.Process.myPid();
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcessInfos = manager.getRunningAppProcesses();
        if (runningAppProcessInfos != null && !runningAppProcessInfos.isEmpty()) {
            for (ActivityManager.RunningAppProcessInfo processInfo : runningAppProcessInfos) {
                if (processInfo.pid == pid) {
                    currentProcessName = processInfo.processName;
                    break;
                }
            }
        }
        return currentProcessName;
    }

    /**
     * 获取设备唯一标识码
     * @param context 上下文
     * @return 设备唯一标识码
     */
    public static String getUDID(Context context) {
//        String udid = AppConfig.getUDID(context, null);
//        if (udid == null || "".equals(udid.trim()) || Pattern.matches("^[0]*?$", udid)) {
//            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//            udid = telephonyManager.getDeviceId();
//            if (udid == null || "".equals(udid.trim()) || Pattern.matches("^[0]*?$", udid))        //非手机设备
//                udid = Build.SERIAL;
//            if (udid == null || "".equals(udid.trim()) || Pattern.matches("^[0]*?$", udid)) {      //两种都获取不到，随机生成
//                Random random = new Random();
//                int rand = random.nextInt(100000);
//                long curTimeMills = System.currentTimeMillis();
//                udid = curTimeMills + "," + rand;
//            }
//            AppConfig.setUDID(context, udid);
//        }
//        return udid;
        return getAndroidID(context);
    }

    /**
     * 获取Android ID
     *
     * @return 字符串
     */
    public static String getAndroidID(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }


    /**
     * 获得操作系统版本
     *
     * @return 系统版本
     */
    public static int getSystemVersionCode() {
        return Build.VERSION.SDK_INT;
    }
}
