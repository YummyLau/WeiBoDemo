package debug;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;

import yummylau.moduleb.BuildConfig;

/**
 * Created by g8931 on 2017/11/14.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            ARouter.openLog();
            ARouter.openDebug();
        }
    }
}
