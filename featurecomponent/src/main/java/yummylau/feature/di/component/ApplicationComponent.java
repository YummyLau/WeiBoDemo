package yummylau.feature.di.component;

import android.app.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import yummylau.feature.di.module.ActivityModule;
import yummylau.feature.di.module.ApplicationModule;

/**
 * @Component 生成Dragger前缀的实现类
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2017/12/11.
 */

@Singleton
@Component(modules = {
        ApplicationModule.class,
        AndroidInjectionModule.class,
        ActivityModule.class})
public interface ApplicationComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        ApplicationComponent build();
    }

    void inject(Application application);
}
