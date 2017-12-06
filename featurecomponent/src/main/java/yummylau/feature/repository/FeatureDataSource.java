package yummylau.feature.repository;

import rx.Observable;
import yummylau.feature.repository.remote.api.StatusResult;

/**
 * Created by g8931 on 2017/12/6.
 */
public interface FeatureDataSource {

    Observable<StatusResult> getAllStatus();

}
