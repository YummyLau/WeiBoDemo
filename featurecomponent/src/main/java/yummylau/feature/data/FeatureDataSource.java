package yummylau.feature.data;

import java.util.List;

import io.reactivex.Flowable;
import yummylau.feature.data.local.db.entity.StatusEntity;
import yummylau.feature.data.local.db.entity.UserEntity;

/**
 * Created by g8931 on 2017/12/6.
 */
public interface FeatureDataSource {

    Flowable<List<StatusEntity>> getFollowedStatus();

    Flowable<UserEntity> getUserInfo(long uid);

    Flowable<UserEntity> getOwnInfo();
}
