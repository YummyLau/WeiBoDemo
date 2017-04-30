package com.example.yummylau.rapiddvpt.util.common;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 处理systemui
 * Created by yummyLau on 17-4-30
 * Email: yummyl.lau@gmail.com
 */

public class SystemUiUtils {

    //全屏模式--粘性沉浸式，状态栏上浮盖着Activity；滑边缘唤出系统Ui为透明，可自动隐藏
    public static final int HIDE_IMMERSIVE_STICKY_STATUS_1 = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

    //全屏模式--粘性沉浸式，状态栏隐藏；滑边缘唤出系统Ui为透明，可自动隐藏
    public static final int HIDE_IMMERSIVE_STICKY_STATUS_2 = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_FULLSCREEN
            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

    //全屏模式--非粘性沉浸式，状态栏上浮盖着Activity；滑边缘唤出系统相当于调用showSystemUi方法
    public static final int HIDE_IMMERSIVE_STATUS_1 = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            | View.SYSTEM_UI_FLAG_IMMERSIVE;

    //全屏模式--非粘性沉浸式，状态栏隐藏；滑边缘唤出系统相当于调用showSystemUi方法
    public static final int HIDE_IMMERSIVE_STATUS_2 = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_FULLSCREEN
            | View.SYSTEM_UI_FLAG_IMMERSIVE;


    //显示系统Ui
    public static final int SHOW_SYSTEMUI_STATUS = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;

    //全屏
    public static final int HIDE_SYSTEMUI_STATUS = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_FULLSCREEN;

    /**
     * 悬浮状态栏
     *
     * @param activity
     */
    @TargetApi(11)
    public static void makeStatusBatFloat(Activity activity) {
        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
    }

    /**
     * 在全屏模式下调用，取消全屏模式
     * 仅限于一下状态：
     * HIDE_IMMERSIVE_STICKY_STATUS_1，
     * HIDE_IMMERSIVE_STICKY_STATUS_2，
     * HIDE_IMMERSIVE_STATUS_1，
     * HIDE_IMMERSIVE_STATUS_2
     * @param activity
     */
    @TargetApi(11)
    public static void showSystemUi(Activity activity) {
        activity.getWindow().getDecorView().setSystemUiVisibility(SHOW_SYSTEMUI_STATUS);
    }

    /**
     * 根据状态值选择不同的隐藏模式
     *
     * @param activity
     * @param status
     */
    @TargetApi(11)
    public static void handleSystemUiForStatus(Activity activity, int status) {
        activity.getWindow().getDecorView().setSystemUiVisibility(status);
    }

    public static int getStatusBarHeight(Activity context) {
        Rect rect = new Rect();
        context.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        return rect.top; // 状态栏高度
    }

    public static int SYSTEM_FLYME = 1;
    public static int SYSTEM_MIUI = 2;
    public static int SYSTEM_API23 = 3;
    public static int sSystemBarDarkmode = 0;

