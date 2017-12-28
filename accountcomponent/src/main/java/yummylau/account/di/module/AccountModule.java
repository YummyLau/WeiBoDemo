package yummylau.account.di.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import yummylau.account.data.remote.WeiboApis;
import yummylau.common.net.HttpManager;
import yummylau.common.net.HttpParam;

/**
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2017/12/28.
 */
@Module
public class AccountModule {

    @Singleton
    @Provides
    WeiboApis provideWeiboService() {
        HttpParam httpParam = new HttpParam.Builder()
                .baseUrl(WeiboApis.BASE_URL)
                .callAdatperFactory(RxJava2CallAdapterFactory.create())
                .converterFactory(GsonConverterFactory.create())
                .build();
        return HttpManager.create(httpParam, WeiboApis.class);
    }

}
