package com.example.yummylau.rapiddvpt.NET;

import android.content.Context;

import com.netease.cloud.nos.android.core.Callback;
import com.netease.cloud.nos.android.core.WanAccelerator;
import com.netease.cloud.nos.android.core.WanNOSObject;
import com.netease.cloud.nos.android.exception.InvalidParameterException;
import com.netease.cloud.nos.android.utils.Util;
import com.netease.hearthstone.api.CommonApis;
import com.netease.hearthstone.biz.LogPrinter;
import com.netease.hearthstone.utils.FileUtils;
import com.netease.hearthstone.utils.common.URLUtils;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by n3207 on 2017/3/10.
 */
public class HttpHelper {

    private final static String TAG = "HttpHelper";
    private final static String DEFAULT_POST_CONTENT_TYPE = "application/x-www-form-urlencoded";    // 默认ContentType
    private final static int DEFAULT_CONNECT_TIME_OUT_MILLIS_NORMAL = 10 * 1000;                            // 10秒连接超时
    private final static int DEFAULT_READ_TIME_OUT_MILLIS_NORMAL = 30 * 1000;                                // 30秒读取超时


    /**
     * 用Retrofit获取数据的封装
     * 传url返回string 通用各种接口
     * 返回Observable<String>
     * @param url
     */
    public static Observable<String> getData(final String url) {
        if (url == null) return null;
        LogPrinter.i(TAG, "getData-->" + url);
        //去掉baseurl 用@url 方式会忽略baseurl
        RetrofitParam retrofitParam = new RetrofitParam.Builder()
                .converterFactory(ScalarsConverterFactory.create())
                .build();
        return RetrofitManager.createService(retrofitParam, CommonApis.class)
                .getData(url)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 用Retrofit获取数据的封装
     * 传url返回string 通用各种接口
     *
     * @param url
     * @param cache
     * @param subscriber
     */
    public static void getData(final String url, boolean cache, final Subscriber<String> subscriber) {
        if (url == null || subscriber == null) return;
        LogPrinter.i(TAG, "getData-->" + url);
        //去掉baseurl 用@url 方式会忽略baseurl
        RetrofitParam retrofitParam = new RetrofitParam.Builder()
                .cache(cache)
                .converterFactory(ScalarsConverterFactory.create())
                .build();
        RetrofitManager.createService(retrofitParam, CommonApis.class)
                .getData(url)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }



    public static String doHttpPost(String urlString, String postData,String contentType,int connectTimeout, int readTimeOut, HashMap<String, String> propertyMap) {
        if (!URLUtils.verifyURL(urlString)) return null;

        HttpURLConnection connection = null;
        StringBuilder result = null;
        try {
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();

            connection.setConnectTimeout(connectTimeout);
            connection.setReadTimeout(readTimeOut);
            connection.setDoOutput(true);                                                            // 设置向输出流写入内容，默认false
            connection.setDoInput(true);                                                            // 默认true
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);                                                            // post请求不能使用缓存
            connection.setRequestProperty("Content-Type", contentType);
            connection.setRequestProperty("Connection", "close");

            //设置http请求头
            if (propertyMap != null) {
                for (Map.Entry<String, String> entry : propertyMap.entrySet()) {
                    connection.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }

            // 设置post参数
            if (postData != null && postData.length() > 0) {
                byte[] bytes = postData.getBytes();
                connection.setFixedLengthStreamingMode(bytes.length);
                OutputStream os = new BufferedOutputStream(connection.getOutputStream());
                os.write(bytes);
                os.flush();
                os.close();
            }

            connection.connect();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            reader.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        if (result == null) {
            return null;
        } else {
            return result.toString();
        }
    }


    public static String doHttpPost(String urlString, String postData, int connectTimeout, int readTimeOut) {
        return doHttpPost(urlString, postData, DEFAULT_POST_CONTENT_TYPE, connectTimeout, readTimeOut, null);
    }

    public static String doHttpPost(String urlString, String postData,HashMap<String, String> propertyMap){
        return doHttpPost(urlString, postData, DEFAULT_POST_CONTENT_TYPE, DEFAULT_CONNECT_TIME_OUT_MILLIS_NORMAL, DEFAULT_READ_TIME_OUT_MILLIS_NORMAL, propertyMap);

    }

    /**
     * 文件上传nos，支持图片压缩
     *
     * @param context    上下文
     * @param path       图片路径
     * @param token      token
     * @param bucketName 桶名称
     * @param objectName 对象名称
     */
    public static void doUpload(Context context, final String path, final String token, final String bucketName,
                                final String objectName, boolean compress, final Callback callback) {
        //进行图片质量和尺寸的压缩
        final File file;
        if (compress) {
            String compressedPath = FileUtils.getCompressedPath(context, path);
            file = new File(compressedPath);
        } else {
            file = new File(path);
        }

        final Context appContext = context.getApplicationContext();

        WanNOSObject wanNOSObject = new WanNOSObject();
        wanNOSObject.setUploadToken(token);
        wanNOSObject.setNosBucketName(bucketName);
        wanNOSObject.setNosObjectName(objectName);
        if (file.getName().contains(".jpg") || file.getName().contains(".jpeg")) {
            wanNOSObject.setContentType("image/jpeg");
        } else if (file.getName().contains(".png")) {
            wanNOSObject.setContentType("image/png");
        } else if (file.getName().contains(".aac")) {
            wanNOSObject.setContentType("audio/aac");
        }
//        else if (file.getName().contains(".gif")) {
//            wanNOSObject.setContentType("image/gif");
//        }

        String uploadContext = null;
        if (Util.getData(appContext, file.getAbsolutePath()) != null && !Util.getData(appContext, file.getAbsolutePath()).equals("")) {
            uploadContext = Util.getData(appContext, file.getAbsolutePath());
        }

        try {
            WanAccelerator.putFileByHttp(appContext, file, file.getAbsoluteFile(), uploadContext, wanNOSObject, callback);
        } catch (InvalidParameterException e) {
            e.printStackTrace();
        }
    }
}
