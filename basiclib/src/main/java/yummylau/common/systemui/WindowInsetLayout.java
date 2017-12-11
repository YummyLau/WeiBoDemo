package yummylau.common.systemui;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.WindowInsetsCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2017/12/11.
 */
public class WindowInsetLayout extends FrameLayout {

    public WindowInsetLayout(Context context) {
        this(context, null);
    }

    public WindowInsetLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WindowInsetLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //监听windowInset
        ViewCompat.setOnApplyWindowInsetsListener(this,
                new android.support.v4.view.OnApplyWindowInsetsListener() {
                    @Override
                    public WindowInsetsCompat onApplyWindowInsets(View v, WindowInsetsCompat insets) {
                        return setWindowInsets(insets);
                    }
                });
    }

    private WindowInsetsCompat setWindowInsets(WindowInsetsCompat insets) {
        if (Build.VERSION.SDK_INT >= 21 && insets.hasSystemWindowInsets()) {
            if (applySystemWindowInsets21(insets)) {
                return insets.consumeSystemWindowInsets();
            }
        }
        return insets;
    }

    @SuppressWarnings("deprecation")
    @Override
    protected boolean fitSystemWindows(Rect insets) {
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            return applySystemWindowInsets19(insets);
        }
        return super.fitSystemWindows(insets);
    }


    @SuppressWarnings("deprecation")
    @TargetApi(19)
    private boolean applySystemWindowInsets19(Rect insets) {
        boolean consumed = false;

        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (!child.getFitsSystemWindows()) {
                continue;
            }

            Rect childInsets = new Rect(insets);
            computeInsetsWithGravity(child, childInsets);

            child.setPadding(childInsets.left, childInsets.top, childInsets.right, childInsets.bottom);

            consumed = true;
        }

        return consumed;
    }

    @TargetApi(21)
    private boolean applySystemWindowInsets21(WindowInsetsCompat insets) {
        boolean consumed = false;

        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);

            if (!child.getFitsSystemWindows()) {
                continue;
            }

            Rect childInsets = new Rect(
                    insets.getSystemWindowInsetLeft(),
                    insets.getSystemWindowInsetTop(),
                    insets.getSystemWindowInsetRight(),
                    insets.getSystemWindowInsetBottom());

            computeInsetsWithGravity(child, childInsets);
            ViewCompat.dispatchApplyWindowInsets(child, insets.replaceSystemWindowInsets(childInsets));

            consumed = true;
        }

        return consumed;
    }

    @SuppressLint("RtlHardcoded")
    private void computeInsetsWithGravity(View view, Rect insets) {
        LayoutParams lp = (LayoutParams) view.getLayoutParams();

        int gravity = lp.gravity;

        /**
         * 因为该方法执行时机早于 FrameLayout.layoutChildren，
         * 而在 {FrameLayout#layoutChildren} 中当 gravity == -1 时会设置默认值为 Gravity.TOP | Gravity.LEFT，
         * 所以这里也要同样设置
         */
        if (gravity == -1) {
            gravity = Gravity.TOP | Gravity.LEFT;
        }

        if (lp.width != LayoutParams.MATCH_PARENT) {
            int horizontalGravity = gravity & Gravity.HORIZONTAL_GRAVITY_MASK;
            switch (horizontalGravity) {
                case Gravity.LEFT:
                    insets.right = 0;
                    break;
                case Gravity.RIGHT:
                    insets.left = 0;
                    break;
            }
        }

        if (lp.height != LayoutParams.MATCH_PARENT) {
            int verticalGravity = gravity & Gravity.VERTICAL_GRAVITY_MASK;
            switch (verticalGravity) {
                case Gravity.TOP:
                    insets.bottom = 0;
                    break;
                case Gravity.BOTTOM:
                    insets.top = 0;
                    break;
            }
        }
    }
}
