package yummylau.account.data.remote;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import yummylau.account.bean.EmotionBean;

/**
 * 微博apis
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2017/12/28.
 */

public interface WeiboApis {

    String BASE_URL = "https://api.weibo.com/2/";

    /**
     * 获取官方表情
     **/
    @GET("emotions.json")
    Flowable<List<EmotionBean>> getEmotions(@Query("access_token") String token,
                                            @Query("type") String type,
                                            @Query("language") String language);
}
