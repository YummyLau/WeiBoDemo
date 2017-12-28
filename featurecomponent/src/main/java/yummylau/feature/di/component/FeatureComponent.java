package yummylau.feature.di.component;

import android.app.Application;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.support.AndroidSupportInjectionModule;
import yummylau.componentlib.di.AppScope;
import yummylau.componentservice.di.component.BaseAppComponent;
import yummylau.feature.FeatureServiceImpl;
import yummylau.feature.di.module.ActivityBuildersModule;
import yummylau.feature.di.module.FeatureModule;
import yummylau.feature.di.module.ViewModelModule;

/**
 * featurecomponent组件的全局注入器
 * 依赖{@link BaseAppComponent}
 * <p>
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2017/12/11.
 */
@AppScope
@Component(modules = {FeatureModule.class,
        AndroidSupportInjectionModule.class,
        AndroidInjectionModule.class,
        ActivityBuildersModule.class,
        ViewModelModule.class})
public interface FeatureComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder context(Application application);

//        @BindsInstance
//        Builder application(Application application);

        FeatureComponent build();
    }

    void inject(FeatureServiceImpl featureService);
}
