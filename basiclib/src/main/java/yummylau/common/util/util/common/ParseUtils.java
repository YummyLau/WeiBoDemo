package yummylau.common.util.util.common;

import android.text.TextUtils;
import android.util.Log;

/**
 * 解析基本类型，防止出错
 * Created by yummyLau on 17-4-30
 * Email: yummyl.lau@gmail.com
 */

public class ParseUtils {

    private static final String TAG = ParseUtils.class.getSimpleName();

    /**
     * 字符串转化为int
     *
     * @param string
     * @param defaultValue
     * @return
     */
    public static int parseInt(String string, int defaultValue) {

        if (TextUtils.isEmpty(string.trim())) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(string.trim());
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return defaultValue;
    }

    /**
     * 字符串转化为double
     *
     * @param string
     * @param defaultValue
     * @return
     */
    public static double parseDouble(String string, double defaultValue) {

        if (TextUtils.isEmpty(string.trim())) {
            return defaultValue;
        }
        try {
            return Double.parseDouble(string.trim());
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return defaultValue;
    }

    /**
     * 字符串转化为long
     *
     * @param string
     * @param defaultValue
     * @return
     */
    public static long parseLong(String string, long defaultValue) {

        if (TextUtils.isEmpty(string.trim())) {
            return defaultValue;
        }
        try {
            return Long.parseLong(string.trim());
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return defaultValue;
    }

    /**
     * 字符串转化为float
     *
     * @param string
     * @param defaultValue
     * @return
     */
    public static float parseFloat(String string, float defaultValue) {
        if (TextUtils.isEmpty(string.trim())) {
            return defaultValue;
        }
        try {
            return Float.parseFloat(string.trim());
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return defaultValue;
    }

    /**
     * 字符串转化为boolean
     *
     * @param string
     * @param defaultValue
     * @return
     */
    public static Boolean parseBoolean(String string, Boolean defaultValue) {
        if (TextUtils.isEmpty(string.trim())) {
            return defaultValue;
        }
        try {
            return Boolean.parseBoolean(string.trim());
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return defaultValue;
    }

    /**
     * 字符串转化为Short
     *
     * @param string
     * @param defaultValue
     * @return
     */
    public static Short parseShort(String string, Short defaultValue) {
        if (TextUtils.isEmpty(string.trim())) {
            return defaultValue;
        }
        try {
            return Short.parseShort(string.trim());
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return defaultValue;
    }

}
