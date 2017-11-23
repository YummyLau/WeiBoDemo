package yummylau.modulea;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import yummylau.modulea.bean.User;
import yummylau.modulea.db.converter.Converters;

/**
 * Created by g8931 on 2017/11/22.
 */

@Database(entities = {User.class}, version = 2)
@TypeConverters({Converters.class})
public abstract class TestDataBase extends RoomDatabase {

    public abstract TestDao userDao();
}
