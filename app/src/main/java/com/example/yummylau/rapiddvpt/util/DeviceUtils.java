package com.example.yummylau.rapiddvpt.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.view.WindowManager;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Locale;

/**
 * 设备相关
 * Created by yummyLau on 17-4-30
 * Email: yummyl.lau@gmail.com
 */

public class DeviceUtils {

    public static final SparseArray<String> SDK_VERSIONS = new SparseArray<String>();

    static {
        SDK_VERSIONS.put(Build.VERSION_CODES.CUPCAKE, "1.5");
        SDK_VERSIONS.put(Build.VERSION_CODES.DONUT, "1.6");
        SDK_VERSIONS.put(Build.VERSION_CODES.ECLAIR, "2.0");
        SDK_VERSIONS.put(Build.VERSION_CODES.ECLAIR_0_1, "2.0.1");
        SDK_VERSIONS.put(Build.VERSION_CODES.ECLAIR_MR1, "2.1");
        SDK_VERSIONS.put(Build.VERSION_CODES.FROYO, "2.2");
        SDK_VERSIONS.put(Build.VERSION_CODES.GINGERBREAD, "2.3");
        SDK_VERSIONS.put(Build.VERSION_CODES.GINGERBREAD_MR1, "2.3.3");
        SDK_VERSIONS.put(Build.VERSION_CODES.HONEYCOMB, "3.0");
        SDK_VERSIONS.put(Build.VERSION_CODES.HONEYCOMB_MR1, "3.1");
        SDK_VERSIONS.put(Build.VERSION_CODES.HONEYCOMB_MR2, "3.2");
        SDK_VERSIONS.put(14, "4.0");
        SDK_VERSIONS.put(15, "4.0.3");
        SDK_VERSIONS.put(16, "4.1");
        SDK_VERSIONS.put(17, "4.2");
        SDK_VERSIONS.put(18, "4.3");
        SDK_VERSIONS.put(19, "4.4");
        SDK_VERSIONS.put(20, "4.4W");
        SDK_VERSIONS.put(21, "5.0");
        SDK_VERSIONS.put(22, "5.1");
        SDK_VERSIONS.put(23, "6.0");
    }

