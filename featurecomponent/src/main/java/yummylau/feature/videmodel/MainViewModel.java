package yummylau.feature.videmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;

import java.util.List;

import rx.Subscriber;
import yummylau.feature.data.FeatureRepository;
import yummylau.feature.data.local.db.entity.StatusEntity;
import yummylau.feature.data.local.db.entity.UserEntity;
import yummylau.feature.data.remote.result.StatusResult;

/**
 * Created by g8931 on 2017/12/5.
 */

public class MainViewModel extends AndroidViewModel {


    private MutableLiveData<UserEntity> ownUserInfo;
    private MutableLiveData<List<StatusEntity>> mAllStatus;
    private FeatureRepository mRepository;


    public final ObservableBoolean dataLoading = new ObservableBoolean(false);
    public final ObservableBoolean error = new ObservableBoolean(false);


    public MainViewModel(@NonNull Application application, FeatureRepository featureRepository) {
        super(application);
        mRepository = featureRepository;
        mAllStatus = new MutableLiveData<>();
        ownUserInfo = new MutableLiveData<>();
    }

    public MutableLiveData<List<StatusEntity>> getAllStatus() {
        return mAllStatus;
    }

    public MutableLiveData<UserEntity> getUser() {
        return ownUserInfo;
    }

    public void loadUserInfo() {
        mRepository.getUserInfo()
                .subscribe(new Subscriber<UserEntity>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(UserEntity userEntity) {
                        ownUserInfo.setValue(userEntity);
                    }
                });
    }

    public void start() {
        fetchData();
    }

    public void onRefresh() {
        fetchData();
    }

    public void fetchData() {
        dataLoading.set(true);
        mRepository.getAllStatus()
                .subscribe(new Subscriber<StatusResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        error.set(true);
                        dataLoading.set(false);
                    }

                    @Override
                    public void onNext(StatusResult statusResult) {
                        error.set(false);
                        dataLoading.set(false);
                        if (statusResult != null && statusResult.statusList != null) {
                            mAllStatus.setValue(statusResult.statusList);
                        }
                    }
                });

    }

}
