package yummylau.account.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import yummylau.account.bean.UserTest;
import yummylau.account.db.converter.Converters;
import yummylau.account.db.dao.UserDao;

/**
 * Created by g8931 on 2017/11/22.
 */

@Database(entities = {UserTest.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDataBase extends RoomDatabase {

    public abstract UserDao userDao();
}
