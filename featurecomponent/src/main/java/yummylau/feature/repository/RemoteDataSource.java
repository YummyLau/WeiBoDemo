package yummylau.feature.repository;

import android.app.Application;
import android.content.Context;

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
 * Created by g8931 on 2017/12/6.
 */

public class RemoteDataSource implements FeatureDataSource {

    private static RemoteDataSource INSTANCE;
    @Autowired(name = IAccountService.SERVICE_NAME)
    public IAccountService accountService;
    public Context mContext;


    private RemoteDataSource(Context context) {
        ARouter.getInstance().inject(this);
        this.mContext = context;
    }

    public static RemoteDataSource getInstance(Application application) {
        if (INSTANCE == null) {
            INSTANCE = new RemoteDataSource(application);

        }
        return INSTANCE;
    }

    @Override
    public Observable<StatusResult> getAllStatus() {
        HttpParam httpParam =
                new HttpParam.Builder()
                        .baseUrl(WeiboApis.BASE_URL)
                        .callAdatperFactory(RxJavaCallAdapterFactory.create())
                        .converterFactory(GsonConverterFactory.create())
                        .build();
        Token token = accountService.getToken(mContext);
        if (token.accessToken == null) {
            RouterManager.navigation(accountService.getLoginPath());
        }
        return HttpManager.create(httpParam, WeiboApis.class)
                .getAllStatus(accountService.getToken(mContext).accessToken);
    }
}
