package com.example.yummylau.rapiddvpt.net.interceptor;

import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.Locale;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 网络请求log调试
 * Created by g8931 on 2017/3/27.
 */

public class LoggingInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        //打印请求
        Logger.t("LoggingInterceptor").i(String.format("Sending request %s on %s%n%s", request.url(), chain.connection(), request.headers()));
        long t1 = System.nanoTime();
        okhttp3.Response response = chain.proceed(chain.request());
        long t2 = System.nanoTime();
        //打印响应头
        Logger.t("LoggingInterceptor").i(String.format(Locale.getDefault(), "Received response for %s in %.1fms%n%s",
                response.request().url(), (t2 - t1) / 1e6d, response.headers()));
        okhttp3.MediaType mediaType = response.body().contentType();
        //打印响应体
        String content = response.body().string();
        Logger.t("LoggingInterceptor").json(content);
        return response.newBuilder()
                .body(okhttp3.ResponseBody.create(mediaType, content))
                .build();
    }
}
