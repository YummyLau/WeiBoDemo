package yummylau.common.systemui;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 1. 系统栏沉浸式处理方式
 * a. 在style/style-19中设置对应的activity主题
 * b. 本来在5.0以上可以使用statusBarColor/colorPrimaryDark控制同时使用toolbar进行配合，
 * 在设置style-21/23过程中发现小米，魅族，努比亚等机型有着不同的表现，故4.4以上统一使用initSystemBar进行着色处理
 * c. androidM中提供了对状态栏图标字体设置白色和黑色，同时小米和魅族在特定版本进行支持；若将状态栏设置成浅色/白色导致与
 * 系统提供的图标字体颜色相近，则目前只能设配6.0以上及小米/魅族部分机型。
 * <p>
 * <p>
 * 关于windowInsets 参考：https://stackoverflow.com/questions/33585225/what-are-windowinsets
 * CollapsingToolbarLayout，该布局内部会消费掉windowInsets
 * <p>
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2017/12/11.
 */
public class SystemUIUtil {

    public static final int STATUSBAR_TYPE_DEFAULT = 0x0000;
    public static final int STATUSBAR_TYPE_MIUI = 0x0001;
    public static final int STATUSBAR_TYPE_FLYME = 0x0002;
    public static final int STATUSBAR_TYPE_ANDROID6 = 0x0003;
    public static int sStatuBarType = STATUSBAR_TYPE_DEFAULT;



    //API 19 全屏模式--粘性沉浸式，状态栏上浮盖着Activity；滑边缘唤出系统Ui为透明，可自动隐藏
    public static final int FULLSCREEN_UNDER_STATUSBAR_IMMERSIVE_STICKY = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

    //API 19 全屏模式--粘性沉浸式，状态栏隐藏；滑边缘唤出系统Ui为透明，可自动隐藏
    public static final int FULLSCREEN_NO_STATUSBAR_IMMERSIVE_STICKY = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_FULLSCREEN
            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

    //API 16
    public static final int FULLSCREEN_NO_STATUSBAR = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_FULLSCREEN;


    //API 19 全屏模式--非粘性沉浸式，状态栏上浮盖着Activity；滑边缘唤出系统相当于调用showSystemUi方法
    public static final int FULLSCREEN_UNDER_STATUSBAR_IMMERSIVE = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            | View.SYSTEM_UI_FLAG_IMMERSIVE;

    //API 19 全屏模式--非粘性沉浸式，状态栏隐藏；滑边缘唤出系统相当于调用showSystemUi方法
    public static final int FULLSCREEN_NO_STATUSBAR_IMMERSIVE = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_FULLSCREEN
            | View.SYSTEM_UI_FLAG_IMMERSIVE;

