package yummylau.componentservice.di;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * 组件appmodule
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2017/12/11.
 */
@Module(includes = SingletonModule.class)
public class AppModule {

    private final Application application;

    public AppModule(Application app) {
        application = app;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return application;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return application;
    }
}
