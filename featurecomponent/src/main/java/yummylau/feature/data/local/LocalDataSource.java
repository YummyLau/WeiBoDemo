package yummylau.feature.data.local;

import rx.Observable;
import yummylau.feature.data.FeatureDataSource;
import yummylau.feature.data.local.db.entity.UserEntity;
import yummylau.feature.data.remote.result.StatusResult;

/**
 * Created by g8931 on 2017/12/6.
 */

public class LocalDataSource implements FeatureDataSource {

    private static LocalDataSource INSTANCE;

    private LocalDataSource() {
    }

    public static LocalDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LocalDataSource();
        }
        return INSTANCE;
    }

    @Override
    public Observable<StatusResult> getAllStatus() {
        return null;
    }

    @Override
    public Observable<UserEntity> getUserInfo() {
        return null;
    }
}
