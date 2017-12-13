package yummylau.feature.data;


import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;

import org.reactivestreams.Publisher;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import yummylau.common.net.HttpManager;
import yummylau.componentservice.bean.Token;
import yummylau.componentservice.interfaces.IAccountService;
import yummylau.feature.data.local.db.AppDataBase;
import yummylau.feature.data.local.db.entity.StatusEntity;
import yummylau.feature.data.local.db.entity.UserEntity;
import yummylau.feature.data.remote.HttpParamCreator;
import yummylau.feature.data.remote.api.WeiboApis;
import yummylau.feature.data.remote.result.StatusResult;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2017/12/11.
 */
@Singleton
public class FeatureRepository implements FeatureDataSource {

    @Autowired(name = IAccountService.SERVICE_NAME)
    public IAccountService accountService;
    private AppDataBase mAppDataBase;
    private WeiboApis mWeiboApis;

    @Inject
    public FeatureRepository(AppDataBase appDataBase, WeiboApis weiboApis) {
        this.mAppDataBase = appDataBase;
        this.mWeiboApis = weiboApis;
        ARouter.getInstance().inject(this);
    }

    @Override
    public LiveData<Resource<List<StatusEntity>>> getFollowedStatus() {
        return new NetworkBoundResource<List<StatusEntity>, List<StatusEntity>>() {
            @NonNull
            @Override
            protected Flowable<List<StatusEntity>> createApi() {
                return accountService.getToken(true)
                        .flatMap(new Function<Token, Publisher<List<StatusEntity>>>() {
                            @Override
                            public Publisher<List<StatusEntity>> apply(Token token) throws Exception {
                                return mWeiboApis
                                        .getAllStatus(token.accessToken)
                                        .map(new Function<StatusResult, List<StatusEntity>>() {
                                            @Override
                                            public List<StatusEntity> apply(StatusResult statusResult) throws Exception {
                                                if (statusResult != null && statusResult.statusList != null) {
                                                    return statusResult.statusList;
                                                }
                                                return null;
                                            }
                                        });
                            }
                        });
            }

            @Override
            protected void saveCallResult(@NonNull List<StatusEntity> item) {
                mAppDataBase.statusDao().insertStatusEntities(item);
            }

            @Override
            protected boolean shouldFetch(@Nullable List<StatusEntity> data) {
                return data == null || data.isEmpty();
            }

            @NonNull
            @Override
            protected LiveData<List<StatusEntity>> loadFromDb() {
                return mAppDataBase.statusDao().getStatus();
            }
        }.getAsLiveData();
    }

    @Override
    public LiveData<Resource<UserEntity>> getUserInfo(long uid) {

        return new NetworkBoundResource<UserEntity, UserEntity>() {
            @NonNull
            @Override
            protected Flowable<UserEntity> createApi() {
                return accountService.getToken(true)
                        .flatMap(new Function<Token, Publisher<UserEntity>>() {
                            @Override
                            public Publisher<UserEntity> apply(Token token) throws Exception {
                                return mWeiboApis.getUser(token.accessToken, token.uid);
                            }
                        });
            }

            @Override
            protected void saveCallResult(@NonNull UserEntity item) {
                if (item != null) {
                    mAppDataBase.userDao().insertUser(item);
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable UserEntity data) {
                return data == null;
            }

            @NonNull
            @Override
            protected LiveData<UserEntity> loadFromDb() {
                return mAppDataBase.userDao().getUser();
            }
        }.getAsLiveData();
    }
}
