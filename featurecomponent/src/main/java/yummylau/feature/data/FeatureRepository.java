package yummylau.feature.data;


import android.support.annotation.NonNull;

import rx.Observable;
import yummylau.feature.data.local.db.entity.UserEntity;
import yummylau.feature.data.remote.result.StatusResult;

/**
 * Created by g8931 on 2017/12/5.
 */

public class FeatureRepository implements FeatureDataSource {

    private volatile static FeatureRepository INSTANCE = null;

    private final FeatureDataSource mRemoteDataSource;
    private final FeatureDataSource mLocalDataSource;

    private FeatureRepository(@NonNull FeatureDataSource remoteDataSource,
                              @NonNull FeatureDataSource localDataSource) {
        mRemoteDataSource = checkNotNull(remoteDataSource);
        mLocalDataSource = checkNotNull(localDataSource);
    }

    public static FeatureRepository getInstance(FeatureDataSource remoteDataSource,
                                                FeatureDataSource localDataSource) {
        if (INSTANCE == null) {
            synchronized (FeatureRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FeatureRepository(remoteDataSource, localDataSource);
                }
            }
        }
        return INSTANCE;
    }

    private static <T> T checkNotNull(T reference) {
        if (reference == null) {
            throw new NullPointerException();
        }
        return reference;
    }


    @Override
    public Observable<StatusResult> getAllStatus() {
        return mRemoteDataSource.getAllStatus();
    }

    @Override
    public Observable<UserEntity> getUserInfo() {
        return mRemoteDataSource.getUserInfo();
    }
}
