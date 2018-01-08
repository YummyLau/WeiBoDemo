package com.example.rapiddvpt;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.rapiddvpt.di.DaggerDagger2Component;
import com.facebook.stetho.Stetho;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import yummylau.common.crash.CrashHandler;
import yummylau.common.net.HttpManager;
import yummylau.componentservice.interfaces.IAccountService;
import yummylau.componentservice.interfaces.IFeatureService;

/**
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2017/12/11.
 */

public class App extends Application implements HasActivityInjector {

    @Autowired(name = IAccountService.SERVICE_NAME)
    public static IAccountService accountService;

    @Autowired(name = IFeatureService.SERVICE_NAME)
    public static IFeatureService featureService;

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

        accountService.createAsLibrary(this);
        featureService.createAsLibrary(this);
    }

}
