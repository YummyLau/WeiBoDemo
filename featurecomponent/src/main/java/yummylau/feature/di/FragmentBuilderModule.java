package yummylau.feature.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import yummylau.feature.view.FollowedFragment;

/**
 * fragment依赖管理
 * Created by g8931 on 2017/12/8.
 */
@Module
public abstract class FragmentBuilderModule {

    @ContributesAndroidInjector
    abstract FollowedFragment contributeFollowedFragment();
}
