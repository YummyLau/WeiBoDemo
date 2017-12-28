package yummylau.feature.data;


import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import yummylau.componentservice.bean.Token;
import yummylau.componentservice.interfaces.IAccountService;
import yummylau.feature.data.local.db.AppDataBase;
import yummylau.feature.data.local.db.converter.Converters;
import yummylau.feature.data.local.db.entity.StatusEntity;
import yummylau.feature.data.local.db.entity.TimeZoneEntity;
import yummylau.feature.data.local.db.entity.UserEntity;
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
    public LiveData<Resource<List<TimeZoneEntity>>> initTimeZones() {
        return new NetworkBoundResource<List<TimeZoneEntity>, Map<Integer, String>>() {
            @NonNull
            @Override
            protected Flowable<Map<Integer, String>> createApi() {
                return accountService.getToken()
                        .flatMap(new Function<Token, Publisher<Map<Integer, String>>>() {
                            @Override
                            public Publisher<Map<Integer, String>> apply(Token token) throws Exception {
                                return mWeiboApis.getTimezone(token.accessToken, null);
                            }
                        });
            }

            @Override
            protected void saveCallResult(@NonNull Map<Integer, String> item) {
                if (item != null && !item.isEmpty()) {
                    Set<Integer> integers = item.keySet();
                    List<TimeZoneEntity> timeZoneEntities = new ArrayList<>();
                    for (Integer key : integers) {
                        final TimeZoneEntity timeZoneEntity = new TimeZoneEntity();
                        timeZoneEntity.id = key;
                        timeZoneEntity.msg = item.get(key);
                        if (!TextUtils.isEmpty(timeZoneEntity.msg) && timeZoneEntity.msg.length() >= 11) {
                            timeZoneEntity.weiboStr = timeZoneEntity.msg.substring(1, 10).replace(":", "");
                        }
                        timeZoneEntities.add(timeZoneEntity);
                    }
                    if (!timeZoneEntities.isEmpty()) {
                        mAppDataBase.timeZoneDao().insertTimeZoneEntities(timeZoneEntities);
                    }
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable List<TimeZoneEntity> data) {
                return data == null || data.isEmpty();
//                return true;
            }


            @NonNull
            @Override
            protected LiveData<List<TimeZoneEntity>> loadFromDb() {
                return mAppDataBase.timeZoneDao().getTimeZones();
            }
        }.getAsLiveData();
    }


    @Override
    public LiveData<Resource<List<StatusEntity>>> getFollowedStatus() {
        return new NetworkBoundResource<List<StatusEntity>, List<StatusEntity>>() {
            @NonNull
            @Override
            protected Flowable<List<StatusEntity>> createApi() {
                return accountService.getToken()
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
                return accountService.getToken()
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
