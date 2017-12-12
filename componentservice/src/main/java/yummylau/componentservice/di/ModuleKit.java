package yummylau.componentservice.di;

import android.app.Application;

/**
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2017/12/11.
 */

public class ModuleKit {

    private static ModuleKit instance;
    private BaseAppComponent component;

    public static ModuleKit getInstance() {
        if (instance == null) {
            synchronized (ModuleKit.class) {
                if (instance == null) {
                    instance = new ModuleKit();
//                    Application application = BaseApplication.getInstance();
//                    instance.component = DaggerBaseAppComponent.builder().appModule(new AppModule(application)).build();
                }
            }
        }
        return instance;
    }

    public BaseAppComponent getComponent() {
        return component;
    }
}
