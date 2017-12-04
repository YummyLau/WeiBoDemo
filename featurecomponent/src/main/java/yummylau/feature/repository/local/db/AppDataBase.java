package yummylau.feature.repository.local.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import yummylau.feature.repository.local.db.dao.UserDao;
import yummylau.feature.repository.local.db.entity.UserEntity;

/**
 * Created by g8931 on 2017/12/4.
 */
@Database(entities = {UserEntity.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {

    public abstract UserDao userDao();
}
