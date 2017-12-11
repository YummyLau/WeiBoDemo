package yummylau.common.net;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 网络变化广播
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2017/12/11.
 */

public class NetworkChangeReceiver extends BroadcastReceiver {

    private static final String TAG = NetworkChangeReceiver.class.getSimpleName();

    @SuppressWarnings("all")
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = mConnectivityManager.getActiveNetworkInfo();
//        if (netInfo != null && netInfo.isAvailable()) {
//            int type = netInfo.getType();
//            if (type == ConnectivityManager.TYPE_WIFI && type != lastType) {                        //WiFi网络
//                ToastUtils.showShort(context, "已切换为WIFI网络");
//            } else if (type == ConnectivityManager.TYPE_MOBILE && type != lastType) {               //移动网络
//                ToastUtils.showShort(context, "已切换为数据流量");
//            }
//            lastType = type;
////            if (mNetChangeListener != null)
////                mNetChangeListener.onNetChange(type);
////            if (mNetChangeListeners.size() > 0) {    // 通知接口完成加载
////                for (NetChangeListener handler : mNetChangeListeners) {
////                    handler.onNetChange(type);
////                }
////            }
//
//            EventBusUtils.post(new NetChangeEvent(type));
//
//        } else {
//            //网络断开
//            ToastUtils.showShort(context, "当前网络不可用");
//            lastType = TYPE_NONE;
//        }
    }
}