    /**
     * 返回设备类型，目前只根据设备是否有电话功能区分是phone还是pad
     *
     * @return PC，Mobile，PAD，Other
     */
    private static String getType(Context context) {
        try {
            TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (manager.getPhoneType() == TelephonyManager.PHONE_TYPE_NONE) {
                return "PAD";
            } else {
                return "Mobile";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 返回手机基站号
     *
     * @return lac:mcc:mnc:cid一串值
     */
    private static String getCla(Context context) {
        int lac = 0;
        int mcc = 0;
        int mnc = 0;
        int cid = 0;
        try {
            TelephonyManager mTelephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (mTelephonyManager.getPhoneType() == TelephonyManager.PHONE_TYPE_GSM) {
                // TODO 红米手机getNetworkOperator()返回字符串"null"
                String mccMnc = mTelephonyManager.getNetworkOperator();
                if (mccMnc.length() > 0) {
                    mcc = Integer.parseInt(mccMnc.substring(0, 3));
                    mnc = Integer.parseInt(mccMnc.substring(3));
                }
                GsmCellLocation location = (GsmCellLocation) mTelephonyManager.getCellLocation();
                lac = location.getLac();
                cid = location.getCid();
            } else if (mTelephonyManager.getPhoneType() == TelephonyManager.PHONE_TYPE_CDMA) {
                CdmaCellLocation cdma = (CdmaCellLocation) mTelephonyManager.getCellLocation();
                lac = cdma.getNetworkId();
                mcc = Integer.parseInt(mTelephonyManager.getNetworkOperator().substring(0, 3));
                mnc = cdma.getSystemId();
                cid = cdma.getBaseStationId();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder();
        return sb.append(lac).append(":").append(mcc).append(":").append(mnc).append(":").append(cid).toString();
    }

    /**
     * 返回设备的DeviceId号
     *
     * @return 获取成功返回手机IMEI，否则返回null
     */
    public static String getDeviceId(Context context) {
        String deviceId = null;
        try {
            TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            deviceId = manager.getDeviceId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //TODO  如果为空应该获取FakeImei
/*        if (TextUtils.isEmpty(deviceId)) {
            deviceId = getFakeImei(context,path);
        }*/
        return deviceId;
    }

    /**
     * 返回设备的AndroidId号
     *
     * @return 获取成功返回手机AndroidId，否则返回null
     */
    public static String getAndroidId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    /**
     * 获取设备的Mac地址
     *
     * @return 获取成功返回Mac地址，否则返回null
     */
    public static String getMacAddress(Context context) {
        String macAddr = null;
        try {
            WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = wifi.getConnectionInfo();
            macAddr = info.getMacAddress();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (TextUtils.isEmpty(macAddr)) {
            macAddr = "00:0A:00:00:00:00";
        }
        return macAddr;
    }

/*    *//**
     * @param context
     * @return
     *//*
    private static String getFakeImei(Context context, String path) {
        File fakeMacFile = new File(Environment.getExternalStorageDirectory().getPath() + path, "fake_imei");
        String fakeMac = FileUtil.getFileContent(fakeMacFile);
        if (TextUtils.isEmpty(fakeMac)) {
            fakeMac = SharedPreferenceUtil.getInstance().getStringRaw("fake_imei", null);
        }
        if (TextUtils.isEmpty(fakeMac)) {
            fakeMac = String.format("%015d", System.currentTimeMillis());
            if (fakeMac.length() > 15) {
                fakeMac = fakeMac.substring(0, 15);
            }
            FileUtil.copyToFile(fakeMac, fakeMacFile);
            SharedPreferenceUtil.getInstance().putStringRaw("fake_imei", fakeMac);
        }
        return fakeMac;
    }*/

    /**
     * 得到当前的手机网络类型
     *
     * @param context
     * @return
     */
    public static String getCurrentNetType(Context context) {
        String type = "";
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info == null) {
            type = "null";
        } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {
            type = "wifi";
        } else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
            int subType = info.getSubtype();
            if (subType == TelephonyManager.NETWORK_TYPE_CDMA || subType == TelephonyManager.NETWORK_TYPE_GPRS
                    || subType == TelephonyManager.NETWORK_TYPE_EDGE) {
                type = "2g";
            } else if (subType == TelephonyManager.NETWORK_TYPE_UMTS || subType == TelephonyManager.NETWORK_TYPE_HSDPA
                    || subType == TelephonyManager.NETWORK_TYPE_EVDO_A
                    || subType == TelephonyManager.NETWORK_TYPE_EVDO_0
                    || subType == TelephonyManager.NETWORK_TYPE_EVDO_B) {
                type = "3g";
            } else if (subType == TelephonyManager.NETWORK_TYPE_LTE) {// LTE是3g到4g的过渡，是3.9G的全球标准
                type = "4g";
            }
        }
        return type;
    }

    /**
     * 获取ip地址
     * TODO 需要补充gprs gps
     *
     * @param context
     * @return
     */
    public static String getIpAddress(Context context) {
        try {
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            int ipAddress = wifiInfo.getIpAddress();
            return InetAddress
                    .getByName(
                            String.format(Locale.getDefault(), "%d.%d.%d.%d", (ipAddress & 0xff),
                                    (ipAddress >> 8 & 0xff), (ipAddress >> 16 & 0xff), (ipAddress >> 24 & 0xff)))
                    .getHostAddress().toString();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return "127.0.0.1";
        }
    }

    /**
     * 获得设备型号
     *
     * @return 设备型号
     */
    public static String getDeviceType() {
        return Build.MODEL.replace(" ", "");
    }

    /**
     * 获得设备平台
     *
     * @return 设备平台
     */
    public static String getSystemName() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获得操作系统版本
     *
     * @return 系统版本
     */
    public static String getSystemVersion() {
        return String.valueOf(Build.VERSION.SDK_INT);
    }

    /**
     * 获得操作系统版本
     *
     * @return 系统版本
     */
    public static int getSystemVersionCode() {
        return Build.VERSION.SDK_INT;
    }

    /***
     * 获取版本号对应版本名
     *
     * @param versionCode
     * @return
     */
    public static String getSystemVersionName(int versionCode) {
        return SDK_VERSIONS.get(versionCode);
    }

    /**
     * 获得应用程序版本
     *
     * @param context context
     * @return 应用版本
     */
    public static String getApplicationVersion(Context context) {
        String version = "?";
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            version = pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }

    /**
     * 获取设备的分辨率
     *
     * @param context context
     * @return String 字符串格式为 width*height
     */
    public static String getResolution(Context context) {
        if (context == null) {
            return "";
        }
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager WM = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        WM.getDefaultDisplay().getMetrics(dm);

        StringBuilder result = new StringBuilder();
        result.append(dm.widthPixels);
        result.append("*");
        result.append(dm.heightPixels);
        return result.toString();
    }

    /**
     * 是否已root
     *
     * @param context context
     * @return
     */
    public static boolean isRooted(Context context) {
        File f = null;
        final String kSuSearchPaths[] = {"/system/bin/", "/system/xbin/", "/system/sbin/", "/sbin/", "/vendor/bin/"};
        try {
            for (int i = 0; i < kSuSearchPaths.length; i++) {
                f = new File(kSuSearchPaths[i] + "su");
                if (f != null && f.exists()) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    /**
     * 判断当前设备是否是模拟器。如果返回TRUE，则当前是模拟器，不是返回FALSE
     *
     * @param context context
     * @return
     */
    public static boolean isEmulator(Context context) {
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String imei = tm.getDeviceId();
            if (imei != null && imei.equals("000000000000000")) {
                return true;
            }
            return (Build.MODEL.equals("sdk")) || (Build.MODEL.equals("google_sdk"));
        } catch (Exception ioe) {

        }
        return false;
    }

    /**
     * 获取SIM卡运营商
     *
     * @param context
     * @return
     */
    public static String getOperators(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String operator = null;
        String IMSI = "";
        try {
            IMSI = tm.getSubscriberId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (IMSI == null || IMSI.equals("")) {
            return operator;
        }
        if (IMSI.startsWith("46000") || IMSI.startsWith("46002")) {
            operator = "中国移动";
        } else if (IMSI.startsWith("46001")) {
            operator = "中国联通";
        } else if (IMSI.startsWith("46003")) {
            operator = "中国电信";
        }
        return operator;
    }
}
