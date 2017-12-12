package yummylau.feature.di.component;

import android.app.Application;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.support.AndroidSupportInjectionModule;
import yummylau.componentlib.di.AppScope;
import yummylau.componentservice.di.AppComponent;
import yummylau.feature.di.module.ActivityBuildersModule;
import yummylau.feature.di.module.FeatureModule;

/**
 * @Component 生成Dragger前缀的实现类
 * featurecomponent组件的全局注入器
 * <p>
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2017/12/11.
 */
@AppScope
@Component(
//        dependencies = BaseAppComponent.class,
        modules = {FeatureModule.class,
                AndroidSupportInjectionModule.class,
                AndroidInjectionModule.class,
                ActivityBuildersModule.class})
public interface FeatureComponent extends AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder context(Application application);

        @BindsInstance
        Builder application(Application application);

        FeatureComponent build();
    }

    void inject(Application application);
}
