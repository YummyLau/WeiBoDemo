package yummylau.feature.di.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import yummylau.feature.view.HomeActivity;

/**
 * @Module 用于标记提供Activity依赖的类
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2017/12/11.
 */
@Module
public abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(modules = FragmentBuildersModule.class)
    abstract HomeActivity homeActivity();

//    这种写法和@ContributesAndroidInjector写法是一致的
//    @Subcomponent(modules = FragmentBuildersModule.class)
//    public interface HomeActivitySubcomponent extends AndroidInjector<HomeActivity> {
//        @Subcomponent.Builder
//        public abstract class Builder extends AndroidInjector.Builder<HomeActivity> {}
//    }
}
