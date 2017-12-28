package yummylau.account.data;

import java.util.List;

import io.reactivex.Flowable;
import yummylau.account.bean.EmotionBean;
import yummylau.componentservice.bean.Token;

/**
 * account 模块数据接口
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2017/12/28.
 */

public interface AccountDataSource {

    Flowable<List<EmotionBean>> getEmotions();

    Flowable<Boolean> isLogin();

    Flowable<Token> getToken();
}
