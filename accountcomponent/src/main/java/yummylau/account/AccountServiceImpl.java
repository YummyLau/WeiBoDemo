package yummylau.account;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.auth.AuthInfo;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;
import yummylau.account.data.AccountRepository;
import yummylau.account.di.component.AccountComponent;
import yummylau.account.di.component.DaggerAccountComponent;
import yummylau.account.rxjava.ConsumerThrowable;
import yummylau.componentlib.service.IService;
import yummylau.componentservice.interfaces.IAccountService;
import yummylau.componentservice.bean.Token;

/**
 * 对外暴露的Account接口
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2017/12/11.
 */
@Route(path = IAccountService.SERVICE_NAME)
public class AccountServiceImpl implements IAccountService {

    public Context mApplication;

    @Inject
    public AccountRepository mAccountRepository;

    @Override
    public Flowable<Token> getToken() {
        return mAccountRepository.getToken();
    }

    @Override
    public String getLoginPath() {
        return Constants.ROUTER_LOGIN;
    }


    @Override
    public Consumer<Throwable> CreateConsumerThrowable(Consumer<Throwable> consumer) {
        return new ConsumerThrowable(consumer);
    }

    @Override
    public void init(Context context) {
        Toast.makeText(context, "初始化账号模块", Toast.LENGTH_LONG).show();
    }

    @Override
    public void createAsLibrary(Application application) {
        Log.d(IService.class.getSimpleName(), "account create as library...");
        Log.d(IService.class.getSimpleName(), "account init wbsdk...");
        AccountComponent accountComponent = DaggerAccountComponent.builder()
                .context(application)
//                .application(application)
                .build();
//        accountComponent.inject(application);
        accountComponent.inject(this);
        mApplication = application;
        WbSdk.install(application, new AuthInfo(application, Constants.APP_KEY, Constants.REDIRECT_URL, Constants.SCOPE));
    }
}
