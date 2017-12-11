package yummylau.account.http;

import com.google.gson.annotations.SerializedName;

/**
 * http请求返回
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2017/12/11.
 */

public class Result {

    @SerializedName("error")
    public String error;

    @SerializedName("error_code")
    public int errorCode;

    @SerializedName("request")
    public String request;
}
