package yummylau.feature.repository.remote.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import yummylau.feature.repository.local.db.entity.StatusEntity;

/**
 * Created by g8931 on 2017/12/5.
 */

public class StatusResult extends WeiboResult {

    @SerializedName("total_number")
    public int totalNum;

    @SerializedName("statuses")
    public List<StatusEntity> statusList;
}
