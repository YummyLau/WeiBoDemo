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
    public Flowable<List<StatusEntity>> getFollowedStatus() {
        return mLocalDataSource.getFollowedStatus()
                .flatMap(new Function<List<StatusEntity>, Publisher<List<StatusEntity>>>() {
                    @Override
                    public Publisher<List<StatusEntity>> apply(List<StatusEntity> statusEntities) throws Exception {
                        if (statusEntities == null || statusEntities.isEmpty()) {
                            Log.d(TAG, "#getAllStatus()  ->  get data by remote");
                            return mRemoteDataSource.getFollowedStatus();
                        }
                        Log.d(TAG, "#getAllStatus()  ->  get data by local");
                        return Flowable.just(statusEntities);
                    }
                });
    }

    @Override
    public Flowable<UserEntity> getUserInfo(final long uid) {
        return mLocalDataSource.getUserInfo(uid)
                .flatMap(new Function<UserEntity, Publisher<UserEntity>>() {
                    @Override
                    public Publisher<UserEntity> apply(UserEntity userEntity) throws Exception {
                        if (userEntity == null) {
                            Log.d(TAG, "#getUserInfo()  ->  get data by remote");
                            return mRemoteDataSource.getUserInfo(uid);
                        }
                        Log.d(TAG, "#getUserInfo()  ->  get data by local");
                        return Flowable.just(userEntity);
                    }
                });
    }

    @Override
    public Flowable<UserEntity> getOwnInfo() {
        return mLocalDataSource.getOwnInfo()
                .flatMap(new Function<UserEntity, Publisher<UserEntity>>() {
                    @Override
                    public Publisher<UserEntity> apply(UserEntity userEntity) throws Exception {
                        if (userEntity == null) {
                            Log.d(TAG, "#getUserInfo()  ->  get data by remote");
                            return mRemoteDataSource.getOwnInfo();
                        }
                        Log.d(TAG, "#getUserInfo()  ->  get data by local");
                        return Flowable.just(userEntity);
                    }
                });
    }
}
