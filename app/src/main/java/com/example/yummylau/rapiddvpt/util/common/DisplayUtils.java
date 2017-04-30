package com.example.yummylau.rapiddvpt.util.common;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * 像素转化
 * Created by yummyLau on 17-4-24
 * Email: yummyl.lau@gmail.com
 */

public class DisplayUtils {

//    private static int SCREEN_WIDTH;
//    private static int SCREEN_HEIGHT;

    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     *
     * @param pxValue
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param dipValue
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

//    public static class ScreenSize {
//        public int width;
//        public int height;
//    }
//
//    public static ScreenSize getScreenSize(Context context) {
//        ScreenSize size = new DisplayUtils.ScreenSize();
//        DisplayMetrics metric = new DisplayMetrics();
//        if (context instanceof Activity) {
//            Activity activity = (Activity) context;
//            activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
//            size.width = metric.widthPixels;
//            size.height = metric.heightPixels;
//        }
//        return size;
//    }
//
//    public static ScreenSize getScreenRealSize(Context context) {
//        ScreenSize size = new DisplayUtils.ScreenSize();
//
//        int heightPixels;
//        int widthPixels;
//        WindowManager w = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//        Display d = w.getDefaultDisplay();
//        DisplayMetrics metrics = new DisplayMetrics();
//        d.getMetrics(metrics);
//        // since SDK_INT = 1;
//        heightPixels = metrics.heightPixels;
//        widthPixels = metrics.widthPixels;
//        // includes window decorations (statusbar bar/navigation bar)
//        if (Build.VERSION.SDK_INT >= 14 && Build.VERSION.SDK_INT < 17)
//            try {
//                heightPixels = (Integer) Display.class
//                        .getMethod("getRawHeight").invoke(d);
//            } catch (Exception ignored) {
//            }
//            // includes window decorations (statusbar bar/navigation bar)
//        else if (Build.VERSION.SDK_INT >= 17)
//            try {
//                android.graphics.Point realSize = new android.graphics.Point();
//                Display.class.getMethod("getRealSize",
//                        android.graphics.Point.class).invoke(d, realSize);
//                heightPixels = realSize.y;
//            } catch (Exception ignored) {
//            }
//
//        size.width = widthPixels;
//        size.height = heightPixels;
//        return size;
//    }
//
//    /**
//     * 压缩图片
//     *
//     * @param path
//     */
//    public static Bitmap dealimage(Context context, Uri path) {
//        try {
//            BitmapFactory.Options options = new BitmapFactory.Options();
//            options.inJustDecodeBounds = true;
//            Bitmap bitmap = BitmapFactory.decodeStream(context
//                    .getContentResolver().openInputStream(path), null, options);
//            options.inJustDecodeBounds = false;
//            int be1 = (int) (options.outHeight / (float) 1024);
//            int be2 = (int) (options.outWidth / (float) 1024);
//
//            int be = be1 > be2 ? be1 : be2;
//            if (be <= 1) {
//                be = 1;
//            }
//            options.inSampleSize = be;
//            bitmap = BitmapFactory.decodeStream(context.getContentResolver()
//                    .openInputStream(path), null, options);
//            return bitmap;
//        } catch (Exception e) {
//            return null;
//        }
//    }
//
//    public static int getScreenWidth(Context context) {
//        if (SCREEN_WIDTH == 0) {
//            SCREEN_WIDTH = context.getResources().getDisplayMetrics().widthPixels;
//        }
//        return SCREEN_WIDTH;
//    }
//
//    public static int getScreenHeight(Context context) {
//        if (SCREEN_HEIGHT == 0) {
//            SCREEN_HEIGHT = context.getResources().getDisplayMetrics().heightPixels;
//        }
//        return SCREEN_HEIGHT;
//    }

    /**
     * 获取屏幕宽度ppi
     *
     * @param context
     * @return 屏幕宽度ppi
     */
    public static float getWidthPpi(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.xdpi;
    }

    /**
     * 获取屏幕高度ppi
     *
     * @param context
     * @return 屏幕高度ppi
     */
    public static float getHeightPpi(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.ydpi;
    }

    /**
     * 获取屏幕ppi
     *
     * @param context
     * @return 屏幕高度ppi
     */
    public static float getScreenPpi(Context context) {
        return (getWidthPpi(context) + getHeightPpi(context)) / 2;
    }

    /**
     * 获取屏幕密度 （像素比例：0.75/1.0/1.5/2.0）
     *
     * @param context
     * @return
     */
    public static float getScreenDensity(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.density;
    }

    /**
     * 获取每英寸的点数(打印分辨率,区别PPI)（每寸像素：120/160/240/320）
     *
     * @param context
     * @return
     */
    public static float getDensityDpi(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.densityDpi;
    }


}
