package yummylau.account.bean;

import com.google.gson.annotations.SerializedName;

/**
 * 表情
 * Created by g8931 on 2017/12/28.
 */

public class EmotionBean {

    @SerializedName("category")
    public String category;

    @SerializedName("common")
    public boolean common;

    @SerializedName("hot")
    public boolean hot;

    @SerializedName("icon")
    public String icon;

    @SerializedName("phrase")
    public String phrase;

    @SerializedName("picid")
    public String picid;

    @SerializedName("type")
    public String type;

    @SerializedName("url")
    public String url;

    @SerializedName("value")
    public String value;
}
