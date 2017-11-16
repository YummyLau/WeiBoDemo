package com.example.yummylau.rapiddvpt.NET;

import com.netease.hearthstone.app.AppConfig;
import com.netease.hearthstone.http.interceptor.CacheInterceptorByHeader;
import com.netease.hearthstone.http.interceptor.LoggingInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * http请求，封装retrofit+okhttp3
 * Created by g8931 on 2017/3/27.
 */
public class HttpClient {

    private static OkHttpClient sOkHttpClient;                              //全局okhttpClient
    private static Retrofit sGsonRetrofit;                                  //全局retrofit，默认使用Gson解析
    private static Retrofit sStringRetrofit;                                //全局retrofit，默认使用Gson解析
    public static final int TYPE_GSON = 0x0001;
    public static final int TYPE_STRING = 0x0002;


    public static void init() {
        sOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(Constants.DEFAULT_CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(Constants.DEFAULT_READ_TIMEOUT, TimeUnit.SECONDS)
//                .addInterceptor(new DynamicHeaderInterceptor())                     //设置useragent等信息
                .addInterceptor(new LoggingInterceptor())                           //设置log调试
//                .addInterceptor(new TokenInterceptor())                             //设置token过期刷新
                .addNetworkInterceptor(new CacheInterceptorByHeader())              //设置在线缓存，有service api header控制
                .build();
        sGsonRetrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(AppConfig.getMainHostUrl())
                .client(sOkHttpClient)
                .build();

        sStringRetrofit = new Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(AppConfig.getMainHostUrl())
                .client(sOkHttpClient)
                .build();
    }

    public static <S> S createService(Class<S> serviceClass) {
        return createService(serviceClass, TYPE_GSON);
    }

    public static <S> S createService(Class<S> serviceClass, int retrofitType) {
        if(sOkHttpClient == null || sGsonRetrofit == null || sStringRetrofit == null){
            init();
        }
        if (retrofitType == TYPE_STRING) {
            return sStringRetrofit.create(serviceClass);
        }
        return sGsonRetrofit.create(serviceClass);
    }
}
