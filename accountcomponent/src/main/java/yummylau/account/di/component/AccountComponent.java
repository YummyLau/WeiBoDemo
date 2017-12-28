package yummylau.account.di.component;

import android.app.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.support.AndroidSupportInjectionModule;
import yummylau.account.AccountServiceImpl;
import yummylau.account.di.module.AccountModule;
import yummylau.account.di.module.ActivityBuildersModule;
import yummylau.componentservice.di.component.BaseAppComponent;

/**
 * account 注入器
 * 依赖{@link BaseAppComponent}
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2017/12/11.
 */

@Singleton
@Component(modules = {AccountModule.class,
        AndroidSupportInjectionModule.class,
        AndroidInjectionModule.class,
        ActivityBuildersModule.class})
public interface AccountComponent{

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder context(Application application);

        AccountComponent build();
    }

    void inject(AccountServiceImpl accountService);
}
