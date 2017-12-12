package yummylau.feature.di.module;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import yummylau.common.net.HttpManager;
import yummylau.common.net.HttpParam;
import yummylau.componentservice.di.SingletonModule;
import yummylau.feature.App;
import yummylau.feature.data.FeatureRepository;
import yummylau.feature.data.local.LocalDataSource;
import yummylau.feature.data.local.db.AppDataBase;
import yummylau.feature.data.local.db.dao.StatusDao;
import yummylau.feature.data.local.db.dao.UserDao;
import yummylau.feature.data.remote.HttpParamCreator;
import yummylau.feature.data.remote.RemoteDataSource;
import yummylau.feature.data.remote.api.WeiboApis;

/**
 * @Provides 标记Module中返回依赖的方法
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2017/12/11.
 */
@Module(includes = {SingletonModule.class, ViewModelModule.class})
public class FeatureModule {


    @Provides
    @Singleton
    FeatureRepository provideFeatureRepository(RemoteDataSource remoteDataSource, LocalDataSource localDataSource) {
        return new FeatureRepository(remoteDataSource, localDataSource);
    }

    @Provides
    @Singleton
    RemoteDataSource provideRemoteDataSource(Application application, AppDataBase db) {
        return new RemoteDataSource(application, db);
    }

    @Provides
    @Singleton
    LocalDataSource provideLocalDataSource(AppDataBase db) {
        return new LocalDataSource(db);
    }

    @Provides
    @Singleton
    AppDataBase provideDB(Application application) {
        return Room.databaseBuilder(application, AppDataBase.class, AppDataBase.DB_FILE_NAME).build();
    }

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

    @Singleton
    @Provides
    UserDao provideUserDao(AppDataBase db) {
        return db.userDao();
    }

    @Singleton
    @Provides
    StatusDao provideStatusDao(AppDataBase db) {
        return db.statusDao();
    }


}
