package yummylau.feature.di.component;

import android.app.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.support.AndroidSupportInjectionModule;
import yummylau.feature.di.module.ActivityBuildersModule;
import yummylau.feature.di.module.AppModule;

/**
 * @Component 生成Dragger前缀的实现类
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2017/12/11.
 */

@Singleton
@Component(modules = {
        AppModule.class,
        AndroidSupportInjectionModule.class,
        ActivityBuildersModule.class})
public interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }

    void inject(Application application);
}
