package yummylau.commonres;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;

/**
 * Created by g8931 on 2017/11/27.
 */

public class ColorGetter {

    @ColorInt
    public static int getStatusBarColor(Context context) {
        return ContextCompat.getColor(context, R.color.colorPrimaryDark);
    }
}
