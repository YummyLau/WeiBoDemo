package yummylau.feature.di.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import yummylau.feature.view.FollowedFragment;

/**
 * fragment依赖管理
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2017/12/11.
 */
@Module
public abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract FollowedFragment contributeFollowedFragment();
}
