package yummylau.feature.data.remote.api;


import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import yummylau.feature.data.local.db.entity.UserEntity;
import yummylau.feature.data.remote.result.StatusResult;

/**
 * Created by g8931 on 2017/11/24.
 */

public interface WeiboApis {

    String BASE_URL = "https://api.weibo.com/";

    @GET("2/users/show.json")
    Observable<UserEntity> getUser(@Query("access_token") String token, @Query("uid") int id);

    @GET("2/statuses/home_timeline.json")
    Observable<StatusResult> getAllStatus(@Query("access_token") String token);


    @GET("2/users/show.json")
    Observable<UserEntity> getUser(@Query("access_token") String token, @Query("screen_name") String screenName);

    @GET("2/users/domain_show.json")
    Observable<UserEntity> getUserByDomain(@Query("access_token") String token, @Query("domain") String domain);
}
