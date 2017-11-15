package com.example.yummylau.rapiddvpt;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;

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
    }
}
