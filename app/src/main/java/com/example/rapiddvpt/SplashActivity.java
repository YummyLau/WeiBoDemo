package com.example.rapiddvpt;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;


import com.example.rapiddvpt.databinding.AppActivitySplashLayoutBinding;

import io.reactivex.functions.Consumer;
import yummylau.common.activity.BaseActivity;
import yummylau.componentlib.component.ComponentManager;
import yummylau.componentlib.router.RouterManager;
import yummylau.componentlib.service.ServiceManager;
import yummylau.componentservice.bean.Token;
import yummylau.componentservice.component.IFeatureComponent;
import yummylau.componentservice.services.IAccountService;

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
                ServiceManager.getService(IAccountService.class)
                        .getToken()
                        .subscribe(new Consumer<Token>() {
                                       @Override
                                       public void accept(Token token) throws Exception {
                                           Log.d(TAG, "当前用户已登录，跳转feature组件...");
                                           RouterManager.navigation(
                                                   ComponentManager.getComponent(IFeatureComponent.class).getMainPath()
                                           );
                                       }
                                   },
                                new Consumer<Throwable>() {
                                    @Override
                                    public void accept(Throwable throwable) throws Exception {
                                        Log.d(TAG, "当前用户未登录，跳转account组件...");
                                        RouterManager.navigation(
                                                ServiceManager.getService(IAccountService.class).getLoginPath()
                                        );
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
