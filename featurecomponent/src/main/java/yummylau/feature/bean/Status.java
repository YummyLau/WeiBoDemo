package yummylau.feature.bean;

import com.google.gson.annotations.SerializedName;

/**
 * 微博实体
 * Created by g8931 on 2017/11/24.
 */

public class Status {

    //微博创建时间
    @SerializedName("created_at")
    public String created_at;

    //微博ID
    @SerializedName("id")
    public int id;

    //微博MID
    @SerializedName("mid")
    public int mid;

    //字符串型的微博ID
    @SerializedName("idstr")
    public String idstr;

    //微博信息内容
    @SerializedName("text")
    public String text;

    //微博来源
    @SerializedName("source")
    public String source;

    //是否已收藏，true：是，false：否
    @SerializedName("favorited")
    public boolean favorited;

    //是否被截断，true：是，false：否
    @SerializedName("truncated")
    public boolean truncated;

    //（暂未支持）回复ID
    @SerializedName("in_reply_to_status_id")
    public String inReplyToStatusId;

    //（暂未支持）回复人UID
    @SerializedName("in_reply_to_user_id")
    public String inReplyToUserId;

    //（暂未支持）回复人昵称
    @SerializedName("in_reply_to_screen_name")
    public String inReplyToScreenName;

    //缩略图片地址，没有时不返回此字段
    @SerializedName("thumbnail_pic")
    public String thumbnailPic;

    //中等尺寸图片地址，没有时不返回此字段
    @SerializedName("bmiddle_pic")
    public String bmiddlePic;

    //原始图片地址，没有时不返回此字段
    @SerializedName("original_pic")
    public String original_pic;

    //地理信息字段
    @SerializedName("geo")
    public Geo geo;

    //微博作者的用户信息字段
    @SerializedName("user")
    public User user;

    //被转发的原微博信息字段，当该微博为转发微博时返回
    @SerializedName("retweeted_status")
    public Status retweetedStatus;

    //转发数
    @SerializedName("reposts_count")
    public int repostsCount;

    //评论数
    @SerializedName("comments_count")
    public int commentsCount;

    //表态数
    @SerializedName("attitudes_count")
    public int attitudesCount;

    //暂未支持
    @SerializedName("mlevel")
    public int mlevel;

    //微博的可见性及指定可见分组信息。该object中type取值，0：普通微博，1：私密微博，3：指定分组微博，4：密友微博；list_id为分组的组号
    @SerializedName("visible")
    public String visible;

    //微博配图ID。多图时返回多图ID，用来拼接图片url。用返回字段thumbnail_pic的地址配上该返回字段的图片ID，即可得到多个图片url。
    @SerializedName("pic_ids")
    public String pic_ids;

    //微博流内的推广微博ID
    @SerializedName("ad")
    public String ad;
}
