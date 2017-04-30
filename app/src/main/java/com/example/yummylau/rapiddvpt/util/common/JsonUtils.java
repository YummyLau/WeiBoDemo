package com.example.yummylau.rapiddvpt.util.common;

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
            e.printStackTrace();
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
    public static JSONObject getSaveJSONObject(JSONObject jsonObject, String name) {
        JSONObject value = null;
        try {
            if (jsonObject.has(name)) {
                value = jsonObject.getJSONObject(name);
            }
        } catch (Exception e) {
            e.printStackTrace();
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
    public static JSONArray getSaveJSONArray(JSONObject jsonObject, String name) {
        JSONArray value = null;
        try {
            if (jsonObject.has(name)) {
                value = jsonObject.getJSONArray(name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * 获取字符串
     *
     * @param jsonObject
     * @param name
     * @return
     */
    public static String getSaveString(JSONObject jsonObject, String name) {
        String value = null;
        try {
            if (jsonObject.has(name) && !jsonObject.isNull(name)) {
                value = jsonObject.getString(name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * 获取已过滤HTML特殊字符的字符串
     *
     * @param jsonObject
     * @param name
     * @return
     */
    public static String getSaveStringFilterHTMLTag(JSONObject jsonObject, String name) {
        return StringUtils.filterHTMLTag(getSaveString(jsonObject, name));
    }

    /**
     * 获取 Double
     *
     * @param jsonObject
     * @param name
     * @param defaultValue
     * @return
     */
    public static Double getSaveDouble(JSONObject jsonObject, String name, double defaultValue) {
        Double value = defaultValue;
        try {
            if (jsonObject.has(name)) {
                value = jsonObject.getDouble(name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * 获取 Int
     *
     * @param jsonObject
     * @param name
     * @param defaultValue
     * @return
     */
    public static Integer getSaveInt(JSONObject jsonObject, String name, int defaultValue) {
        Integer value = defaultValue;
        try {
            if (jsonObject.has(name)) {
                value = jsonObject.getInt(name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * 获取 Boolean
     *
     * @param jsonObject
     * @param name
     * @param defaultValue
     * @return
     */
    public static Boolean getSaveBoolean(JSONObject jsonObject, String name, boolean defaultValue) {
        Boolean value = defaultValue;
        try {
            if (jsonObject.has(name)) {
                value = jsonObject.getBoolean(name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
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
        Long value = defaultValue;
        try {
            if (jsonObject.has(name)) {
                value = jsonObject.getLong(name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }
}
