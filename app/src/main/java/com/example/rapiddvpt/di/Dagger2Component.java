package com.example.rapiddvpt.di;

import android.app.Application;
import android.content.Context;

import com.example.rapiddvpt.App;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;
import yummylau.account.di.module.AccountModule;
import yummylau.componentlib.di.module.ViewModelFactoryModule;
import yummylau.feature.di.module.FeatureModule;

/**
 * 顶级注入，用于壳app处理
 * <p>
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2017/12/12.
 */

@Singleton
@Component(
        modules = {
                ViewModelFactoryModule.class,

                FeatureModule.class,
                AccountModule.class,

                AndroidSupportInjectionModule.class,
                ActivityBuildersModule.class,

                ViewModelModule.class
        })
public interface Dagger2Component {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder context(Context context);

        @BindsInstance
        Builder application(Application application);

        Dagger2Component build();
    }

    void inject(App application);
}
