package yummylau.feature.repository.remote.api;


import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;
import yummylau.feature.repository.local.db.entity.UserEntity;

/**
 * Created by g8931 on 2017/11/24.
 */

public interface WeiboApis {

    String BASE_URL = "https://api.weibo.com/";

    @GET("2/users/show.json")
    Observable<UserEntity> getUser(@Query("access_token") String token, @Query("uid") int id);

    @GET("2/users/show.json")
    Observable<UserEntity> getUser(@Query("access_token") String token, @Query("screen_name") String screenName);

    @GET("2/users/domain_show.json")
    Observable<UserEntity> getUserByDomain(@Query("access_token") String token, @Query("domain") String domain);

    @GET
    Observable<String> getTest(@Url String url);
}
