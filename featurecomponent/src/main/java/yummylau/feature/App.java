package yummylau.feature;


import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;

import yummylau.feature.data.local.db.AppDataBase;

/**
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2017/12/11.
 */

public class App extends Application {

    private static AppDataBase sInstance = null;


    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            ARouter.openLog();
            ARouter.openDebug();
        }
    }
}
