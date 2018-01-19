package com.example.rapiddvpt;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.rapiddvpt.di.DaggerDagger2Component;
import com.facebook.stetho.Stetho;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import yummylau.account.AccountServiceImpl;
import yummylau.account.di.component.AccountComponent;
import yummylau.common.crash.CrashHandler;
import yummylau.common.net.HttpManager;
import yummylau.componentlib.component.ComponentManager;
import yummylau.componentlib.service.ServiceManager;
import yummylau.componentservice.services.IAccountService;
import yummylau.feature.FeatureComponentImpl;

/**
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2017/12/11.
 */

public class App extends Application implements HasActivityInjector {


    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingInjector;

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityDispatchingInjector;
    }

    @Override
    public void onCreate() {
        super.onCreate();


        DaggerDagger2Component.builder()
                .context(this)
                .application(this)
                .build().inject(this);


        ARouter.openDebug();
        ARouter.openLog();
        ARouter.printStackTrace();
        ARouter.init(this);
        Stetho.initializeWithDefaults(this);
        //crash收集
        CrashHandler.getInstance().init(this);
        HttpManager.init(this);
        //初始化库
        ARouter.getInstance().inject(this);

        //初始化基础服务
        ServiceManager.register(this, AccountServiceImpl.class);

        //初始化组件
        ComponentManager.bind(this, FeatureComponentImpl.class);
    }

}
