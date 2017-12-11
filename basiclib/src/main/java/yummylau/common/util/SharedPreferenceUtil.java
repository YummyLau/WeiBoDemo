package yummylau.common.util;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 轻量级数据存储
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2017/12/11.
 */

public class SharedPreferenceUtil {

    private static final String TAG = SharedPreferenceUtil.class.getSimpleName();
    private SharedPreferences mSharedPreferences;
    private static Context sApplicationContext;
    private static HashMap<String, WeakReference<SharedPreferenceUtil>> sInstances = new HashMap<>();

    /**
     * {@link Application#onCreate()} 初始化
     *
     * @param context
     */
    public static void init(@NonNull Context context) {
        if (context == null) {
            throw new RuntimeException(TAG + "#init: context can't be null !");
        }
        sApplicationContext = context;
    }

    private SharedPreferenceUtil(@NonNull SharedPreferences sharedPreferences) {
        mSharedPreferences = sharedPreferences;
    }

    public static SharedPreferenceUtil getInstance(@NonNull String preferenceName) {
        return getInstance(preferenceName, Context.MODE_PRIVATE);
    }

    public static SharedPreferenceUtil getInstance(String preferenceName, int mode) {
        WeakReference<SharedPreferenceUtil> weakReference = sInstances.get(preferenceName);
        SharedPreferenceUtil sharedPreferenceUtil = weakReference == null ? null : weakReference.get();
        if (sharedPreferenceUtil == null) {
            sharedPreferenceUtil = new SharedPreferenceUtil(sApplicationContext.getSharedPreferences(preferenceName, mode));
            sInstances.put(preferenceName, new WeakReference<>(sharedPreferenceUtil));
        }
        return sharedPreferenceUtil;
    }

    public void putString(String key, String value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(key, value).commit();
    }

    public String getString(String key, String defaultValue) {
        return mSharedPreferences.getString(key, defaultValue);
    }

    public void putStringSet(String key, Set<String> values) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putStringSet(key, values).commit();
    }

    public Set<String> getStringSet(String key, Set<String> defaultValue) {
        return mSharedPreferences.getStringSet(key, defaultValue);
    }

    public void putStrings(Map<String, String> map) {
        if (map != null) {
            SharedPreferences.Editor editor = mSharedPreferences.edit();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                editor.putString(entry.getKey(), entry.getValue());
            }
            editor.commit();
        }
    }

    public void putInt(String key, int value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putInt(key, value).commit();
    }

    public int getInt(String key, int defaultValue) {
        return mSharedPreferences.getInt(key, defaultValue);
    }

    public void putBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(key, value).commit();
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return mSharedPreferences.getBoolean(key, defaultValue);
    }

    public void putFloat(String key, float value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putFloat(key, value).commit();
    }

    public float getFloat(String key, float defaultValue) {
        return mSharedPreferences.getFloat(key, defaultValue);
    }

    public void putLong(String key, long value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putLong(key, value).commit();
    }

    public long getLong(String key, long defaultValue) {
        return mSharedPreferences.getLong(key, defaultValue);
    }

    public boolean clearConfig() {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        return editor.clear().commit();
    }
}
