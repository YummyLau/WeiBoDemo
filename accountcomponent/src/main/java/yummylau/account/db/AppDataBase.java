package yummylau.account.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import yummylau.account.bean.UserTest;
import yummylau.account.db.converter.Converters;
import yummylau.account.db.dao.UserDao;

/**
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2017/12/11.
 */

@Database(entities = {UserTest.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDataBase extends RoomDatabase {

    public abstract UserDao userDao();
}
