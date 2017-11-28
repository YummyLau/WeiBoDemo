package yummylau.common.util.util.common;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;

/**
 * 屏幕相关工具类
 * Created by yummyLau on 17-4-30
 * Email: yummyl.lau@gmail.com
 */

public class ScreenUtils {

    private static final String TAG = ScreenUtils.class.getSimpleName();
    private static final String STATUS_BAR_HEIGHT_RES_NAME = "status_bar_height";
    private static final String NAVIGATION_BAR_HEIGHT_RES_NAME = "navigation_bar_height";
    private static final boolean isWidth = true;

    public static int sStatusBarHeight;
    public static boolean sHasNavBar;
    public static int sNavBarWidth;
    public static int sNavBarHeight;
    public static int sWidth;
    public static int sHeight;
    public static int sRawWidth;
    public static int sRawHeight;
    public static int sOrientation;


    /**
     * 获取屏幕横竖屏状态
     *
     * @param context
     * @return
     */
    public static int getOrientation(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        if (displayMetrics.widthPixels > displayMetrics.heightPixels) {
            return Configuration.ORIENTATION_LANDSCAPE;
        } else {
            return Configuration.ORIENTATION_PORTRAIT;
        }
    }


    /**
     * 测量初始化屏幕属性
     *
     * @param context
     */
    public static void measureScreen(Context context) {
        //获取整个屏幕区域，不包括导航栏
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        sWidth = displayMetrics.widthPixels;
        sHeight = displayMetrics.heightPixels;
        if (sWidth > sHeight) {
            sOrientation = Configuration.ORIENTATION_LANDSCAPE;
        } else {
            sOrientation = Configuration.ORIENTATION_PORTRAIT;
        }
        //获取状态栏高度
        sStatusBarHeight = getInternalDimensionSize(context.getResources(), STATUS_BAR_HEIGHT_RES_NAME);
        //获取导航栏高度
        sNavBarHeight = getInternalDimensionSize(context.getResources(), NAVIGATION_BAR_HEIGHT_RES_NAME);
        try {
            final WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            final Display display = windowManager.getDefaultDisplay();
            Point point = new Point();
            //版本判断
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH &&
                    Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
                Display.class.getMethod("getRealSize", Point.class).invoke(display, point);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                display.getRealSize(point);
            }
            sRawWidth = point.x;
            sRawHeight = point.y;
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        if (sOrientation == Configuration.ORIENTATION_LANDSCAPE && sRawWidth < sRawHeight) {
            int temp = sRawWidth;
            sRawWidth = sRawHeight;
            sRawHeight = temp;
        }
        if (sRawWidth <= 0) {
            sRawWidth = sWidth;
        }
        if (sRawHeight <= 0) {
            sRawHeight = sHeight;
        }
//        sNavBarWidth = sRawWidth - sWidth;
//        sNavBarHeight = sRawHeight - sHeight;
        sHasNavBar = sNavBarWidth > 0 || sNavBarHeight > 0;
    }


    /**
     * 是否有导航栏显示
     *
     * @param context
     * @return
     */
    public static boolean isNavigationBarShow(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
            Point size = new Point();
            Point realSize = new Point();
            display.getSize(size);
            display.getRealSize(realSize);
            return realSize.y != size.y;
        } else {
            boolean menu = ViewConfiguration.get(context).hasPermanentMenuKey();
            boolean back = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
            if (menu || back) {
                return false;
            } else {
                return true;
            }

        }
    }


    /**
     * 选择性获取状态栏、导航栏
     *
     * @param res context.getResources()
     * @param key NAVIGATION_BAR_HEIGHT_RES_NAME 或 STATUS_BAR_HEIGHT_RES_NAME
     * @return
     */

    private static int getInternalDimensionSize(Resources res, String key) {
        int result = 0;
        int resourceId = res.getIdentifier(key, "dimen", "android");
        if (resourceId > 0) {
            result = res.getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 获取屏幕可操作宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWOrH(Context context,boolean isWidth) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displayMetrics);
        if(isWidth){
            return displayMetrics.widthPixels;
        }else{
            return displayMetrics.heightPixels;
        }
    }

    /**
     * 获取屏幕真实宽度
     *
     * @param context
     * @return 屏幕真实宽度
     */
    public static int getRealWOrH(Context context,boolean isWidth) {
        try {
            final WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            final Display display = windowManager.getDefaultDisplay();
            Point point = new Point();
            //版本判断
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH &&
                    Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
                Display.class.getMethod("getRealSize", Point.class).invoke(display, point);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                display.getRealSize(point);
            }
            if(isWidth){
                return point.x;
            }else{
                return point.y;
            }
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return 0;
        }
    }

    /**
     * 获取屏幕宽度物理尺寸
     *
     * @param context
     * @return
     */
    public static float getWidthInch(Context context) {
        int realWidth = getRealWOrH(context,isWidth);
        return (float) realWidth / DisplayUtils.getWidthPpi(context);
    }

    /**
     * 获取屏幕高度物理尺寸
     *
     * @param context
     * @return
     */
    public static float getHeightInch(Context context) {
        int realHeight = getRealWOrH(context,!isWidth);
        return (float) realHeight / DisplayUtils.getHeightPpi(context);
    }

    /**
     * 获取屏幕物理尺寸
     *
     * @param context
     * @return 屏幕物理尺寸
     */
    public static float getScreenInch(Context context) {
        return (float) Math.sqrt(Math.pow(getWidthInch(context), 2) + Math.pow(getHeightInch(context), 2));
    }

    /**
     * 获取当前屏幕截图，包含状态栏
     *
     * @return
     */
    public static Bitmap snapShotWithStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int width = getScreenWOrH(activity,isWidth);
        int height = getScreenWOrH(activity,!isWidth);
        Bitmap bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
        view.destroyDrawingCache();
        return bp;
    }

    /**
     * 获取当前屏幕截图，不包含状态栏
     *
     * @return
     */
    public static Bitmap snapShotWithoutStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        Rect frame = new Rect();
        //不包括状态栏
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        int width = getScreenWOrH(activity,isWidth);
        int height = getScreenWOrH(activity,!isWidth);
        Bitmap bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height - statusBarHeight);
        view.destroyDrawingCache();
        return bp;
    }
}