    /**
     * 对状态栏进行着色
     * 可以在主题中设置4.4以上的状态栏透明，但是5.0以上的则为半透明。
     * 为了兼容4.4以上的版本都可以全透明，并根据业务需求对状态栏进行着色
     *
     * @param activity
     * @param color
     */
    public static void initSystemBar(Activity activity, int color) {
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
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            sSystemBarDarkmode = SYSTEM_API23;
        } else {
            if (FlymeSetStatusBarLightMode(activity.getWindow(), true)) {
                sSystemBarDarkmode = SYSTEM_FLYME;
            } else if (MIUISetStatusBarLightMode(activity.getWindow(), true)) {
                sSystemBarDarkmode = SYSTEM_MIUI;
            }
        }

    }
    /**
     * 设置状态栏图标为深色和魅族特定的文字风格，Flyme4.0以上
     * 可以用来判断是否为Flyme用户
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    public static boolean FlymeSetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }

    /**
     * 设置状态栏字体图标为深色，需要MIUIV6以上
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    public static boolean MIUISetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            Class clazz = window.getClass();
            try {
                int darkModeFlag = 0;
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

    static class SystemBarTintManager{
        static {
            // Android allows a system property to override the presence of the navigation bar.
            // Used by the emulator.
            // See https://github.com/android/platform_frameworks_base/blob/master/policy/src/com/android/internal/policy/impl/PhoneWindowManager.java#L1076
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                try {
                    Class c = Class.forName("android.os.SystemProperties");
                    Method m = c.getDeclaredMethod("get", String.class);
                    m.setAccessible(true);
                    sNavBarOverride = (String) m.invoke(null, "qemu.hw.mainkeys");
                } catch (Throwable e) {
                    sNavBarOverride = null;
                }
            }
        }


        /**
         * The default system bar tint color value.
         */
        public static final int DEFAULT_TINT_COLOR = 0x99000000;

        private static String sNavBarOverride;

        private final SystemBarConfig mConfig;
        private boolean mStatusBarAvailable;
        private boolean mNavBarAvailable;
        private boolean mStatusBarTintEnabled;
        private boolean mNavBarTintEnabled;
        private View mStatusBarTintView;
        private View mNavBarTintView;

        /**
         * Constructor. Call this in the host activity onCreate method after its
         * content view has been set. You should always create new instances when
         * the host activity is recreated.
         *
         * @param activity The host activity.
         */
        @TargetApi(19)
        @SuppressWarnings("ResourceType")
        public SystemBarTintManager(Activity activity) {

            Window win = activity.getWindow();
            ViewGroup decorViewGroup = (ViewGroup) win.getDecorView();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                // check theme attrs
                int[] attrs = {android.R.attr.windowTranslucentStatus,
                        android.R.attr.windowTranslucentNavigation};
                TypedArray a = activity.obtainStyledAttributes(attrs);
                try {
                    mStatusBarAvailable = a.getBoolean(0, false);
                    mNavBarAvailable = a.getBoolean(1, false);
                } finally {
                    a.recycle();
                }

                // check window flags
                WindowManager.LayoutParams winParams = win.getAttributes();
                int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
                if ((winParams.flags & bits) != 0) {
                    mStatusBarAvailable = true;
                }
                bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
                if ((winParams.flags & bits) != 0) {
                    mNavBarAvailable = true;
                }
            }

            mConfig = new SystemBarConfig(activity, mStatusBarAvailable, mNavBarAvailable);
            // device might not have virtual navigation keys
            if (!mConfig.hasNavigtionBar()) {
                mNavBarAvailable = false;
            }

            if (mStatusBarAvailable) {
                setupStatusBarView(activity, decorViewGroup);
            }
            if (mNavBarAvailable) {
                setupNavBarView(activity, decorViewGroup);
            }

        }

        /**
         * Enable tinting of the system status bar.
         * <p/>
         * If the platform is running Jelly Bean or earlier, or translucent system
         * UI modes have not been enabled in either the theme or via window flags,
         * then this method does nothing.
         *
         * @param enabled True to enable tinting, false to disable it (default).
         */
        public void setStatusBarTintEnabled(boolean enabled) {
            mStatusBarTintEnabled = enabled;
            if (mStatusBarAvailable) {
                mStatusBarTintView.setVisibility(enabled ? View.VISIBLE : View.GONE);
            }
        }

        /**
         * Enable tinting of the system navigation bar.
         * <p/>
         * If the platform does not have soft navigation keys, is running Jelly Bean
         * or earlier, or translucent system UI modes have not been enabled in either
         * the theme or via window flags, then this method does nothing.
         *
         * @param enabled True to enable tinting, false to disable it (default).
         */
        public void setNavigationBarTintEnabled(boolean enabled) {
            mNavBarTintEnabled = enabled;
            if (mNavBarAvailable) {
                mNavBarTintView.setVisibility(enabled ? View.VISIBLE : View.GONE);
            }
        }

        /**
         * Apply the specified color tint to all system UI bars.
         *
         * @param color The color of the background tint.
         */
        public void setTintColor(int color) {
            setStatusBarTintColor(color);
            setNavigationBarTintColor(color);
        }

        /**
         * Apply the specified drawable or color resource to all system UI bars.
         *
         * @param res The identifier of the resource.
         */
        public void setTintResource(int res) {
            setStatusBarTintResource(res);
            setNavigationBarTintResource(res);
        }

        /**
         * Apply the specified drawable to all system UI bars.
         *
         * @param drawable The drawable to use as the background, or null to remove it.
         */
        public void setTintDrawable(Drawable drawable) {
            setStatusBarTintDrawable(drawable);
            setNavigationBarTintDrawable(drawable);
        }

        /**
         * Apply the specified alpha to all system UI bars.
         *
         * @param alpha The alpha to use
         */
        public void setTintAlpha(float alpha) {
            setStatusBarAlpha(alpha);
            setNavigationBarAlpha(alpha);
        }

        /**
         * Apply the specified color tint to the system status bar.
         *
         * @param color The color of the background tint.
         */
        public void setStatusBarTintColor(int color) {
            if (mStatusBarAvailable) {
                mStatusBarTintView.setBackgroundColor(color);
            }
        }

        /**
         * Apply the specified drawable or color resource to the system status bar.
         *
         * @param res The identifier of the resource.
         */
        public void setStatusBarTintResource(int res) {
            if (mStatusBarAvailable) {
                mStatusBarTintView.setBackgroundResource(res);
            }
        }

        /**
         * Apply the specified drawable to the system status bar.
         *
         * @param drawable The drawable to use as the background, or null to remove it.
         */
        @SuppressWarnings("deprecation")
        public void setStatusBarTintDrawable(Drawable drawable) {
            if (mStatusBarAvailable) {
                mStatusBarTintView.setBackgroundDrawable(drawable);
            }
        }

        /**
         * Apply the specified alpha to the system status bar.
         *
         * @param alpha The alpha to use
         */
        @TargetApi(11)
        public void setStatusBarAlpha(float alpha) {
            if (mStatusBarAvailable && Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                mStatusBarTintView.setAlpha(alpha);
            }
        }

        /**
         * Apply the specified color tint to the system navigation bar.
         *
         * @param color The color of the background tint.
         */
        public void setNavigationBarTintColor(int color) {
            if (mNavBarAvailable) {
                mNavBarTintView.setBackgroundColor(color);
            }
        }

        /**
         * Apply the specified drawable or color resource to the system navigation bar.
         *
         * @param res The identifier of the resource.
         */
        public void setNavigationBarTintResource(int res) {
            if (mNavBarAvailable) {
                mNavBarTintView.setBackgroundResource(res);
            }
        }

        /**
         * Apply the specified drawable to the system navigation bar.
         *
         * @param drawable The drawable to use as the background, or null to remove it.
         */
        @SuppressWarnings("deprecation")
        public void setNavigationBarTintDrawable(Drawable drawable) {
            if (mNavBarAvailable) {
                mNavBarTintView.setBackgroundDrawable(drawable);
            }
        }

        /**
         * Apply the specified alpha to the system navigation bar.
         *
         * @param alpha The alpha to use
         */
        @TargetApi(11)
        public void setNavigationBarAlpha(float alpha) {
            if (mNavBarAvailable && Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                mNavBarTintView.setAlpha(alpha);
            }
        }

        /**
         * Get the system bar configuration.
         *
         * @return The system bar configuration for the current device configuration.
         */
        public SystemBarConfig getConfig() {
            return mConfig;
        }

        /**
         * Is tinting enabled for the system status bar?
         *
         * @return True if enabled, False otherwise.
         */
        public boolean isStatusBarTintEnabled() {
            return mStatusBarTintEnabled;
        }

        /**
         * Is tinting enabled for the system navigation bar?
         *
         * @return True if enabled, False otherwise.
         */
        public boolean isNavBarTintEnabled() {
            return mNavBarTintEnabled;
        }

        private void setupStatusBarView(Context context, ViewGroup decorViewGroup) {
            mStatusBarTintView = new View(context);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, mConfig.getStatusBarHeight());
            params.gravity = Gravity.TOP;
            if (mNavBarAvailable && !mConfig.isNavigationAtBottom()) {
                params.rightMargin = mConfig.getNavigationBarWidth();
            }
            mStatusBarTintView.setLayoutParams(params);
            mStatusBarTintView.setBackgroundColor(DEFAULT_TINT_COLOR);
            mStatusBarTintView.setVisibility(View.GONE);
            decorViewGroup.addView(mStatusBarTintView);
        }

        private void setupNavBarView(Context context, ViewGroup decorViewGroup) {
            mNavBarTintView = new View(context);
            FrameLayout.LayoutParams params;
            if (mConfig.isNavigationAtBottom()) {
                params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, mConfig.getNavigationBarHeight());
                params.gravity = Gravity.BOTTOM;
            } else {
                params = new FrameLayout.LayoutParams(mConfig.getNavigationBarWidth(), FrameLayout.LayoutParams.MATCH_PARENT);
                params.gravity = Gravity.RIGHT;
            }
            mNavBarTintView.setLayoutParams(params);
            mNavBarTintView.setBackgroundColor(DEFAULT_TINT_COLOR);
            mNavBarTintView.setVisibility(View.GONE);
            decorViewGroup.addView(mNavBarTintView);
        }

        /**
         * Class which describes system bar sizing and other characteristics for the current
         * device configuration.
         */
        public static class SystemBarConfig {

            private static final String STATUS_BAR_HEIGHT_RES_NAME = "status_bar_height";
            private static final String NAV_BAR_HEIGHT_RES_NAME = "navigation_bar_height";
            private static final String NAV_BAR_HEIGHT_LANDSCAPE_RES_NAME = "navigation_bar_height_landscape";
            private static final String NAV_BAR_WIDTH_RES_NAME = "navigation_bar_width";
            private static final String SHOW_NAV_BAR_RES_NAME = "config_showNavigationBar";

            private final boolean mTranslucentStatusBar;
            private final boolean mTranslucentNavBar;
            private final int mStatusBarHeight;
            private final int mActionBarHeight;
            private final boolean mHasNavigationBar;
            private final int mNavigationBarHeight;
            private final int mNavigationBarWidth;
            private final boolean mInPortrait;
            private final float mSmallestWidthDp;

            private SystemBarConfig(Activity activity, boolean translucentStatusBar, boolean traslucentNavBar) {
                Resources res = activity.getResources();
                mInPortrait = (res.getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT);
                mSmallestWidthDp = getSmallestWidthDp(activity);
                mStatusBarHeight = getInternalDimensionSize(res, STATUS_BAR_HEIGHT_RES_NAME);
                mActionBarHeight = getActionBarHeight(activity);
                mNavigationBarHeight = getNavigationBarHeight(activity);
                mNavigationBarWidth = getNavigationBarWidth(activity);
                mHasNavigationBar = (mNavigationBarHeight > 0);
                mTranslucentStatusBar = translucentStatusBar;
                mTranslucentNavBar = traslucentNavBar;
            }

            @TargetApi(14)
            private int getActionBarHeight(Context context) {
                int result = 0;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                    TypedValue tv = new TypedValue();
                    context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true);
                    result = TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics());
                }
                return result;
            }

            @TargetApi(14)
            private int getNavigationBarHeight(Context context) {
                Resources res = context.getResources();
                int result = 0;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                    if (hasNavBar(context)) {
                        String key;
                        if (mInPortrait) {
                            key = NAV_BAR_HEIGHT_RES_NAME;
                        } else {
                            key = NAV_BAR_HEIGHT_LANDSCAPE_RES_NAME;
                        }
                        return getInternalDimensionSize(res, key);
                    }
                }
                return result;
            }

            @TargetApi(14)
            private int getNavigationBarWidth(Context context) {
                Resources res = context.getResources();
                int result = 0;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                    if (hasNavBar(context)) {
                        return getInternalDimensionSize(res, NAV_BAR_WIDTH_RES_NAME);
                    }
                }
                return result;
            }

            @TargetApi(14)
            private boolean hasNavBar(Context context) {
                Resources res = context.getResources();
                int resourceId = res.getIdentifier(SHOW_NAV_BAR_RES_NAME, "bool", "android");
                if (resourceId != 0) {
                    boolean hasNav = res.getBoolean(resourceId);
                    // check override flag (see static block)
                    if ("1".equals(sNavBarOverride)) {
                        hasNav = false;
                    } else if ("0".equals(sNavBarOverride)) {
                        hasNav = true;
                    }
                    return hasNav;
                } else { // fallback
                    return !ViewConfiguration.get(context).hasPermanentMenuKey();
                }
            }

            private int getInternalDimensionSize(Resources res, String key) {
                int result = 0;
                int resourceId = res.getIdentifier(key, "dimen", "android");
                if (resourceId > 0) {
                    result = res.getDimensionPixelSize(resourceId);
                }
                return result;
            }

            @SuppressLint("NewApi")
            private float getSmallestWidthDp(Activity activity) {
                DisplayMetrics metrics = new DisplayMetrics();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    activity.getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
                } else {
                    // TODO this is not correct, but we don't really care pre-kitkat
                    activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
                }
                float widthDp = metrics.widthPixels / metrics.density;
                float heightDp = metrics.heightPixels / metrics.density;
                return Math.min(widthDp, heightDp);
            }

            /**
             * Should a navigation bar appear at the bottom of the screen in the current
             * device configuration? A navigation bar may appear on the right side of
             * the screen in certain configurations.
             *
             * @return True if navigation should appear at the bottom of the screen, False otherwise.
             */
            public boolean isNavigationAtBottom() {
                return (mSmallestWidthDp >= 600 || mInPortrait);
            }

            /**
             * Get the height of the system status bar.
             *
             * @return The height of the status bar (in pixels).
             */
            public int getStatusBarHeight() {
                return mStatusBarHeight;
            }

            /**
             * Get the height of the action bar.
             *
             * @return The height of the action bar (in pixels).
             */
            public int getActionBarHeight() {
                return mActionBarHeight;
            }

            /**
             * Does this device have a system navigation bar?
             *
             * @return True if this device uses soft key navigation, False otherwise.
             */
            public boolean hasNavigtionBar() {
                return mHasNavigationBar;
            }

            /**
             * Get the height of the system navigation bar.
             *
             * @return The height of the navigation bar (in pixels). If the device does not have
             * soft navigation keys, this will always return 0.
             */
            public int getNavigationBarHeight() {
                return mNavigationBarHeight;
            }

            /**
             * Get the width of the system navigation bar when it is placed vertically on the screen.
             *
             * @return The width of the navigation bar (in pixels). If the device does not have
             * soft navigation keys, this will always return 0.
             */
            public int getNavigationBarWidth() {
                return mNavigationBarWidth;
            }

            /**
             * Get the layout inset for any system UI that appears at the top of the screen.
             *
             * @param withActionBar True to include the height of the action bar, False otherwise.
             * @return The layout inset (in pixels).
             */
            public int getPixelInsetTop(boolean withActionBar) {
                return (mTranslucentStatusBar ? mStatusBarHeight : 0) + (withActionBar ? mActionBarHeight : 0);
            }

            /**
             * Get the layout inset for any system UI that appears at the bottom of the screen.
             *
             * @return The layout inset (in pixels).
             */
            public int getPixelInsetBottom() {
                if (mTranslucentNavBar && isNavigationAtBottom()) {
                    return mNavigationBarHeight;
                } else {
                    return 0;
                }
            }

            /**
             * Get the layout inset for any system UI that appears at the right of the screen.
             *
             * @return The layout inset (in pixels).
             */
            public int getPixelInsetRight() {
                if (mTranslucentNavBar && !isNavigationAtBottom()) {
                    return mNavigationBarWidth;
                } else {
                    return 0;
                }
            }

        }
    }
}
