package yummylau.componentservice.di.component;

import javax.inject.Singleton;

import dagger.Component;
import yummylau.common.interfaces.IImageLoader;
import yummylau.componentservice.di.module.AppModule;
import yummylau.componentservice.di.module.SingletonModule;

/**
 * 公用模块，被依赖的时需要显示暴露对象 如IImageLoader IImageLoader()
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2017/12/11.
 */
@Singleton
@Component(modules = {AppModule.class, SingletonModule.class})
public interface BaseAppComponent {

    IImageLoader IImageLoader();
}
