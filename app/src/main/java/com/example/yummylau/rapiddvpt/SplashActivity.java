package com.example.yummylau.rapiddvpt;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;


import com.example.yummylau.rapiddvpt.databinding.AppActivitySplashLayoutBinding;

import yummylau.common.activity.BaseActivityOld;
import yummylau.componentlib.router.RouterManager;

/**
 * 启动闪屏
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2017/12/11.
 */

public class SplashActivity extends BaseActivityOld {

    private AppActivitySplashLayoutBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.app_activity_splash_layout);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                RouterManager.navigation(App.featureService.getMainPath());
                finish();
            }
        }, 2000);
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }
}
