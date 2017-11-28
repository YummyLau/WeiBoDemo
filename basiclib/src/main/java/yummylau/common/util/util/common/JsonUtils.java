package yummylau.common.util.util.common;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * json转化
 * Created by yummyLau on 17-4-24
 * Email: yummyl.lau@gmail.com
 */

public class JsonUtils {

    private static final String TAG = JsonUtils.class.getSimpleName();

    /**
     * 根据map生成Json字符串
     *
     * @param map Map数据
     * @return Json字符串
     */
    public static String getJsonStr(Map<String, Object> map) {
        JSONObject jsonObject = new JSONObject();
        String str = null;
        try {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                jsonObject.put(key, value);
            }
            str = jsonObject.toString();
        } catch (JSONException e) {
            Log.e(TAG, e.toString());
        }
        return str;
    }

    /**
     * 获取JSONObject
     *
     * @param jsonObject
     * @param name
     * @return
     */
    public static JSONObject getJSONObject(JSONObject jsonObject, String name) {
        JSONObject value = null;
        try {
            if (jsonObject.has(name)) {
                value = jsonObject.getJSONObject(name);
            }
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return value;
    }

    /**
     * 获取JSONArray
     *
     * @param jsonObject
     * @param name
     * @return
     */
    public static JSONArray getJSONArray(JSONObject jsonObject, String name) {
        JSONArray value = null;
        try {
            if (jsonObject.has(name)) {
                value = jsonObject.getJSONArray(name);
            }
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return value;
    }

    /**
     * 获取字符串
     *
     * @param jsonObject
     * @param name
     * @param defaultValue
     * @return
     */
    public static String getString(JSONObject jsonObject, String name, String defaultValue) {
        try {
            if (jsonObject.has(name) && !jsonObject.isNull(name)) {
                return jsonObject.optString(name, defaultValue);
            }
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return defaultValue;
    }

    /**
     * 获取已过滤HTML特殊字符的字符串
     *
     * @param jsonObject
     * @param name
     * @return
     */
    public static String getStringFilterHTMLTag(JSONObject jsonObject, String name, String defaultValue) {
        return StringUtils.filterHTMLTag(getString(jsonObject, name, defaultValue));
    }

    /**
     * 获取 Double
     *
     * @param jsonObject
     * @param name
     * @param defaultValue
     * @return
     */
    public static Double getDouble(JSONObject jsonObject, String name, double defaultValue) {
        try {
            if (jsonObject.has(name)) {
                return jsonObject.optDouble(name, defaultValue);
            }
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return defaultValue;
    }

    /**
     * 获取 Int
     *
     * @param jsonObject
     * @param name
     * @param defaultValue
     * @return
     */
    public static Integer getInt(JSONObject jsonObject, String name, int defaultValue) {
        try {
            if (jsonObject.has(name)) {
                return jsonObject.optInt(name, defaultValue);
            }
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return defaultValue;
    }

    /**
     * 获取 Boolean
     *
     * @param jsonObject
     * @param name
     * @param defaultValue
     * @return
     */
    public static Boolean getBoolean(JSONObject jsonObject, String name, boolean defaultValue) {
        try {
            if (jsonObject.has(name)) {
                return jsonObject.optBoolean(name, defaultValue);
            }
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return defaultValue;
    }

    /**
     * 获取 Long
     *
     * @param jsonObject
     * @param name
     * @param defaultValue
     * @return
     */
    public static Long getSaveLong(JSONObject jsonObject, String name, Long defaultValue) {
        try {
            if (jsonObject.has(name)) {
                return jsonObject.optLong(name, defaultValue);
            }
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return defaultValue;
    }
}
