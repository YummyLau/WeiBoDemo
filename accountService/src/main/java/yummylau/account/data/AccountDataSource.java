package yummylau.account.data;

import io.reactivex.Flowable;
import yummylau.componentservice.bean.Token;

/**
 * account 模块数据接口
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2017/12/28.
 */

public interface AccountDataSource {

    Flowable<Boolean> isLogin();

    Flowable<Token> getToken();
}
