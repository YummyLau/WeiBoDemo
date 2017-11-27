package yummylau.common.net;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import yummylau.common.BuildConfig;
import yummylau.common.net.interceptor.CacheInterceptor;
import yummylau.common.net.interceptor.LogInterceptor;

/**
 * Created by yummylau on 2017/11/14.
 */
public class HttpManager {

    private final static String TAG = HttpManager.class.getSimpleName();
    private static OkHttpClient.Builder sHttpClientBuilder;
    private static Context sContext;

    public static void init(Application application) {
        sContext = application;
        sHttpClientBuilder = new OkHttpClient.Builder();
    }

    public static <T> T create(@NonNull HttpParam httpParam, @NonNull Class<T> apiClass) {
        if (sContext == null) {
            throw new RuntimeException("you should init in Application#onCreate!");
        }
        if (httpParam == null || apiClass == null) {
            new IllegalArgumentException("illegalArgument!");
        }
        sHttpClientBuilder.connectTimeout(httpParam.connectTimeOut, TimeUnit.SECONDS);
        sHttpClientBuilder.readTimeout(httpParam.readTimeOut, TimeUnit.SECONDS);
        sHttpClientBuilder.writeTimeout(httpParam.writeTimeOut, TimeUnit.SECONDS);
        sHttpClientBuilder.interceptors().clear();
        if (httpParam.supportCache) {
            sHttpClientBuilder.addInterceptor(new CacheInterceptor(true, NetworkUtils.networkAvailable(sContext)));
        } else {
            sHttpClientBuilder.addInterceptor(new CacheInterceptor(false, NetworkUtils.networkAvailable(sContext)));
        }
        if (BuildConfig.DEBUG) {
            sHttpClientBuilder.addInterceptor(new LogInterceptor());
        }
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(httpParam.baseUrl)
                .addConverterFactory(httpParam.converterFactory)
                .addCallAdapterFactory(httpParam.callAdapterFactory);

        return builder.client(sHttpClientBuilder.build())
                .build().create(apiClass);

    }

}