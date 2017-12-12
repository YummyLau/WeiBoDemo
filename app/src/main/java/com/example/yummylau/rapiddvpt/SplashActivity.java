package com.example.yummylau.rapiddvpt;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;


import com.example.yummylau.rapiddvpt.databinding.AppActivitySplashLayoutBinding;

import yummylau.common.activity.BaseActivity;
import yummylau.componentlib.router.RouterManager;

/**
 * 启动闪屏
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2017/12/11.
 */

public class SplashActivity extends BaseActivity<SplashViewModel, AppActivitySplashLayoutBinding> {

    @Override
    public Class<SplashViewModel> getViewModel() {
        return SplashViewModel.class;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.app_activity_splash_layout;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
