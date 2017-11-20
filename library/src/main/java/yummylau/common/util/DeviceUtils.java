package yummylau.common.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.ViewConfiguration;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 设备信息
 * Created by g8931 on 2017/11/20.
 */

public class DeviceUtils {

    private static final String STATUS_BAR_HEIGHT = "status_bar_height";
    private static final String NAVIGATION_BAR_HEIGHT = "navigation_bar_height";

    public static int getStatusBarHeight(Context context) {
        int statusBar = 0;
        try {
            Class c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField(STATUS_BAR_HEIGHT);
            int x = Integer.parseInt(field.get(obj).toString());
            statusBar = context.getResources().getDimensionPixelSize(x);
        } catch (Exception var7) {
            var7.printStackTrace();
        }

        return statusBar;
    }

    public static int getNavBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier(NAVIGATION_BAR_HEIGHT, "dimen", "android");
        return resourceId > 0 ? resources.getDimensionPixelSize(resourceId) : 0;
    }

    /**
     * 判断是否有虚拟按键，（只判断4.4之后）
     *
     * @param context 上下文
     * @return 是否支持
     */
    @SuppressLint("NewApi")
    public static boolean hasNavigationBar(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            boolean hasPermanentMenuKey = ViewConfiguration.get(context).hasPermanentMenuKey();
            boolean hasMenuKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_MENU);
            boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
            if (!hasMenuKey && !hasPermanentMenuKey && !hasBackKey) {
                return true;
            }
        }
        return false;
    }
}
