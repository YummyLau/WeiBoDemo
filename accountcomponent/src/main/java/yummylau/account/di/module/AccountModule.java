package yummylau.account.di.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.android.AndroidInjectionModule;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * 暴露所有account模块的modules
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2017/12/11.
 */
@Singleton
@Module(includes = {AppModule.class,
        ActivityBuildersModule.class,
//        AndroidSupportInjectionModule.class,
//        AndroidInjectionModule.class
})
public class AccountModule {
}
