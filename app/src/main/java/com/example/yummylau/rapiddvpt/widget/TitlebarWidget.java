package com.example.yummylau.rapiddvpt.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yummylau.rapiddvpt.R;
import com.example.yummylau.rapiddvpt.databinding.WidgetCommonTitlebarBinding;

/**
 * Created by yummyLau on 17-6-10
 * Email: yummyl.lau@gmail.com
 */

public class CommonTitlebar extends FrameLayout implements View.OnClickListener {

    private static final int TITLE_GRAVITY_CENTER = 0;
    private static final int TITLE_GRAVITY_LEFT = 1;
    private WidgetCommonTitlebarBinding binding;

    public CommonTitlebar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * @param context
     * @param attrs
     * @param defStyle
     */
    public CommonTitlebar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.widget_common_titlebar, this, true);
        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TitleBar, defStyle, 0);

        // 读取属性
        String title = null;
        boolean back = true;
        int titleGravity = TITLE_GRAVITY_LEFT;
        if (typedArray != null) {
            title = typedArray.getString(R.styleable.TitleBar_activity_title);
            back = typedArray.getBoolean(R.styleable.TitleBar_activity_back, true);
            titleGravity = typedArray.getInteger(R.styleable.TitleBar_activity_title_gravity, TITLE_GRAVITY_LEFT);
            typedArray.recycle();
        }

        if (titleGravity == TITLE_GRAVITY_CENTER) {
            mBinding.tvTitlebarTitle.setGravity(Gravity.CENTER);
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mBinding.tvTitlebarTitle.getLayoutParams();
            layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
            layoutParams.addRule(RelativeLayout.RIGHT_OF, 0);
            layoutParams.leftMargin = 0;
        }

        mBinding.tvTitlebarTitle.setText(title);
        if (back) {
            mBinding.llTitlebarBackBtn.setVisibility(View.VISIBLE);
            mBinding.llTitlebarBackBtn.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_titlebar_back_btn:
                finishActivity();
                break;
        }
    }

    public void finishActivity() {
        final Activity activity = (Activity) getContext();
        activity.finish();
    }

    public TextView getTitleView() {
        return mBinding.tvTitlebarTitle;
    }

    public TitleBar setTitle(String title) {
        mBinding.tvTitlebarTitle.setText(title);
        return this;
    }

    public TitleBar setTitleDrawable(@Nullable Drawable left, @Nullable Drawable top, @Nullable Drawable right, @Nullable Drawable bottom) {
        mBinding.tvTitlebarTitle.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
        return this;
    }

    public TitleBar setTitle(String title, OnClickListener onClickListener) {
        mBinding.tvTitlebarTitle.setText(title);
        mBinding.tvTitlebarTitle.setOnClickListener(onClickListener);
        return this;
    }

    public TitleBar setAction(String actionText, OnClickListener onClickListener) {
        mBinding.tvTitlebarRight.setText(actionText);
        mBinding.tvTitlebarRight.setOnClickListener(onClickListener);
        mBinding.tvTitlebarRight.setVisibility(View.VISIBLE);
        return this;
    }

    public TitleBar setActionEnabled(boolean enabled) {
        mBinding.tvTitlebarRight.setEnabled(enabled);
        return this;
    }

    public View getBackView() {
        return mBinding.llTitlebarBackBtn;
    }

    public TextView getActionView() {
        return mBinding.tvTitlebarRight;
    }

    public TitleBar setAction(String action) {
        mBinding.tvTitlebarRight.setText(action);
        mBinding.tvTitlebarRight.setVisibility(VISIBLE);
        return this;
    }
}
}
