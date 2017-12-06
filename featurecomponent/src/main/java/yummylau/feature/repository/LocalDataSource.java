package yummylau.feature.repository;

import rx.Observable;
import yummylau.feature.repository.remote.api.StatusResult;

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
}
