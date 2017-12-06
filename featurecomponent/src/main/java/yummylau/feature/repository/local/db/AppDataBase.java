package yummylau.feature.repository.local.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.transition.Slide;

import yummylau.feature.App;
import yummylau.feature.repository.local.db.dao.UserDao;
import yummylau.feature.repository.local.db.entity.UserEntity;

/**
 * Created by g8931 on 2017/12/4.
 */
@Database(entities = {UserEntity.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {

    public abstract UserDao userDao();

    private static final Object sLock = new Object();
    private static AppDataBase INSTANCE;
    private static final String DB_FILE_NAME = "feature_component.db";

    public static AppDataBase getInstance(Context context) {
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        AppDataBase.class, DB_FILE_NAME)
                        .build();
            }
            return INSTANCE;
        }
    }
}
