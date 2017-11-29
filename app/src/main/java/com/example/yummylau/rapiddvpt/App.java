package com.example.yummylau.rapiddvpt;

import android.app.Application;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.facebook.stetho.Stetho;

import yummylau.common.crash.CrashHandler;
import yummylau.common.net.HttpManager;
import yummylau.componentservice.interfaces.IAccountService;
import yummylau.componentservice.interfaces.IFeatureService;

/**
 * Created by g8931 on 2017/11/15.
 */

public class App extends Application {

    @Autowired(name = IAccountService.SERVICE_NAME)
    public static IAccountService accountService;

    @Autowired(name = IFeatureService.SERVICE_NAME)
    public static IFeatureService featureService;

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
