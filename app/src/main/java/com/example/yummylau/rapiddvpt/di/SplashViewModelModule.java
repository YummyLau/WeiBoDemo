package com.example.yummylau.rapiddvpt.di;

import android.arch.lifecycle.ViewModel;

import com.example.yummylau.rapiddvpt.SplashViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import yummylau.feature.di.ViewModelKey;

/**
 * Created by g8931 on 2017/12/12.
 */
@Module
public abstract class SplashViewModelModule {


    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel.class)
    abstract ViewModel bindsSplashViewModel(SplashViewModel splashViewModel);
}
