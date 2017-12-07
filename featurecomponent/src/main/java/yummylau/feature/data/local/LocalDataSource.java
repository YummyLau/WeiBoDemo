package yummylau.feature.data.local;


import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import yummylau.feature.data.FeatureDataSource;
import yummylau.feature.data.local.db.AppDataBase;
import yummylau.feature.data.local.db.entity.StatusEntity;
import yummylau.feature.data.local.db.entity.UserEntity;

/**
 * Created by g8931 on 2017/12/6.
 */

public class LocalDataSource implements FeatureDataSource {

    private static LocalDataSource INSTANCE;
    private AppDataBase mAppDataBase;

    private LocalDataSource(AppDataBase appDataBase) {
        this.mAppDataBase = appDataBase;
    }

    public static LocalDataSource getInstance(AppDataBase appDataBase) {
        if (INSTANCE == null) {
            INSTANCE = new LocalDataSource(appDataBase);
        }
        return INSTANCE;
    }

    @Override
    public Flowable<List<StatusEntity>> getAllStatus() {
        return mAppDataBase.statusDao().getStatus();
    }

    @Override
    public Flowable<List<UserEntity>> getUserInfo() {
        return mAppDataBase.userDao().getUsers();
    }
}
