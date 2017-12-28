package yummylau.account.di.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import yummylau.account.LoginActivity;

/**
 * @Module 用于标记提供Activity依赖的类
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2017/12/11.
 */
@Module
public abstract class ActivityBuildersModule {

    @ContributesAndroidInjector
    abstract LoginActivity homeActivity();


//    这种写法和@ContributesAndroidInjector写法是一致的
//    @Subcomponent(modules = FragmentBuildersModule.class)
//    public interface HomeActivitySubcomponent extends AndroidInjector<HomeActivity> {
//        @Subcomponent.Builder
//        public abstract class Builder extends AndroidInjector.Builder<HomeActivity> {}
//    }
}
