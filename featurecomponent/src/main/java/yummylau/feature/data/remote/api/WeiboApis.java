package yummylau.feature.data.remote.api;


import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import yummylau.feature.bean.Comment;
import yummylau.feature.data.local.db.entity.UserEntity;
import yummylau.feature.data.remote.result.CommentListResult;
import yummylau.feature.data.remote.result.FriendFollowResult;
import yummylau.feature.data.remote.result.StatusResult;

/**
 * Created by g8931 on 2017/11/24.
 */

public interface WeiboApis {

    String BASE_URL = "https://api.weibo.com/2/";

    /** 用户 **/
    @GET("users/show.json")
    Flowable<UserEntity> getUser(@Query("access_token") String token, @Query("uid") long id);

    /** 关系 **/
    //获取用户的关注列表
    @GET("friendships/friends.json")
    Flowable<FriendFollowResult> getFriends(@Query("access_token") String token);
    //获取用户的粉丝列表
    @GET("friendships/followers.json")
    Flowable<FriendFollowResult> getFollowers(@Query("access_token") String token);


    /** 微博 **/
    //获取所有关注者的微博
    @GET("statuses/home_timeline.json")
    Flowable<StatusResult> getAllStatus(@Query("access_token") String token);
    //获取所有自己的微博
    @GET("statuses/user_timeline.json")
    Flowable<StatusResult> getOwnStatus(@Query("access_token") String token,
                                        @Query("uid") String uid);


    /** 评论 **/
    //根据微博ID返回某条微博的评论列表
    @GET("comments/show.json")
    Flowable<CommentListResult> getComments(@Query("access_token") String token,
                                            @Query("id") long statusId);
    //获取当前登录用户所发出的评论列表
    @GET("comments/by_me.json")
    Flowable<CommentListResult> getCommentsByMe(@Query("access_token") String token);
    //获取当前登录用户所接收到的评论列表
    @GET("comments/to_me.json")
    Flowable<CommentListResult> getCommentsToMe(@Query("access_token") String token);
    //获取当前登录用户的最新评论包括接收到的与发出的
    @GET("comments/to_me.json")
    Flowable<CommentListResult> getCommentsTimeLine(@Query("access_token") String token);
    //对一条微博进行评论
    @POST("comments/create.json")
    Flowable<Comment> commentsCreate(@Field("access_token") String token,
                                     @Field("comment") String comment,
                                     @Field("id") long statusId);
    //对一条微博进行删除
    @POST("comments/destroy.json")
    Flowable<Comment> commentsDestory(@Field("access_token") String token,
                                           @Field("cid") long commentId);
    //回复一条评论
    @POST("comments/reply.json")
    Flowable<Comment> commentsReply(@Field("access_token") String token,
                                         @Field("cid") long commentId,
                                         @Field("id") long String);
}
