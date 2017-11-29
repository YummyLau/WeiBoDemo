package yummylau.account;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;


import yummylau.account.Constants;
import yummylau.componentservice.interfaces.IAccountService;
import yummylau.componentservice.bean.Token;

/**
 * 对外暴露的Account接口
 * Created by g8931 on 2017/11/28.
 */
@Route(path = IAccountService.SERVICE_NAME)
public class AccountServiceImpl implements IAccountService {

    @Override
    public Token getToken(Context context) {
        Oauth2AccessToken accessToken = AccessTokenKeeper.readAccessToken(context);
        Token token = new Token();
        if (accessToken.isSessionValid()) {
            token.uid = accessToken.getUid();
            token.accessToken = accessToken.getToken();
            token.refreshToken = accessToken.getRefreshToken();
            token.expiresTime = accessToken.getExpiresTime();
            token.phoneNum = accessToken.getPhoneNum();
        }
        return token;
    }

    @Override
    public String getLoginPath() {
        return Constants.ROUTER_LOGIN;
    }

    @Override
    public void init(Context context) {
        Toast.makeText(context, "初始化账号模块", Toast.LENGTH_LONG).show();
    }

    @Override
    public void createAsLibrary(Application application) {
        WbSdk.install(application, new AuthInfo(application, Constants.APP_KEY, Constants.REDIRECT_URL, Constants.SCOPE));
    }
}
