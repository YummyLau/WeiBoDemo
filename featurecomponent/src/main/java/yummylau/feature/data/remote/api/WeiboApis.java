package yummylau.feature.data.remote.api;


import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import yummylau.feature.data.local.db.entity.UserEntity;
import yummylau.feature.data.remote.result.StatusResult;

/**
 * Created by g8931 on 2017/11/24.
 */

public interface WeiboApis {

    String BASE_URL = "https://api.weibo.com/";

    @GET("2/users/show.json")
    Flowable<UserEntity> getUser(@Query("access_token") String token, @Query("uid") long id);

    @GET("2/statuses/home_timeline.json")
    Flowable<StatusResult> getAllStatus(@Query("access_token") String token);


    @GET("2/users/show.json")
    Flowable<UserEntity> getUser(@Query("access_token") String token, @Query("screen_name") String screenName);

    @GET("2/users/domain_show.json")
    Flowable<UserEntity> getUserByDomain(@Query("access_token") String token, @Query("domain") String domain);
}