    /**
     * 对状态栏进行着色
     * 若需要对4.4以上布局设置透明，则可以在xml中配置即可，但是5.0以上存在半透明现象，故透明处理同样在该方法中处理以做兼容。
     * 理论上，该方法可对状态栏着任何颜色
     *
     * @param activity
     * @param color
     */
    @Deprecated
    public static void initSystemBar(Activity activity, @ColorInt int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT &&
                Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Window mWindow = activity.getWindow();
            mWindow.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            SystemBarTintManager mTintManager = new SystemBarTintManager(activity);
            mTintManager.setStatusBarTintEnabled(true);
            mTintManager.setStatusBarTintColor(color);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //兼容5.0及以上支持全透明
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            int visibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            activity.getWindow().getDecorView().setSystemUiVisibility(visibility);

            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color);
        }
    }

    /**
     * 倩女业务
     * 兼容“网易助手”，由于状态栏颜色白色，故需要对字体图标做黑色处理。
     * 兼容机型：6.0以上/，小米/魅族特定机型
     *
     * @param activity
     * @param color
     */
    @Deprecated
    public static void initSystemBarForQN(Activity activity, @ColorInt int color) {
         //系统的默认状态栏颜色为白色，正常模式下我们的状态栏为白色，所以需要抹除对状态栏的修改
        if (notMiuiOrFlymeFromKitkatToM()) {
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //兼容5.0及以上支持全透明
                Window window = activity.getWindow();
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                int visibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                activity.getWindow().getDecorView().setSystemUiVisibility(visibility);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                //5.0以上，设置statusBarColor必须取消FLAG_TRANSLUCENT_STATUS FLGA和设置 FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS
                window.setStatusBarColor(ContextCompat.getColor(activity,android.R.color.black));
            }
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT &&
                Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Window mWindow = activity.getWindow();
            mWindow.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            SystemBarTintManager mTintManager = new SystemBarTintManager(activity);
            mTintManager.setStatusBarTintEnabled(true);
            mTintManager.setStatusBarTintColor(color);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //兼容5.0及以上支持全透明
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            int visibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            activity.getWindow().getDecorView().setSystemUiVisibility(visibility);

            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //5.0以上，设置statusBarColor必须取消FLAG_TRANSLUCENT_STATUS FLGA和设置 FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS
            window.setStatusBarColor(color);
        }

        appLightMode(activity);
    }

    /**
     * 是否是[4.4-6.0) 版本区间中的非小米魅族手机
     *
     * @return
     */
    @Deprecated
    public static boolean notMiuiOrFlymeFromKitkatToM() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return !(RomUtil.isFlyme() || RomUtil.isMiui());
        } else {
            return false;
        }
    }

    /**
     * 处理图标高亮
     *
     * @param activity
     */
    @Deprecated
    public static void appLightMode(Activity activity) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int visibility = activity.getWindow().getDecorView().getSystemUiVisibility();
            visibility |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            activity.getWindow().getDecorView().setSystemUiVisibility(visibility);
        }

        if (StatusbarUtil.lightMIUIStatusbarText(activity.getWindow(), true)) {
            return;
        }

        if (StatusbarUtil.lightFlymeStatusbarText(activity.getWindow(), true)) {
            return;
        }
    }



    /**
     * 设置状态栏字体图标为深色，需要MIUIV6以上
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    @Deprecated
    public static boolean MIUISetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            Class clazz = window.getClass();
            try {
                int darkModeFlag;
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                if (dark) {
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag);//状态栏透明且黑色字体
                } else {
                    extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
                }
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
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


    /**
     * 沉浸式状态栏
     * 支持 4.4 以上版本的 MIUI 和 Flyme，以及 5.0 以上版本的其他 Android
     *
     * @param activity
     */
    @TargetApi(19)
    public static void translucent(Activity activity, @ColorInt int colorOn5x) {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            // 版本小于4.4，绝对不考虑沉浸式
            return;
        }

        // 小米和魅族4.4 以上版本支持沉浸式
        if (RomUtil.isFlyme() || RomUtil.isMiui()) {
            Window window = activity.getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && supportTransclentStatusBar6()) {
                // android 6以后可以改状态栏字体颜色，因此可以自行设置为透明
                // ZUK Z1是个另类，自家应用可以实现字体颜色变色，但没开放接口
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
            } else {
                // android 5不能修改状态栏字体颜色，因此直接用FLAG_TRANSLUCENT_STATUS，nexus表现为半透明
                // update: 部分手机运用FLAG_TRANSLUCENT_STATUS时背景不是半透明而是没有背景了。。。。。
                // window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                // 采取setStatusBarColor的方式，部分机型不支持，那就纯黑了，保证状态栏图标可见
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(colorOn5x);
            }
        }
    }

    public static boolean supportTransclentStatusBar6() {
        return true;
    }

    /**
     * 设置状态栏黑色字体图标，
     * 支持 4.4 以上版本 MIUI 和 Flyme，以及 6.0 以上版本的其他 Android
     *
     * @param activity 需要被处理的 Activity
     */
    public static void setStatusBarLightMode(Activity activity) {
        if (sStatuBarType != STATUSBAR_TYPE_DEFAULT) {
            setStatusBarLightMode(activity, sStatuBarType);
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (StatusbarUtil.lightMIUIStatusbarText(activity.getWindow(), true)) {
                sStatuBarType = STATUSBAR_TYPE_MIUI;
            } else if (StatusbarUtil.lightFlymeStatusbarText(activity.getWindow(), true)) {
                sStatuBarType = STATUSBAR_TYPE_FLYME;
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Window window = activity.getWindow();
                View decorView = window.getDecorView();
                int systemUi = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                systemUi = changeStatusBarModeRetainFlag(window, systemUi);
                decorView.setSystemUiVisibility(systemUi);
                sStatuBarType = STATUSBAR_TYPE_ANDROID6;
            }
        }
    }

    /**
     * 已知系统类型时，设置状态栏黑色字体图标。
     * 支持 4.4 以上版本 MIUI 和 Flyme，以及 6.0 以上版本的其他 Android
     *
     * @param activity 需要被处理的 Activity
     * @param type     StatusBar 类型，对应不同的系统
     */
    @TargetApi(11)
    private static void setStatusBarLightMode(Activity activity, int type) {
        if (type == STATUSBAR_TYPE_MIUI) {
            StatusbarUtil.lightMIUIStatusbarText(activity.getWindow(), true);
        } else if (type == STATUSBAR_TYPE_FLYME) {
            StatusbarUtil.lightFlymeStatusbarText(activity.getWindow(), true);
        } else if (type == STATUSBAR_TYPE_ANDROID6) {
            Window window = activity.getWindow();
            View decorView = window.getDecorView();
            int systemUi = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            systemUi = changeStatusBarModeRetainFlag(window, systemUi);
            decorView.setSystemUiVisibility(systemUi);
        }
    }

    /**
     * 设置状态栏白色字体图标
     * 支持 4.4 以上版本 MIUI 和 Flyme，以及 6.0 以上版本的其他 Android
     */
    @TargetApi(11)
    public static void setStatusBarDarkMode(Activity activity) {
        if (sStatuBarType == STATUSBAR_TYPE_DEFAULT) {
            // 默认状态，不需要处理
            return;
        }
        if (sStatuBarType == STATUSBAR_TYPE_MIUI) {
            StatusbarUtil.lightMIUIStatusbarText(activity.getWindow(), false);
        } else if (sStatuBarType == STATUSBAR_TYPE_FLYME) {
            StatusbarUtil.lightFlymeStatusbarText(activity.getWindow(), false);
        } else if (sStatuBarType == STATUSBAR_TYPE_ANDROID6) {
            Window window = activity.getWindow();
            View decorView = window.getDecorView();
            int systemUi = View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            systemUi = changeStatusBarModeRetainFlag(window, systemUi);
            decorView.setSystemUiVisibility(systemUi);
        }
    }

    /**
     * 每次设置SystemUiVisibility要保证其它必须的flag不能丢
     */
    private static int changeStatusBarModeRetainFlag(Window window, int out) {
        out = retainSystemUiFlag(window, out, View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        out = retainSystemUiFlag(window, out, View.SYSTEM_UI_FLAG_FULLSCREEN);
        out = retainSystemUiFlag(window, out, View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        out = retainSystemUiFlag(window, out, View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        out = retainSystemUiFlag(window, out, View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        out = retainSystemUiFlag(window, out, View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        return out;
    }

    @TargetApi(11)
    public static int retainSystemUiFlag(Window window, int out, int type) {
        int now = window.getDecorView().getSystemUiVisibility();
        //如果当前window包含type，则out需要|上type
        if ((now & type) == type) {
            out |= type;
        }
        return out;
    }

    /**
     * 根据状态值选择不同的隐藏模式
     *
     * @param activity
     * @param status
     */
    @TargetApi(16)
    public static void handleSystemUiForStatus(Activity activity, int status) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            activity.getWindow().getDecorView().setSystemUiVisibility(status);
        }
    }
}
