package yummylau.feature.data;


import android.support.annotation.NonNull;
import android.util.Log;

import org.reactivestreams.Publisher;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import yummylau.feature.data.local.db.entity.StatusEntity;
import yummylau.feature.data.local.db.entity.UserEntity;

/**
 * Created by g8931 on 2017/12/5.
 */

public class FeatureRepository implements FeatureDataSource {

    private static final String TAG = FeatureRepository.class.getSimpleName();

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
    public Flowable<List<StatusEntity>> getAllStatus() {
        return mLocalDataSource.getAllStatus()
                .flatMap(new Function<List<StatusEntity>, Publisher<List<StatusEntity>>>() {
                    @Override
                    public Publisher<List<StatusEntity>> apply(List<StatusEntity> statusEntities) throws Exception {
                        if (statusEntities == null || statusEntities.isEmpty()) {
                            Log.d(TAG, "#getAllStatus()  ->  get data by remote");
                            return mRemoteDataSource.getAllStatus();
                        }
                        Log.d(TAG, "#getAllStatus()  ->  get data by local");
                        return Flowable.just(statusEntities);
                    }
                });
    }

    @Override
    public Flowable<List<UserEntity>> getUserInfo() {
        return mLocalDataSource.getUserInfo()
                .flatMap(new Function<List<UserEntity>, Publisher<List<UserEntity>>>() {
                    @Override
                    public Publisher<List<UserEntity>> apply(List<UserEntity> userEntity) throws Exception {
                        if (userEntity == null || userEntity.isEmpty()) {
                            Log.d(TAG, "#getUserInfo()  ->  get data by remote");
                            return mRemoteDataSource.getUserInfo();
                        }
                        Log.d(TAG, "#getUserInfo()  ->  get data by local");
                        return Flowable.just(userEntity);
                    }
                });
    }
}
