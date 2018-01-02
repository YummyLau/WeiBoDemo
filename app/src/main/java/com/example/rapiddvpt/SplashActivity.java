package com.example.rapiddvpt;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;


import com.example.rapiddvpt.databinding.AppActivitySplashLayoutBinding;

import io.reactivex.functions.Consumer;
import yummylau.common.activity.BaseActivity;
import yummylau.componentlib.router.RouterManager;
import yummylau.componentservice.bean.Token;

/**
 * 启动闪屏
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2017/12/11.
 */

public class SplashActivity extends BaseActivity<SplashViewModel, AppActivitySplashLayoutBinding> {

    private static final String TAG = SplashActivity.class.getSimpleName();

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
                //检测用户是否登录
                App.accountService.getToken()
                        .subscribe(new Consumer<Token>() {
                                       @Override
                                       public void accept(Token token) throws Exception {
                                           Log.d(TAG, "当前用户已登录，跳转feature组件...");
                                           RouterManager.navigation(App.featureService.getMainPath());
                                       }
                                   },
                                new Consumer<Throwable>() {
                                    @Override
                                    public void accept(Throwable throwable) throws Exception {
                                        Log.d(TAG, "当前用户未登录，跳转account组件...");
                                        RouterManager.navigation(App.accountService.getLoginPath());
                                    }
                                });

            }
        }, 2000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
