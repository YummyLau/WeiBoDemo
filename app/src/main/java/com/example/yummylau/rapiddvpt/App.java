package com.example.yummylau.rapiddvpt;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.facebook.stetho.Stetho;

import yummylau.common.crash.CrashHandler;
import yummylau.common.net.HttpManager;
import yummylau.componentservice.interfaces.IAccountService;
import yummylau.componentservice.interfaces.IFeatureService;

/**
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2017/12/11.
 */

public class App extends Application {

    @Autowired(name = IAccountService.SERVICE_NAME)
    public static IAccountService accountService;

    @Autowired(name = IFeatureService.SERVICE_NAME)
    public static IFeatureService featureService;

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
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
