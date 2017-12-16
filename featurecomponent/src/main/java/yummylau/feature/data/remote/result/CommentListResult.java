package yummylau.feature.data.remote.result;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import yummylau.feature.bean.Comment;

/**
 * 针对 {@link yummylau.feature.data.remote.api.WeiboApis#getComments(String, long)}  接口处理gson数据
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2017/12/16.
 */

public class CommentListResult extends ApiResult {

    @SerializedName("next_cursor")
    public int nextCursor;

    @SerializedName("total_number")
    public int totalNumber;

    @SerializedName("previous_cursor")
    public int previousCursor;

    @SerializedName("comments")
    public List<Comment> commentList;
}
