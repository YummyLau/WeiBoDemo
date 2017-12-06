package yummylau.feature.data;

import rx.Observable;
import yummylau.feature.data.local.db.entity.UserEntity;
import yummylau.feature.data.remote.result.StatusResult;

/**
 * Created by g8931 on 2017/12/6.
 */
public interface FeatureDataSource {

    Observable<StatusResult> getAllStatus();

    Observable<UserEntity> getUserInfo();

}
