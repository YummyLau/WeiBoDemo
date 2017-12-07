package com.example.yummylau.rapiddvpt;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;


import com.example.yummylau.rapiddvpt.databinding.AppActivitySplashLayoutBinding;

import yummylau.common.activity.BaseActivity;
import yummylau.componentlib.router.RouterManager;

/**
 * 启动闪屏
 * Created by g8931 on 2017/11/28.
 */

public class SplashActivity extends BaseActivity {

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
