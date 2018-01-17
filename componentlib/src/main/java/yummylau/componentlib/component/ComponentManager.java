package yummylau.componentlib.component;

import android.app.Application;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import yummylau.componentlib.service.IService;
import yummylau.componentlib.service.ServiceManager;

/**
 * 组件注册管理
 * Email yummyl.lau@gmail.com
 * Created by g8931 on 2018/1/17.
 */

public class ComponentManager {

    private static final String TAG = ServiceManager.class.getSimpleName();

    private static Map<String, IComponent> sServiceMap = new HashMap<>();

    @Nullable
    public static IComponent getService(String servicePath) {
        IComponent result = sServiceMap.get(servicePath);
        if (result == null) {
            Log.w(TAG, "has not service which match a servicePath key!");
        }
        return result;
    }

    public static void register(Application application, String servicePath, IComponent service) {
        if (TextUtils.isEmpty(servicePath) || service == null) {
            Log.w(TAG, "servicePath and service can't be null");
            return;
        }
        sServiceMap.put(servicePath, service);
        service.createAsLibrary(application);
    }

    public static void unRegister(String servicePath) {
        if (TextUtils.isEmpty(servicePath)) {
            Log.w(TAG, "servicePath can't be null");
            return;
        }
        IComponent component = sServiceMap.remove(servicePath);
        component.release();
    }
}
