package yummylau.common.util.util.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v4.net.ConnectivityManagerCompat;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * 网络处理工具类
 * <p>
 * Created by yummyLau on 17-4-30
 * Email: yummyl.lau@gmail.com
 */

public class NetUtils {

    private static final String TAG = NetUtils.class.getSimpleName();


    public static final int NETWORK_TYPE_NONE = -1;      //没有联网
    public static final int NETWORK_TYPE_WIFI = 0;       //WIFI网络
    public static final int NETWORK_TYPE_2G = 1;         //2G网络
    public static final int NETWORK_TYPE_3G = 2;         //3G网络
    public static final int NETWORK_TYPE_4G = 3;         //4G网络
    public static final int NETWORK_TYPE_UNKNOWN = 4;    //未知网络类型


    /**
     * 检查当前WIFI是否连接，两层意思——是否连接，连接是不是WIFI
     *
     * @param context
     * @return true表示当前网络处于连接状态，且是WIFI，否则返回false
     */
    public static boolean isWifiConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info != null && info.isConnected()
                && ConnectivityManager.TYPE_WIFI == info.getType()) {
            return true;
        }
        return false;
    }

    /**
     * 检查当前GPRS是否连接，两层意思——是否连接，连接是不是GPRS
     *
     * @param context
     * @return true表示当前网络处于连接状态，且是GPRS，否则返回false
     */
    public static boolean isMobileNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info != null && info.isConnected()
                && ConnectivityManager.TYPE_MOBILE == info.getType()) {
            return true;
        }
        return false;
    }

    /**
     * 检查当前是否连接
     *
     * @param context
     * @return true表示当前网络处于连接状态，否则返回false
     */
    public static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            return true;
        }
        return false;
    }

    /**
     * 对大数据传输时，需要调用该方法做出判断，如果流量敏感，应该提示用户
     *
     * @param context
     * @return true表示流量敏感，false表示不敏感
     */
    public static boolean isActiveNetworkMetered(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        return ConnectivityManagerCompat.isActiveNetworkMetered(cm);
    }


    /**
     * 是否是属于已联网
     *
     * @param netType
     * @return
     */
    public final static boolean isConnected(int netType) {
        return netType != NETWORK_TYPE_NONE;
    }

    /**
     * 是否是2G或者没有联网
     *
     * @param netType
     * @return
     */
    public final static boolean isNoneOr2g(int netType) {
        if (netType == NETWORK_TYPE_2G || netType == NETWORK_TYPE_NONE) {
            return true;
        }
        return false;
    }

    /**
     * 是否是3G还是未知类型
     *
     * @param netType
     * @return
     */
    public final static boolean is3gOrUnknown(int netType) {
        if (netType == NETWORK_TYPE_3G || netType == NETWORK_TYPE_UNKNOWN) {
            return true;
        }
        return false;
    }

    /**
     * 是否是4G或者wifi
     *
     * @param netType
     * @return
     */
    public final static boolean is4gOrWifi(int netType) {
        if (netType == NETWORK_TYPE_4G || netType == NETWORK_TYPE_WIFI) {
            return true;
        }
        return false;
    }


    /**
     * 获取网络类型
     *
     * @param context
     * @return
     */
    public static int getNetworkType(@NonNull Context context) {
        int type = NETWORK_TYPE_NONE;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                type = NETWORK_TYPE_WIFI;
            } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                String _strSubTypeName = networkInfo.getSubtypeName();
                int networkType = networkInfo.getSubtype();
                switch (networkType) {
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN:   //api<8 : replace by 11
                        type = NETWORK_TYPE_2G;
                        break;
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B: //api<9 : replace by 14
                    case TelephonyManager.NETWORK_TYPE_EHRPD:  //api<11 : replace by 12
                    case TelephonyManager.NETWORK_TYPE_HSPAP:  //api<13 : replace by 15
                        type = NETWORK_TYPE_3G;
                        break;
                    case TelephonyManager.NETWORK_TYPE_LTE:    //api<11 : replace by 13
                        type = NETWORK_TYPE_4G;
                        break;
                    default:
                        // http://baike.baidu.com/item/TD-SCDMA 中国移动 联通 电信 三种3G制式
                        if (_strSubTypeName.equalsIgnoreCase("TD-SCDMA") || _strSubTypeName.equalsIgnoreCase("WCDMA") || _strSubTypeName.equalsIgnoreCase("CDMA2000")) {
                            type = NETWORK_TYPE_3G;
                        }
                        break;
                }
                Log.d(TAG, "Network getSubtype : " + Integer.valueOf(networkType).toString());
            }
        }
        return type;
    }
}
