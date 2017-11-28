package yummylau.commonres;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;

/**
 * 支持
 * 导航栏图片
 * App logo
 * 标题和子标题
 * 自定义空间
 * ActionMenu
 * Created by g8931 on 2017/11/27.
 */

public class CustomToolbar extends Toolbar {

    public CustomToolbar(Context context) {
        super(context);
    }

    public CustomToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
