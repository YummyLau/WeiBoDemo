package yummylau.feature.data.remote;

import android.app.Application;
import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;

import org.reactivestreams.Publisher;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import yummylau.common.net.HttpManager;
import yummylau.common.net.HttpParam;
import yummylau.componentlib.router.RouterManager;
import yummylau.componentservice.bean.Token;
import yummylau.componentservice.interfaces.IAccountService;
import yummylau.feature.data.FeatureDataSource;
import yummylau.feature.data.local.db.AppDataBase;
import yummylau.feature.data.local.db.entity.StatusEntity;
import yummylau.feature.data.local.db.entity.UserEntity;
import yummylau.feature.data.remote.result.StatusResult;
import yummylau.feature.data.remote.api.WeiboApis;

/**
 * Created by g8931 on 2017/12/6.
 */

public class RemoteDataSource implements FeatureDataSource {

    private static RemoteDataSource INSTANCE;
    @Autowired(name = IAccountService.SERVICE_NAME)
    public IAccountService accountService;
    public Context mContext;
    private AppDataBase mAppDataBase;


    private RemoteDataSource(Context context, AppDataBase appDataBase) {
        ARouter.getInstance().inject(this);
        this.mContext = context;
        this.mAppDataBase = appDataBase;
    }

    public static RemoteDataSource getInstance(Application application, AppDataBase appDataBase) {
        if (INSTANCE == null) {
            INSTANCE = new RemoteDataSource(application, appDataBase);

        }
        return INSTANCE;
    }

    @Override
    public Flowable<List<StatusEntity>> getFollowedStatus() {
        return accountService.getToken(true)
                .flatMap(new Function<Token, Publisher<List<StatusEntity>>>() {

                    @Override
                    public Publisher<List<StatusEntity>> apply(Token token) throws Exception {
                        return HttpManager.create(HttpParamCreator.create(), WeiboApis.class)
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
                })
                .observeOn(Schedulers.io())
                .doOnNext(new Consumer<List<StatusEntity>>() {
                    @Override
                    public void accept(List<StatusEntity> statusEntities) throws Exception {
                        mAppDataBase.statusDao().insertStatusEntities(statusEntities);
                    }
                });
    }

    @Override
    public Flowable<UserEntity> getUserInfo(final long uid) {
        return accountService.getToken(true)
                .flatMap(new Function<Token, Publisher<UserEntity>>() {
                    @Override
                    public Publisher<UserEntity> apply(Token token) throws Exception {
                        return HttpManager.create(HttpParamCreator.create(), WeiboApis.class)
                                .getUser(token.accessToken, uid);
                    }
                })
                .observeOn(Schedulers.io())
                .doOnNext(new Consumer<UserEntity>() {
                    @Override
                    public void accept(UserEntity userEntity) throws Exception {
                        mAppDataBase.userDao().insertUser(userEntity);
                    }
                });
    }

    @Override
    public Flowable<UserEntity> getOwnInfo() {
        return accountService.getToken(true)
                .flatMap(new Function<Token, Publisher<UserEntity>>() {
                    @Override
                    public Publisher<UserEntity> apply(Token token) throws Exception {
                        return HttpManager.create(HttpParamCreator.create(), WeiboApis.class)
                                .getUser(token.accessToken, token.uid);
                    }
                })
                .observeOn(Schedulers.io())
                .doOnNext(new Consumer<UserEntity>() {
                    @Override
                    public void accept(UserEntity userEntity) throws Exception {
                        mAppDataBase.userDao().insertUser(userEntity);
                    }
                });
    }
}
