package com.example.yummylau.rapiddvpt.net;

import android.content.Context;
import android.util.Log;


import com.example.yummylau.rapiddvpt.util.common.NetUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;


/**
 * Created by g8165 on 2016/11/28.
 *
 * Retrofit请求方法,interceptor拦截器内还可添加固定参数,host,https设置等
 *
 * 备注 by g8675：
 * 1. 用@url 方式可以忽略baseurl
 * 2. @Headers可以对部分api进行head配置（userAgent等等）
 *
 */

public class RetrofitManager {

    private final static String TAG = "retrofit";

    private static Context sApplicationContext;

    private static OkHttpClient sCachedOkHttpClient;

    private static OkHttpClient sNoCachedOkHttpClient;

    /***
     * key: RetrofitParam.getKey
     */
    private static HashMap<String, Retrofit> sRetrofits = new HashMap<>();

    /** 没有网络上时，缓存策略 */
    private final static CacheControl NO_NETWORK_CACHE_CONTROL = new CacheControl
            .Builder()
            .maxAge(Constants.MAX_AGE, TimeUnit.SECONDS)
            .maxStale(Constants.MAX_STALE, TimeUnit.SECONDS)
            .build();

    public static void init(final Context context) {
        sApplicationContext = context.getApplicationContext();
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .connectTimeout(Constants.DEFAULT_CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(Constants.DEFAULT_READ_TIMEOUT, TimeUnit.SECONDS);

        // 带缓存的Interceptor, io线程执行
        final Interceptor cacheInterceptor = new BaseInterceptor() {

            CacheControl cacheControl = new CacheControl.Builder()
                    .maxAge(Constants.MAX_AGE, TimeUnit.SECONDS)
                    .maxStale(0, TimeUnit.SECONDS)
                    .build();

            @Override
            public CacheControl getCacheControl() {
                return NetUtils.isConnected(context) ? cacheControl : NO_NETWORK_CACHE_CONTROL;
            }

        };
        httpClient.interceptors().add(cacheInterceptor);
        sCachedOkHttpClient = httpClient.build();
        httpClient.interceptors().remove(cacheInterceptor);

        // 没有缓存的Interceptor, io线程执行
        final Interceptor noCacheInterceptor = new BaseInterceptor() {

            CacheControl cacheControl = new CacheControl.Builder().noCache().build();

            @Override
            public CacheControl getCacheControl() {
                return cacheControl;
            }
        };
        httpClient.interceptors().add(noCacheInterceptor);
        sNoCachedOkHttpClient = httpClient.build();
    }

    /***
     * 根据参数获取对应的Retrofit实例
     * @param retrofitParam
     */
    private static Retrofit getRetrofit(RetrofitParam retrofitParam) {
        final String key = retrofitParam.getKey();
        Retrofit retrofit;
        synchronized (sRetrofits) {
            retrofit = sRetrofits.get(retrofitParam.getKey());
            if (retrofit == null) {
                retrofit = new Retrofit.Builder()
                        .addConverterFactory(retrofitParam.converterFactory)
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .baseUrl(retrofitParam.baseUrl)
                        .client(retrofitParam.cache ? sCachedOkHttpClient : sNoCachedOkHttpClient)
                        .build();
                sRetrofits.put(key, retrofit);
            }
        }
        return retrofit;
    }

    public static <S> S createService(RetrofitParam retrofitParam, Class<S> serviceClass) {
        if (sApplicationContext == null) {
            throw new RuntimeException("Application中缺少RetrofitManager.init初始化");
        }
        try {
            return getRetrofit(retrofitParam).create(serviceClass);
        }catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return null;
    }

    /**
     * Request基础拦截器
     * io线程执行
     */
    private static abstract class BaseInterceptor implements Interceptor {
        @Override
        public okhttp3.Response intercept(Interceptor.Chain chain) throws IOException {
            Response response = null;
            try {
                Request oriRequest = chain.request();
                Request.Builder requestBuilder = oriRequest.newBuilder()
                        .cacheControl(getCacheControl())
                        .method(oriRequest.method(), oriRequest.body());

                Request request = requestBuilder.build();
                response = chain.proceed(request);

                Log.d(TAG, "code:" + response.code() + ", url:" + request.url());
            } catch (StackOverflowError e) {
                Log.e(TAG, "", e);
            }
            return response;
        }

        public abstract CacheControl getCacheControl();

    }
}
