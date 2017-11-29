package yummylau.feature.api;


import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;
import rx.Observer;
import yummylau.feature.bean.User;

/**
 * Created by g8931 on 2017/11/24.
 */

public interface WeiboApis {

    String BASE_URL = "https://api.weibo.com/";

    @GET("2/users/show.json")
    Observable<User> getUser(@Query("access_token") String token, @Query("uid") int id);

    @GET("2/users/show.json")
    Observable<User> getUser(@Query("access_token") String token, @Query("screen_name") String screenName);

    @GET("2/users/domain_show.json")
    Observable<User> getUserByDomain(@Query("access_token") String token, @Query("domain") String domain);

    @GET
    Observable<String> getTest(@Url String url);
}
