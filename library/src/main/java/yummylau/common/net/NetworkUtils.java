package yummylau.common.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.util.Log;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * 网络模块
 * Created by g8931 on 2017/11/17.
 */

public class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    public static final int NETWORKTYPE_INVALID = 0;            // 无网络
    public static final int NETWORKTYPE_2G = 1;            // 2G
    public static final int NETWORKTYPE_3G = 2;            // 3G
    public static final int NETWORKTYPE_4G = 3;             // 4G
    public static final int NETWORKTYPE_WIFI = 4;           // WIFI
    public static final int NETWORKTYPE_MOBILE = 5;        // MOBILE


    @SuppressWarnings("all")
    public static int getNetworkType(Context context) {
        int type = NETWORKTYPE_INVALID;
        if (context != null) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    type = NETWORKTYPE_WIFI;
                } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                    String _strSubTypeName = networkInfo.getSubtypeName();

                    // TD-SCDMA   networkType is 17
                    int networkType = networkInfo.getSubtype();
                    switch (networkType) {
                        case TelephonyManager.NETWORK_TYPE_GPRS:
                        case TelephonyManager.NETWORK_TYPE_EDGE:
                        case TelephonyManager.NETWORK_TYPE_CDMA:
                        case TelephonyManager.NETWORK_TYPE_1xRTT:
                        case TelephonyManager.NETWORK_TYPE_IDEN:   //api<8 : replace by 11
                            type = NETWORKTYPE_2G;
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
                            type = NETWORKTYPE_3G;
                            break;
                        case TelephonyManager.NETWORK_TYPE_LTE:    //api<11 : replace by 13
                            type = NETWORKTYPE_4G;
                            break;
                        default:
                            // http://baike.baidu.com/item/TD-SCDMA 中国移动 联通 电信 三种3G制式
                            if (_strSubTypeName.equalsIgnoreCase("TD-SCDMA") || _strSubTypeName.equalsIgnoreCase("WCDMA") || _strSubTypeName.equalsIgnoreCase("CDMA2000")) {
                                type = NETWORKTYPE_3G;
                            } else {
                                type = NETWORKTYPE_MOBILE;
                            }
                            break;
                    }
                    Log.d(TAG, "Network getSubtype : " + Integer.valueOf(networkType).toString());
                }
            }
        }
        return type;
    }

    public static String getNetworkTypeName(Context context) {
        String name = "invalid";
        switch (getNetworkType(context)) {
            case NETWORKTYPE_INVALID:
                name = "invalid";
                break;
            case NETWORKTYPE_2G:
                name = "2G";
                break;
            case NETWORKTYPE_3G:
                name = "3G";
                break;
            case NETWORKTYPE_4G:
                name = "4G";
                break;
            case NETWORKTYPE_WIFI:
                name = "wifi";
                break;
            case NETWORKTYPE_MOBILE:
                name = "mobile";
                break;
        }
        return name;
    }

    public static boolean networkAvailable(Context context) {
        return !(NETWORKTYPE_INVALID == getNetworkType(context));
    }

    public static boolean isWifi(Context context) {
        return NETWORKTYPE_WIFI == getNetworkType(context);
    }

    public static void trustAllHosts() {
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[] {};
            }

            @Override
            public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {

            }

            @Override
            public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {

            }
        } };
        // Install the all-trusting trust manager
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
