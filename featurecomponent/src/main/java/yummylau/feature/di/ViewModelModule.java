package yummylau.feature.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import yummylau.common.viewmodel.ViewModelFactory;
import yummylau.feature.videmodel.HomeViewModel;

/**
 * viewmodel依赖管理
 * Created by g8931 on 2017/12/8.
 */

@Module
public abstract class ViewModelModule {

    @Binds
    abstract ViewModelProvider.Factory bindsViewModelFactory(ViewModelFactory movieViewModelFactory);

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel.class)
    abstract ViewModel bindsHomeViewModel(HomeViewModel homeViewModel);
}
