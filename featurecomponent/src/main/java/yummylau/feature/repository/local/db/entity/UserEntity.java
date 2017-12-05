package yummylau.feature.repository.local.db.entity;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

/**
 * Created by g8931 on 2017/11/24.
 */
@Entity(tableName = "user_table")
public class UserEntity {

    //用户UID
    @PrimaryKey
    @SerializedName("id")
    public long id;

    //字符串型的用户UID
    @SerializedName("idStr")
    public String idStr;

    //用户昵称
    @SerializedName("screen_name")
    public String nick;

    //友好显示名称
    @SerializedName("name")
    public String name;

    //用户所在省级ID
    @SerializedName("province")
    public int province;

    //用户所在城市ID
    @SerializedName("city")
    public int city;

    //用户所在地
    @SerializedName("location")
    public String location;

    //用户个人描述
    @SerializedName("description")
    public String description;

    //用户博客地址
    @SerializedName("url")
    public String url;

    //用户头像地址（中图），50×50像素
    @SerializedName("profile_image_url")
    public String profileImageUrl;

    //用户的微博统一URL地址
    @SerializedName("profile_url")
    public String profile_url;

    //用户的个性化域名
    @SerializedName("domain")
    public String domain;

    //用户的微号
    @SerializedName("weihao")
    public String weihao;

    //性别，m：男、f：女、n：未知
    @SerializedName("gender")
    public String gender;

    //粉丝数
    @SerializedName("followers_count")
    public int followersCount;

    //关注数
    @SerializedName("friends_count")
    public int friendsCount;

    //微博数
    @SerializedName("statuses_count")
    public int statusesCount;

    //收藏数
    @SerializedName("favourites_count")
    public int favouritesCount;

    //用户创建（注册）时间
    @SerializedName("created_at")
    public String createdAt;

    //暂未支持
    @SerializedName("following")
    public boolean following;

    //是否允许所有人给我发私信，true：是，false：否
    @SerializedName("allow_all_act_msg")
    public boolean allowAllActMsg;

    //是否允许标识用户的地理位置，true：是，false：否
    @SerializedName("geo_enabled")
    public boolean geoEnabled;

    //是否是微博认证用户，即加V用户，true：是，false：否
    @SerializedName("verified")
    public boolean verified;

    //暂未支持
    @SerializedName("verified_type")
    public int verified_type;

    //用户备注信息，只有在查询用户关系时才返回此字段
    @SerializedName("remark")
    public String remark;

    //用户的最近一条微博信息字段
    @Ignore
    @SerializedName("status")
    public StatusEntity status;

    //是否允许所有人对我的微博进行评论，true：是，false：否
    @SerializedName("allow_all_comment")
    public boolean allowAllComment;

    //用户头像地址（大图），180×180像素
    @SerializedName("avatar_large")
    public String avatarLarge;

    //用户头像地址（高清），高清头像原图
    @SerializedName("avatar_hd")
    public String avatarHd;

    //认证原因
    @SerializedName("verified_reason")
    public String verifiedReason;

    //该用户是否关注当前登录用户，true：是，false：否
    @SerializedName("follow_me")
    public boolean followMe;

    //用户的在线状态，0：不在线、1：在线
    @SerializedName("online_status")
    public int onlineStatus;

    //用户的互粉数
    @SerializedName("bi_followers_count")
    public int biFollowersCount;

    //用户当前的语言版本，zh-cn：简体中文，zh-tw：繁体中文，en：英语
    @SerializedName("lang")
    public String lang;
}
