package yummylau.componentservice.di.module;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import yummylau.common.interfaces.IImageLoader;
import yummylau.common.util.imageloader.ImageLoader;

/**
 * 底层提供的单例处理
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2017/12/11.
 */
@Module
public class SingletonModule {

    @Provides
    @Singleton
    IImageLoader providerImageLoader() {
        return ImageLoader.getInstance();
    }

}
