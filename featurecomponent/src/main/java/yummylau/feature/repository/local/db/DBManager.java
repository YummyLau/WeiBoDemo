package yummylau.feature.repository.local.db;

import android.app.Application;
import android.arch.persistence.room.Room;


/**
 * Created by g8931 on 2017/12/4.
 */

public class DBManager {

    private static AppDataBase sInstance;
    private static final String DB_FILE_NAME = "feature_component.db";

    public static void init(Application application) {
        sInstance = Room.databaseBuilder(application, AppDataBase.class, DB_FILE_NAME).build();
    }

    public synchronized static AppDataBase getInstance() {
        return sInstance;
    }
}
