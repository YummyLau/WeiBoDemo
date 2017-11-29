package yummylau.feature.bean;

import com.google.gson.annotations.SerializedName;

/**
 * 短链
 * Created by g8931 on 2017/11/24.
 */

public class UrlShort {

    //短链接
    @SerializedName("url_short")
    public String urlShort;

    //原始长链接
    @SerializedName("url_long")
    public String urlLong;

    //链接的类型，0：普通网页、1：视频、2：音乐、3：活动、5、投票
    @SerializedName("type")
    public int type;

    //短链的可用状态，true：可用、false：不可用。
    @SerializedName("result")
    public boolean result;
}
