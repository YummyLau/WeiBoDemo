package yummylau.feature.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import yummylau.feature.view.FollowedFragment;

/**
 * fragment依赖管理
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2017/12/11.
 */
@Module
public abstract class FragmentBuilderModule {

    @ContributesAndroidInjector
    abstract FollowedFragment contributeFollowedFragment();
}
