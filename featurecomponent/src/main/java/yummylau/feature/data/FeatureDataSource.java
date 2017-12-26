package yummylau.feature.data;


import android.arch.lifecycle.LiveData;

import java.util.List;

import yummylau.feature.data.local.db.entity.StatusEntity;
import yummylau.feature.data.local.db.entity.TimeZoneEntity;
import yummylau.feature.data.local.db.entity.UserEntity;

/**
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2017/12/11.
 */
public interface FeatureDataSource {

    LiveData<Resource<List<TimeZoneEntity>>> initTimeZones();

    LiveData<Resource<List<StatusEntity>>> getFollowedStatus();

    LiveData<Resource<UserEntity>> getUserInfo(long uid);
}
