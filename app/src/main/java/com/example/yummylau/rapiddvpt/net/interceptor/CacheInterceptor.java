package com.example.yummylau.rapiddvpt.NET.interceptor;
import com.netease.hearthstone.utils.common.NetUtil;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 缓存拦截器
 * 如果只实现在线缓存，则通过{@link okhttp3.OkHttpClient.Builder#addNetworkInterceptor(Interceptor)}添加
 * 如果只实现离线缓存，{@link okhttp3.OkHttpClient.Builder#addInterceptor(Interceptor)} 添加
 * 注意点：
 * 1. 只能缓存get请求接口，不能缓存post请求接口
 * 2. 默认添加该拦截器，会对所有请求都使用相同的缓存配置，建议直接在retrofit @headers中统一设置
 * Created by g8931 on 2017/3/27.
 */

public class CacheInterceptor implements Interceptor{

    private static final int MAX_AGE = 10;                  //10秒内重复请求读取缓存
    private static final int MAX_STALE = 60 * 60;           //离线缓存保存1小时

    @Override
    public Response intercept(Chain chain) throws IOException {

//        //默认retrofit @headers中统一设置
//        Request request = chain.request();
//        Response response = chain.proceed(request);
//
//        if (NetworkUtil.getInstance().isConnected()) {
//            //获取头部信息
//            String cacheControl =request.cacheControl().toString();
//            return response.newBuilder()
//                    .removeHeader("Pragma")//清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
//                    .header("Cache-Control", cacheControl)
//                    .build();
//        }
//        return response;

        Request request = chain.request();
        //没网的情况下
        if(!NetUtil.networkAvailable()){
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
        }

        Response originalResponse = chain.proceed(request);
        //网络可用
        if(NetUtil.networkAvailable()){
            //读接口上@Headers配置，
            String cacheControl = request.cacheControl().toString();
            return originalResponse.newBuilder()
                    .header("Cache-Control","max-age="+MAX_AGE)
                    .removeHeader("Progma") //如果服务器不支持，会返回一些干扰信息，不清除下面无法生效
                    .build();
        }else{
            return originalResponse.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-age=" + MAX_STALE)
                    .removeHeader("Pragma")
                    .build();
        }
    }
}
