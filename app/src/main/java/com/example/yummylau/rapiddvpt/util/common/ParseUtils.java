package com.example.yummylau.rapiddvpt.util.common;

import android.text.TextUtils;

/**
 * 解析基本类型，防止出错
 * Created by yummyLau on 17-4-30
 * Email: yummyl.lau@gmail.com
 */

public class ParseUtils {


    /**
     * 字符串转整型
     *
     * @param intString    字符串
     * @param defaultValue 默认值
     * @return 整型
     */
    public static int parseInt(String intString, int defaultValue) {

        if(TextUtils.isEmpty(intString.trim())){
            return defaultValue;
        }
        try {
            return Integer.parseInt(intString.trim());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defaultValue;
    }

}
