package com.example.yummylau.rapiddvpt.util.common;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by yummyLau on 17-4-30
 * Email: yummyl.lau@gmail.com
 */

public class ScreenUtils {

    private static final String STATUS_BAR_HEIGHT_RES_NAME = "status_bar_height";
    private static final String NAVIGATION_BAR_HEIGHT_RES_NAME = "navigation_bar_height";

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
     * 重新测量屏幕
     *
     * @param context
     */
    public static void tryReinit(Context context) {
        if (getOrientation(context) != sOrientation) {
            init(context);
        }
    }

    /**
     * 测量初始化屏幕属性
     *
     * @param context
     */
    public static void init(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        sWidth = displayMetrics.widthPixels;
        sHeight = displayMetrics.heightPixels;
        if (sWidth > sHeight) {
            sOrientation = Configuration.ORIENTATION_LANDSCAPE;
        } else {
            sOrientation = Configuration.ORIENTATION_PORTRAIT;
        }
        sStatusBarHeight = getInternalDimensionSize(context.getResources(), STATUS_BAR_HEIGHT_RES_NAME);
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
            e.printStackTrace();
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
        sNavBarWidth = sRawWidth - sWidth;
        sNavBarHeight = sRawHeight - sHeight;
        sHasNavBar = sNavBarWidth > 0 || sNavBarHeight > 0;
    }

    /**
     * 选择性获取状态栏、导航栏告诉
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
    public static int getScreenWidth(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    /**
     * 获取屏幕可操作高度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    /**
     * 获取屏幕可操作区域宽度和高度
     *
     * @param context
     * @return 宽度和高度封装到Point对象中
     */
    public static Point getScreenSize(Context context) {
        return new Point(getScreenWidth(context), getScreenHeight(context));
    }

    /**
     * 获取屏幕真实宽度
     *
     * @param context
     * @return 屏幕真实宽度
     */
    public static int getRealWidth(Context context) {
        int widthPixels = 0;
        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        final int VERSION = Build.VERSION.SDK_INT;
        if (VERSION < 13) {
            display.getWidth();
        } else if (VERSION == 13) {
            try {
                widthPixels = (Integer) Display.class.getMethod("getRawWidth").invoke(display);
            } catch (Exception e) {
            }
        } else {
            Point realSize = new Point();
            try {
                Display.class.getMethod("getRealSize", Point.class).invoke(display, realSize);
                widthPixels = realSize.x;
            } catch (Exception e) {
            }
        }
        return widthPixels;
    }

    /**
     * 获取屏幕真实高度
     *
     * @param context
     * @return 屏幕真实高度
     */
    public static int getRealHeight(Context context) {
        int heightPixels = 0;
        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        final int VERSION = Build.VERSION.SDK_INT;

        if (VERSION < 13) {
            display.getHeight();
        } else if (VERSION == 13) {
            try {
                heightPixels = (Integer) Display.class.getMethod("getRawHeight").invoke(display);
            } catch (Exception e) {
            }
        } else {
            Point realSize = new Point();
            try {
                Display.class.getMethod("getRealSize", Point.class).invoke(display, realSize);
                heightPixels = realSize.y;
            } catch (Exception e) {
            }
        }
        return heightPixels;
    }

    /**
     * 获取屏幕真实宽度和高度
     *
     * @param context
     * @return 宽度和高度封装到Point对象中
     */
    public static Point getRealSize(Context context) {
        return new Point(getRealWidth(context), getRealHeight(context));
    }

    /**
     * 获取屏幕宽度物理尺寸
     *
     * @param context
     * @return
     */
    public static float getWidthInch(Context context) {
        int realWidth = getRealWidth(context);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displayMetrics);
        return (float) realWidth / DisplayUtils.getWidthPpi(context);
    }

    /**
     * 获取屏幕高度物理尺寸
     *
     * @param context
     * @return
     */
    public static float getHeightInch(Context context) {
        int realHeight = getRealHeight(context);
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
        int width = getScreenWidth(activity);
        int height = getScreenHeight(activity);
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
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        int width = getScreenWidth(activity);
        int height = getScreenHeight(activity);
        Bitmap bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height - statusBarHeight);
        view.destroyDrawingCache();
        return bp;
    }
}
