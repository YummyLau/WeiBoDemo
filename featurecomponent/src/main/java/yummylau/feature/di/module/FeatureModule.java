package yummylau.feature.di.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.android.AndroidInjectionModule;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * feature 模块所有modules
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2017/12/11.
 */
@Singleton
@Module(includes = {
        AppModule.class,
        ActivityBuildersModule.class,
        ViewModelModule.class})
public class FeatureModule {
}
