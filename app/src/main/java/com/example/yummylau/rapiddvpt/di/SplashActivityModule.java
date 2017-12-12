package com.example.yummylau.rapiddvpt.di;

import com.example.yummylau.rapiddvpt.SplashActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by g8931 on 2017/12/12.
 */
@Module
public abstract class SplashActivityModule {

    @ContributesAndroidInjector
    abstract SplashActivity splashActivity();
}
