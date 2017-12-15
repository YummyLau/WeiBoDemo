package yummylau.feature.data.remote.api;


import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
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

    /** 关系 **/

    //获取所有关注者的微博
    @GET("2/statuses/home_timeline.json")
    Flowable<StatusResult> getAllStatus(@Query("access_token") String token);

    //获取所有自己的微博
    @GET("2/statuses/user_timeline.json")
    Flowable<StatusResult> getAllStatus(@Query("access_token") String token, @Query("uid") String uid);

    //根据微博ID返回某条微博的评论列表
    @GET("2/comments/show.json")
    Flowable<StatusResult> getComments(@Query("access_token") String token, @Query("id") long statusId);

    //获取当前登录用户所发出的评论列表
    @GET("2/comments/by_me.json")
    Flowable<StatusResult> getCommentsByMe(@Query("access_token") String token);

    //获取当前登录用户所接收到的评论列表
    @GET("2/comments/to_me.json")
    Flowable<StatusResult> getCommentsToMe(@Query("access_token") String token);

    //获取当前登录用户的最新评论包括接收到的与发出的
    @GET("2/comments/to_me.json")
    Flowable<StatusResult> getCommentsTimeLine(@Query("access_token") String token);

    //对一条微博进行评论
    @POST("2/comments/create.json")
    Flowable<StatusResult> commentsCreate(@Field("access_token") String token,
                                          @Field("comment") String comment,
                                          @Field("id") long statusId);

    //对一条微博进行删除
    @POST("2/comments/destroy.json")
    Flowable<StatusResult> commentsDestory(@Field("access_token") String token,
                                           @Field("cid") long commentId);

    //回复一条评论
    @POST("2/comments/reply.json")
    Flowable<StatusResult> commentsReply(@Field("access_token") String token,
                                         @Field("cid") long commentId,
                                         @Field("id") long String);
}
