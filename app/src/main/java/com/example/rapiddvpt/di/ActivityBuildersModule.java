package com.example.rapiddvpt.di;

import com.example.rapiddvpt.SplashActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by g8931 on 2017/12/12.
 */
@Module
public abstract class ActivityBuildersModule {

    @ContributesAndroidInjector
    abstract SplashActivity splashActivity();
}
