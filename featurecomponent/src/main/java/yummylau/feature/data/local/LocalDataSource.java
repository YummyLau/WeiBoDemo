package yummylau.feature.data.local;


import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;

import org.reactivestreams.Publisher;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import yummylau.componentservice.bean.Token;
import yummylau.componentservice.interfaces.IAccountService;
import yummylau.feature.data.FeatureDataSource;
import yummylau.feature.data.local.db.AppDataBase;
import yummylau.feature.data.local.db.entity.StatusEntity;
import yummylau.feature.data.local.db.entity.UserEntity;

/**
 * Created by g8931 on 2017/12/6.
 */

public class LocalDataSource implements FeatureDataSource {

    private static LocalDataSource INSTANCE;
    @Autowired(name = IAccountService.SERVICE_NAME)
    public IAccountService accountService;
    private AppDataBase mAppDataBase;

    private LocalDataSource(AppDataBase appDataBase) {
        ARouter.getInstance().inject(this);
        this.mAppDataBase = appDataBase;
    }

    public static LocalDataSource getInstance(AppDataBase appDataBase) {
        if (INSTANCE == null) {
            INSTANCE = new LocalDataSource(appDataBase);
        }
        return INSTANCE;
    }


    @Override
    public Flowable<List<StatusEntity>> getFollowedStatus() {
        return mAppDataBase.statusDao().getStatus();
    }

    @Override
    public Flowable<UserEntity> getUserInfo(final long uid) {
        return mAppDataBase.userDao().getUserById(uid)
                .map(new Function<List<UserEntity>, UserEntity>() {
                    @Override
                    public UserEntity apply(List<UserEntity> userEntities) throws Exception {
                        //解决使用room查询时flowable查询不到时不发送事件，改成list返回集
                        if (userEntities == null || userEntities.isEmpty()) {
                            return UserEntity.EMPTY_OBJ;
                        } else {
                            return userEntities.get(0);
                        }
                    }
                });
    }

    @Override
    public Flowable<UserEntity> getOwnInfo() {
        return accountService.getToken(true)
                .flatMap(new Function<Token, Publisher<UserEntity>>() {
                    @Override
                    public Publisher<UserEntity> apply(Token token) throws Exception {
                        return mAppDataBase.userDao().getUserById(token.uid)
                                .map(new Function<List<UserEntity>, UserEntity>() {
                                    @Override
                                    public UserEntity apply(List<UserEntity> userEntities) throws Exception {
                                        //解决使用room查询时flowable查询不到时不发送事件，改成list返回集
                                        return UserEntity.EMPTY_OBJ;
                                    }
                                });
                    }
                });
    }
}
