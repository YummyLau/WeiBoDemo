package yummylau.feature.di.component;

import android.app.Application;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.support.AndroidSupportInjectionModule;
import yummylau.componentlib.di.component.BaseAppComponent;
import yummylau.componentlib.di.module.ViewModelFactoryModule;
import yummylau.componentlib.di.scope.FeatureScope;
import yummylau.feature.di.module.FeatureModule;

/**
 * featurecomponent组件的全局注入器
 * 依赖{@link BaseAppComponent}
 * <p>
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2017/12/11.
 */
@FeatureScope
@Component(modules = {
        FeatureModule.class,
        ViewModelFactoryModule.class,
        AndroidSupportInjectionModule.class,
        AndroidInjectionModule.class}
        )
public interface FeatureComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder context(Application application);

        @BindsInstance
        Builder application(Application application);

        FeatureComponent build();
    }

    //        void inject(FeatureConponentImpl featureService);
    void inject(Application application);
}
