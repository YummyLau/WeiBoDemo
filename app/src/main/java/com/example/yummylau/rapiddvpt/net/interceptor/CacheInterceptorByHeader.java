package com.example.yummylau.rapiddvpt.net.interceptor;

import com.netease.hearthstone.utils.common.NetUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 由retrofit头部控制缓存
 * Created by g8931 on 2017/3/27.
 */

public class CacheInterceptorByHeader implements Interceptor{

    @Override
    public Response intercept(Chain chain) throws IOException {
        //默认retrofit @headers中统一设置
        Request request = chain.request();
        Response response = chain.proceed(request);

        if (NetUtil.networkAvailable()) {
            //获取头部信息
            String cacheControl =request.cacheControl().toString();
            return response.newBuilder()
                    .removeHeader("Pragma")//清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                    .header("Cache-Control", cacheControl)
                    .build();
        }
        return response;
    }
}
