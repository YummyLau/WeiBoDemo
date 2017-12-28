package com.example.yummylau.rapiddvpt.di;

import android.app.Application;

import com.example.yummylau.rapiddvpt.App;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * 顶级注入，用于壳app处理
 * <p>
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2017/12/12.
 */

@Singleton
@Component(
        modules = {
                AndroidSupportInjectionModule.class,
                yummylau.feature.di.module.ActivityBuildersModule.class,
                AndroidInjectionModule.class,
                ActivityBuildersModule.class,
                ViewModelModule.class
        })
public interface Dagger2Component {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        Dagger2Component build();
    }

    void inject(App application);
}
