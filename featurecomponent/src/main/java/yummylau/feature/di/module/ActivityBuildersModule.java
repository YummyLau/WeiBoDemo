package yummylau.feature.di.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import yummylau.common.activity.BaseActivity;
import yummylau.common.activity.BaseFragment;
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

}
