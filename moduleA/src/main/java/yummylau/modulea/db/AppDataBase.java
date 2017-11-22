package yummylau.modulea.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import yummylau.modulea.bean.User;
import yummylau.modulea.db.converter.Converters;
import yummylau.modulea.db.dao.UserDao;

/**
 * Created by g8931 on 2017/11/22.
 */

@Database(entities = {User.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDataBase extends RoomDatabase {

    public abstract UserDao userDao();
}
