package yummylau.feature.di.module;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import yummylau.componentlib.di.ViewModelKey;
import yummylau.feature.videmodel.FollowedViewModel;
import yummylau.feature.videmodel.HomeViewModel;

/**
 * viewmodel依赖管理
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2017/12/11.
 */

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel.class)
    abstract ViewModel bindsHomeViewModel(HomeViewModel homeViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(FollowedViewModel.class)
    abstract ViewModel bindsFollowedViewModel(FollowedViewModel homeViewModel);
}
