package com.example.yummylau.rapiddvpt.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.webkit.DownloadListener;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * webview
 * Created by yummyLau on 17-4-30
 * Email: yummyl.lau@gmail.com
 */

public class WebviewUtils {

    private static final String TAG = WebviewUtils.class.getSimpleName();

    public static WebSettings initWebSettings(final WebView webView) {
        WebSettings webSettings = webView.getSettings();
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setSupportZoom(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setDefaultTextEncodingName("utf-8");
        /** 加入地理位置 start */
        // 启用数据库
        webSettings.setDatabaseEnabled(true);
        String dir = webView.getContext().getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();
        // 启用地理定位
        webSettings.setGeolocationEnabled(true);
        // 设置定位的数据库路径
        webSettings.setGeolocationDatabasePath(dir);
        // ***最重要的方法，一定要设置，这就是出不来的主要原因
        webSettings.setDomStorageEnabled(true);
        /** 加入地理位置 end */

        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }

        });

        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                // 利用默认浏览器下载
                try {
                    Uri uri = Uri.parse(url);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    webView.getContext().startActivity(intent);
                } catch (Exception e) {
                    Log.e(TAG,e.toString());
                }
            }
        });
        return webSettings;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static void evaluateJavascript(final WebView webView, final String js) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            webView.post(new Runnable() {
                @Override
                public void run() {
                    webView.evaluateJavascript(js, null);
                }
            });
        } else {
            webView.post(new Runnable() {
                @Override
                public void run() {
                    webView.loadUrl("javascript:" + js);
                }
            });
        }
    }
}
