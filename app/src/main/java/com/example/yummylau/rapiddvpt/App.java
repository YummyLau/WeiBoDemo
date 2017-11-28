package com.example.yummylau.rapiddvpt;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.facebook.stetho.Stetho;

import yummylau.common.crash.CrashHandler;

/**
 * Created by g8931 on 2017/11/15.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        loadModules();
        ARouter.openDebug();
        ARouter.openLog();
        ARouter.printStackTrace();
        ARouter.init(this);
        Stetho.initializeWithDefaults(this);
        //crash收集
        CrashHandler.getInstance().init(this);
    }

    private void loadModules() {
        if (BuildConfig.BUILD_MODULE_ACCOUNT) {
            yummylau.account.App.onCreateAsLibrary(this);
        }
        if (BuildConfig.BUILD_MODULE_B) {
            yummylau.feature.App.onCreateAsLibrary();
        }
    }
}
