package yummylau.feature.data.remote.result;

import com.google.gson.annotations.SerializedName;

/**
 * Created by g8931 on 2017/12/5.
 */

public class WeiboResult {

    @SerializedName("request")
    public String request;

    @SerializedName("error_code")
    public int errorCode;

    @SerializedName("error")
    public String errorMsg;
}
