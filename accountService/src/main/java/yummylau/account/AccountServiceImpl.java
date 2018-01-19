package yummylau.account;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.alibaba.android.arouter.launcher.ARouter;
import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;


import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

import yummylau.account.data.remote.ConsumerThrowable;
import yummylau.componentlib.component.IComponent;
import yummylau.componentservice.exception.TokenInvalidException;
import yummylau.componentservice.services.IAccountService;
import yummylau.componentservice.bean.Token;

/**
 * 对外暴露的Account接口
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2017/12/11.
 */
public class AccountServiceImpl implements IAccountService {

    public Context mApplication;

    @Override
    public Class register() {
        return AccountServiceImpl.class;
    }

    @Override
    public void login(String returnActivityPath) {
        ARouter.getInstance().build(getLoginPath())
                .withString(Constants.RETURN_ACTIVITY_PATH, returnActivityPath)
                .navigation();
    }

    @Override
    public Flowable<Token> getToken() {
        return Flowable.just(AccessTokenKeeper.readAccessToken(mApplication))
                .map(new Function<Oauth2AccessToken, Token>() {
                    @Override
                    public Token apply(Oauth2AccessToken oauth2AccessToken) throws Exception {
                        if (!oauth2AccessToken.isSessionValid()) {
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
    public void createAsLibrary(Application application) {
        Log.d(IComponent.class.getSimpleName(), "account create as library...");
        Log.d(IComponent.class.getSimpleName(), "account init wbsdk...");
        mApplication = application;
        WbSdk.install(application, new AuthInfo(application, Constants.APP_KEY, Constants.REDIRECT_URL, Constants.SCOPE));
    }

    @Override
    public void release() {

    }
}
