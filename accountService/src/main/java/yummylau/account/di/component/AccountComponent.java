package yummylau.account.di.component;

import android.app.Application;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.support.AndroidSupportInjectionModule;
import yummylau.account.di.module.AccountModule;
import yummylau.componentlib.di.component.BaseAppComponent;
import yummylau.componentlib.di.module.ViewModelFactoryModule;
import yummylau.componentlib.di.scope.FeatureScope;

/**
 * account 注入器
 * 依赖{@link BaseAppComponent}
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2017/12/11.
 */

@FeatureScope
@Component(modules = {AccountModule.class,
        ViewModelFactoryModule.class,
        AndroidSupportInjectionModule.class,
        AndroidInjectionModule.class})
public interface AccountComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder context(Application application);

        @BindsInstance
        Builder application(Application application);

        AccountComponent build();
    }

    void inject(Application application);
}
