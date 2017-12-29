package yummylau.componentlib.di.component;

import javax.inject.Singleton;

import dagger.Component;
import yummylau.common.interfaces.IImageLoader;
import yummylau.componentlib.di.scope.AppModule;
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
