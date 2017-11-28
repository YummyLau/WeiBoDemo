package yummylau.common.util;

import android.content.Context;
import android.widget.Toast;

import yummylau.common.view.GSToastBottom;


/**
 */
public class ToastUtils {

    /**
     * LENGTH_SHORT == 2 second
     * LENGTH_LONG == 3 second
     */
    //whether show
    public static boolean isShow = true;

    private ToastUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * show a Toast which fades out quickly
     *
     * @param context context
     * @param message the content to show
     */
    public static void showShort(Context context, CharSequence message) {
        if (null == context) return;
        if (isShow) {
            new GSToastBottom(context, message.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * show a Toast which fades out quickly
     *
     * @param context context
     * @param message the content to show
     */
    public static void showShort(Context context, String message) {
        if (null == context) return;
        if (isShow) {
            new GSToastBottom(context, message, Toast.LENGTH_SHORT).show();
        }
    }

    public static void showShort(Context context, int resId) {
        if (null == context) return;
        if (isShow) {
            if (resId > 0) {
                new GSToastBottom(context, resId, Toast.LENGTH_SHORT).show();
            }

        }
    }

    /**
     * show a Toast which stays for long time
     *
     * @param context context
     * @param message the content to show
     */
    public static void showLong(Context context, CharSequence message) {
        if (null == context) return;
        if (isShow) {
            new GSToastBottom(context, message.toString(), Toast.LENGTH_LONG).show();
        }
    }

    /**
     * show a Toast which stays for long time
     *
     * @param context context
     * @param message the content to show
     */
    public static void showLong(Context context, String message) {
        if (null == context) return;
        if (isShow) {
            new GSToastBottom(context, message, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * show a Toast whose lifetime depends on you
     *
     * @param context  context
     * @param message  the content to show
     * @param duration how long to stay
     */
    public static void showCustom(Context context, CharSequence message, int duration) {
        if (null == context) return;
        if (isShow) {
            new GSToastBottom(context, message.toString(), duration).show();
        }
    }

    /**
     * show a Toast whose lifetime depends on you
     *
     * @param context  context
     * @param message  the content to show
     * @param duration how long to stay
     */
    public static void showCustom(Context context, String message, int duration) {
        if (null == context) return;
        if (isShow) {
            new GSToastBottom(context, message, duration).show();
        }
    }

}
