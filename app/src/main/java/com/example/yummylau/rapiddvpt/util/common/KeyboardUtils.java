package com.example.yummylau.rapiddvpt.util.common;

import android.content.Context;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * 软键盘工具类
 * Created by yummyLau on 17-4-30
 * Email: yummyl.lau@gmail.com
 */

public class KeyboardUtils {

    private final static String TAG = KeyboardUtils.class.getSimpleName();


    /**
     * 隐藏软键盘
     *
     * @param context
     * @param editText
     */
    public static void hideSoftInputFromWindow(Context context, EditText editText) {
        try {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(editText.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }


    /**
     * 显示软键盘
     *
     * @param context
     * @param editText
     */
    public static void showSoftInputWindow(Context context, EditText editText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED);
    }

    /**
     * 显示软键盘
     *
     * @param context
     * @param editText
     * @param flags
     */
    public static void showSoftInputWindow(Context context, EditText editText, int flags) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, flags);
    }
}
