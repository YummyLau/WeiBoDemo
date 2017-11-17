package yummylau.moduleb;


import com.alibaba.android.arouter.launcher.ARouter;

import yummylau.common.bus.CommonBizLogin.ModuleBFuns;
import yummylau.common.activity.BaseApplication;
import yummylau.common.bus.FunctionBus;

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

    public static void onCreateAsLibrary() {
        // 子类选择覆盖
        FunctionBus.setFunction(new ModuleBFuns(){
            @Override
            public String getModuleName() {
                return "模块B";
            }
        });
    }
}
