package com.example.yummylau.rapiddvpt;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.OnLifecycleEvent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.util.Log;


import com.example.yummylau.rapiddvpt.databinding.AppActivitySplashLayoutBinding;

import okhttp3.internal.Util;
import rx.Subscription;
import yummylau.common.activity.BaseActivity;
import yummylau.common.rx.RxUtils;
import yummylau.componentlib.router.RouterManager;

/**
 * 启动闪屏
 * Created by g8931 on 2017/11/28.
 */

public class SplashActivity extends BaseActivity {

    private static final String TAG = SplashActivity.class.getSimpleName();
    private AppActivitySplashLayoutBinding mBinding;
    private Subscription gotoMainActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.app_activity_splash_layout);
        gotoMainActivity = RxUtils.postDelayed(2000, new Runnable() {
            @Override
            public void run() {
                RouterManager.navigation(App.featureService.getMainPath());
            }
        });

        gotoMainActivity = RxUtils.postDelayed(5000, new Runnable() {
            @Override
            public void run() {
                mBinding.text.setText("后台更新数据");
                Log.d(TAG, mBinding.text.getText().toString());
            }
        });
        Listener listener = new Listener();
        Lifecycle registry = getLifecycle();
        getLifecycle().getCurrentState();
        registry.addObserver(listener);
    }

    @Override
    protected void onDestroy() {
        if (gotoMainActivity != null && !gotoMainActivity.isUnsubscribed()) {
            gotoMainActivity.unsubscribe();
        }
        super.onDestroy();
    }

    class Listener implements LifecycleObserver {

        @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
        void onCreate() {
            Log.d(TAG, "onCreate!");
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_START)
        void onStart() {
            Log.d(TAG, "onStart!");
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        void onPause() {
            Log.d(TAG, "onPause!");
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
        void onStop() {
            Log.d(TAG, "onStop!");
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        void onDestory() {
            Log.d(TAG, "onDestory!");
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
        void onAny() {
            Log.d(TAG, "ON_ANY!");
        }
    }
}
