package com.example.yummylau.rapiddvpt.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yummylau.rapiddvpt.R;
import com.example.yummylau.rapiddvpt.databinding.WidgetTitlebarLayoutBinding;

/**
 * Created by yummyLau on 17-6-10
 * Email: yummyl.lau@gmail.com
 */

public class TitlebarWidget extends FrameLayout implements View.OnClickListener {

    private static final int TITLE_GRAVITY_CENTER = 0;
    private static final int TITLE_GRAVITY_LEFT = 1;
    private WidgetTitlebarLayoutBinding binding;

    public TitlebarWidget(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * @param context
     * @param attrs
     * @param defStyle
     */
    public TitlebarWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.widget_titlebar_layout, this, true);
        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TitlebarWidget, defStyle, 0);

        // 读取属性
        String title = null;
        boolean back = true;
        int titleGravity = TITLE_GRAVITY_LEFT;
        if (typedArray != null) {
            title = typedArray.getString(R.styleable.TitlebarWidget_activity_title);
            back = typedArray.getBoolean(R.styleable.TitlebarWidget_activity_back, true);
            titleGravity = typedArray.getInteger(R.styleable.TitlebarWidget_activity_title_gravity, TITLE_GRAVITY_LEFT);
            typedArray.recycle();
        }

        if (titleGravity == TITLE_GRAVITY_CENTER) {
            binding.tvTitlebarTitle.setGravity(Gravity.CENTER);
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) binding.tvTitlebarTitle.getLayoutParams();
            layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
            layoutParams.addRule(RelativeLayout.RIGHT_OF, 0);
            layoutParams.leftMargin = 0;
        }

        binding.tvTitlebarTitle.setText(title);
        if (back) {
            binding.llTitlebarBackBtn.setVisibility(View.VISIBLE);
            binding.llTitlebarBackBtn.setOnClickListener(this);
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
        return binding.tvTitlebarTitle;
    }

    public TitlebarWidget setTitle(String title) {
        binding.tvTitlebarTitle.setText(title);
        return this;
    }

    public TitlebarWidget setTitleDrawable(@Nullable Drawable left, @Nullable Drawable top, @Nullable Drawable right, @Nullable Drawable bottom) {
        binding.tvTitlebarTitle.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
        return this;
    }

    public TitlebarWidget setTitle(String title, OnClickListener onClickListener) {
        binding.tvTitlebarTitle.setText(title);
        binding.tvTitlebarTitle.setOnClickListener(onClickListener);
        return this;
    }

    public TitlebarWidget setAction(String actionText, OnClickListener onClickListener) {
        binding.tvTitlebarRight.setText(actionText);
        binding.tvTitlebarRight.setOnClickListener(onClickListener);
        binding.tvTitlebarRight.setVisibility(View.VISIBLE);
        return this;
    }

    public TitlebarWidget setActionEnabled(boolean enabled) {
        binding.tvTitlebarRight.setEnabled(enabled);
        return this;
    }

    public View getBackView() {
        return binding.llTitlebarBackBtn;
    }

    public TextView getActionView() {
        return binding.tvTitlebarRight;
    }

    public TitlebarWidget setAction(String action) {
        binding.tvTitlebarRight.setText(action);
        binding.tvTitlebarRight.setVisibility(VISIBLE);
        return this;
    }
}
