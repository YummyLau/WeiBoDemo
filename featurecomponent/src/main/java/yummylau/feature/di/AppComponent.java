package yummylau.feature.di;

import android.app.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

/**
 * Created by g8931 on 2017/12/8.
 */

@Singleton
@Component(modules = {
        AppModule.class,
        AndroidInjectionModule.class,
        ActivityBuilderModule.class})
public class AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}
