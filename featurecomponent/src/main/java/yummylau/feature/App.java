package yummylau.feature;


import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;

import yummylau.common.activity.BaseApplication;
import yummylau.componentservice.interfaces.IAccountService;
import yummylau.componentservice.interfaces.IFeatureService;

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
