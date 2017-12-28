package yummylau.account.data;

import android.app.Application;
import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import org.reactivestreams.Publisher;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import yummylau.account.bean.EmotionBean;
import yummylau.account.data.remote.WeiboApis;
import yummylau.componentservice.bean.Token;
import yummylau.componentservice.exception.TokenInvalidException;

/**
 * 账号模块仓库
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2017/12/28.
 */
@Singleton
public class AccountRepository implements AccountDataSource {

    private WeiboApis mWeiboApis;
    private Application mApplication;


    @Inject
    public AccountRepository(WeiboApis weiboApis, Application application) {
        this.mWeiboApis = weiboApis;
        this.mApplication = application;
    }

    @Override
    public Flowable<Boolean> isLogin() {
        return Flowable.just(AccessTokenKeeper.readAccessToken(mApplication))
                .map(new Function<Oauth2AccessToken, Boolean>() {
                    @Override
                    public Boolean apply(Oauth2AccessToken oauth2AccessToken) throws Exception {
                        return oauth2AccessToken != null && oauth2AccessToken.isSessionValid();
                    }
                });
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
    public Flowable<List<EmotionBean>> getEmotions() {
        return getToken()
                .flatMap(new Function<Token, Publisher<List<EmotionBean>>>() {
                    @Override
                    public Publisher<List<EmotionBean>> apply(Token token) throws Exception {
                        return mWeiboApis.getEmotions(token.accessToken, "face", "cnname");
                    }
                });
    }

}
