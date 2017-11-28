package yummylau.account.db;

import android.app.Application;
import android.arch.persistence.room.Room;
/**
 * 数据库助手
 * Created by g8931 on 2017/11/23.
 */

public class DBManager {

    private static AppDataBase sInstance;

    public static void init(Application application) {
        sInstance = Room.databaseBuilder(application, AppDataBase.class, "myDb.db").build();
    }

    public synchronized static AppDataBase getInstance() {
        return sInstance;
    }
}
