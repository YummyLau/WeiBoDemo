package yummylau.feature.data.remote.result;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import yummylau.feature.data.local.db.entity.UserEntity;

/**
 * 针对 {@link yummylau.feature.data.remote.api.WeiboApis#getFriends(String)} 接口处理gson数据
 * 针对 {@link yummylau.feature.data.remote.api.WeiboApis#getFollowers(String)} 接口处理gson数据
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2017/12/16.
 */

public class FriendFollowResult extends ApiResult {

    @SerializedName("next_cursor")
    public int nextCursor;

    @SerializedName("total_number")
    public int totalNumber;

    @SerializedName("previous_cursor")
    public int previousCursor;

    @SerializedName("users")
    public List<UserEntity> userList;
}


