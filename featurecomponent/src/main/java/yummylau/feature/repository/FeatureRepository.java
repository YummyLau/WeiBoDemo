package yummylau.feature.repository;


import android.content.Context;
import android.support.annotation.NonNull;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;

import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import yummylau.common.net.HttpManager;
import yummylau.common.net.HttpParam;
import yummylau.componentlib.router.RouterManager;
import yummylau.componentservice.bean.Token;
import yummylau.componentservice.interfaces.IAccountService;
import yummylau.feature.repository.remote.api.StatusResult;
import yummylau.feature.repository.remote.api.WeiboApis;

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

}
