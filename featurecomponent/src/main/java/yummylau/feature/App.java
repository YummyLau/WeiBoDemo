package yummylau.feature;


import com.alibaba.android.arouter.launcher.ARouter;

import yummylau.common.activity.BaseApplication;

/**
 * Created by g8931 on 2017/11/14.
 */

public class App extends BaseApplication {


    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            ARouter.openLog();
            ARouter.openDebug();
        }
    }
}
