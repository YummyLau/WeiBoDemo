package com.example.yummylau.rapiddvpt.NET;

import java.nio.charset.Charset;

/**
 * Http module constants
 * Created by g8931 on 2017/3/17.
 */

public class Constants {

    //统一 host配置
    public static final String BASE_URL ="http://app.16163.com/";
    public static final String DEFAULT_BASE_URL = "http://xm.gameyw.netease.com:8080/xiaomei/";     //默认BASE_RUL
    public static final Charset UTF8 = Charset.forName("UTF-8");                                    //默认编码格式
    public final static String DEFAULT_POST_CONTENT_TYPE = "application/x-www-form-urlencoded";     // 默认ContentType
    public static final String NOS_HOST=  "http://nos.gameyw.netease.com/gameyw-gbox/";//BASE_URL_3,用于用户头像上传


    //请求客户端配置
    public final static long DEFAULT_CONNECT_TIMEOUT = 15;
    public final static long DEFAULT_READ_TIMEOUT = 30;
    public final static long CACHE_SIZE = 1024 * 1024 * 100;                    //缓存大小100MB
    public final static int MAX_AGE = 10;
    public final static int MAX_STALE = 60;


    //社区相关常量
    public static final int TYPE_POST  = 0x0002;
    public static final int TYPE_COMMENT = 0x0003;
    public static final String ORDER_BY_CREATE_TIME = "createtime";
    public static final String ORDER_BY_HOT_COUNT = "hot_count";
    public static final String ORDER_BY_ID = "id";

}
