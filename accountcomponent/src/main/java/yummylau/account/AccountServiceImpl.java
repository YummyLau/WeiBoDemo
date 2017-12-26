package yummylau.account;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import yummylau.account.rxjava.ConsumerThrowable;
import yummylau.common.view.RoundedDrawable;
import yummylau.componentlib.router.RouterManager;
import yummylau.componentlib.service.IService;
import yummylau.componentservice.exception.TokenInvalidException;
import yummylau.componentservice.interfaces.IAccountService;
import yummylau.componentservice.bean.Token;

/**
 * 对外暴露的Account接口
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2017/12/11.
 */
@Route(path = IAccountService.SERVICE_NAME)
public class AccountServiceImpl implements IAccountService {

    private Application mApplication;

    public Application getApplication() {
        return mApplication;
    }

    @Override
    public Flowable<Token> getToken() {
        return getToken(false);
    }

    @Override
    public Flowable<Token> getToken(final boolean checkInvalid) {
        return Flowable.just(AccessTokenKeeper.readAccessToken(getApplication()))
                .map(new Function<Oauth2AccessToken, Token>() {
                    @Override
                    public Token apply(Oauth2AccessToken oauth2AccessToken) throws Exception {
                        if (!oauth2AccessToken.isSessionValid()) {
                            if (checkInvalid) {
                                RouterManager.navigation(getLoginPath());
                            }
                            throw new TokenInvalidException();
                        }
                        Token token = new Token();
                        token.uid = Integer.valueOf(oauth2AccessToken.getUid());
                        token.accessToken = oauth2AccessToken.getToken();
                        token.refreshToken = oauth2AccessToken.getRefreshToken();
                        token.expiresTime = oauth2AccessToken.getExpiresTime();
                        token.phoneNum = oauth2AccessToken.getPhoneNum();
                        return token;
                    }
                });
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
        mApplication = application;
        WbSdk.install(application, new AuthInfo(application, Constants.APP_KEY, Constants.REDIRECT_URL, Constants.SCOPE));
    }
}
