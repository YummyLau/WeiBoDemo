package yummylau.feature.di;

import android.app.Application;
import android.arch.persistence.room.Room;

import javax.inject.Singleton;

import dagger.Provides;
import yummylau.feature.data.local.db.AppDataBase;

/**
 * app依赖管理
 * Created by g8931 on 2017/12/8.
 */

public class AppModule {

    @Provides
    @Singleton
    AppDataBase provideAppDatabase(Application application) {
        return Room.databaseBuilder(application, AppDataBase.class, AppDataBase.DB_FILE_NAME).build();
    }
}
