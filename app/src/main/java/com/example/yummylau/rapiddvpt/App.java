package com.example.yummylau.rapiddvpt;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;

import yummylau.common.crash.CrashHandler;

/**
 * Created by g8931 on 2017/11/15.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ARouter.openDebug();
        ARouter.openLog();
        ARouter.printStackTrace();

        ARouter.init(this);

        yummylau.modulea.App.onCreateAsLibrary();
        yummylau.moduleb.App.onCreateAsLibrary();

        //crash收集
        CrashHandler.getInstance().init(this);
    }
}
