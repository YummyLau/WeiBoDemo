package yummylau.feature.data;

import java.util.List;

import io.reactivex.Flowable;
import yummylau.feature.data.local.db.entity.StatusEntity;
import yummylau.feature.data.local.db.entity.UserEntity;

/**
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2017/12/11.
 */
public interface FeatureDataSource {

    Flowable<List<StatusEntity>> getFollowedStatus();

    Flowable<UserEntity> getUserInfo(long uid);

    Flowable<UserEntity> getOwnInfo();
}
