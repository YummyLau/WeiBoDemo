package yummylau.feature.di.module;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import javax.inject.Inject;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import yummylau.common.viewmodel.ViewModelFactory;
import yummylau.feature.di.ViewModelKey;
import yummylau.feature.videmodel.FollowedViewModel;
import yummylau.feature.videmodel.HomeViewModel;

/**
 * viewmodel依赖管理
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2017/12/11.
 */

@Module
public abstract class ViewModelModule {

    /**
     * 该注解告诉我们，ViewModelProvider.Factory 使用ViewModelFactory来实现的
     *
     * @param factory
     * @return
     */
    @Binds
    abstract ViewModelProvider.Factory bindsViewModelFactory(ViewModelFactory factory);

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel.class)
    abstract ViewModel bindsHomeViewModel(HomeViewModel homeViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(FollowedViewModel.class)
    abstract ViewModel bindsFollowedViewModel(FollowedViewModel homeViewModel);
}
