package yummylau.modulea.http;

import com.google.gson.annotations.SerializedName;

/**
 * http请求返回
 * Created by g8931 on 2017/11/24.
 */

public class Result {

    @SerializedName("error")
    public String error;

    @SerializedName("error_code")
    public int errorCode;

    @SerializedName("request")
    public String request;
}
