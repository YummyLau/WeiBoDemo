package yummylau.commonres;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;

/**
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2017/12/11.
 */

public class ColorGetter {

    @ColorInt
    public static int getStatusBarColor(Context context) {
        return ContextCompat.getColor(context, R.color.colorPrimaryDark);
    }
}
