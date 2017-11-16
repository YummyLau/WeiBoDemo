package com.example.yummylau.rapiddvpt.NET.interceptor;

import com.netease.hearthstone.http.Constants;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * token刷新
 * Created by g8931 on 2017/3/27.
 */

public class TokenInterceptor implements Interceptor {


    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response originalResponse = chain.proceed(request);
        ResponseBody requestBody = originalResponse.body();
        BufferedSource source = requestBody.source();
        source.request(Long.MAX_VALUE);
        Buffer buffer = source.buffer();
        Charset charset = Constants.UTF8;
        MediaType contentType = requestBody.contentType();
        if (contentType != null) {
            charset = contentType.charset(Constants.UTF8);
        }
        String bodyString = buffer.clone().readString(charset);

        if (false) {   // TODO: 2017/3/27 判断token过去
            //取出本地token
            String refreshToken = "我是过期的token";
            // TODO: 2017/3/27 通过retorfit serviceapi取得token
            String newToken = "我是新的token";
            Request newRequest = request.newBuilder().header("token", newToken).build();
            originalResponse.body().close();
            return chain.proceed(newRequest);
        }
        return originalResponse;
    }
}
