package yummylau.account;

import com.alibaba.android.arouter.launcher.ARouter;
import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.auth.AuthInfo;

import yummylau.common.activity.BaseApplication;
import yummylau.account.db.DBManager;

/**
 * Created by g8931 on 2017/11/14.
 */

public class App extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        DBManager.init(this);
        WbSdk.install(this, new AuthInfo(this, Constants.APP_KEY, Constants.REDIRECT_URL, Constants.SCOPE));
    }
}
