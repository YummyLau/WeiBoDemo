package yummylau.feature.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import yummylau.feature.view.HomeActivity;

/**
 * activity依赖管理
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2017/12/11.
 */
@Module
public abstract class ActivityBuilderModule {

    @ContributesAndroidInjector(modules = FragmentBuilderModule.class)
    abstract HomeActivity homeActivity();
}
