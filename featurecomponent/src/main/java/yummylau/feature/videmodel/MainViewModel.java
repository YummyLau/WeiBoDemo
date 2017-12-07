package yummylau.feature.videmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
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
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<UserEntity>>() {
                    @Override
                    public void accept(List<UserEntity> userEntity) throws Exception {
                        try {
                            ownUserInfo.setValue(userEntity.get(0));
                        } catch (Exception e) {
                            e.getMessage();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.getMessage();
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
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<StatusEntity>>() {
                    @Override
                    public void accept(List<StatusEntity> statusEntities) throws Exception {
                        error.set(false);
                        dataLoading.set(false);
                        mAllStatus.setValue(statusEntities);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        error.set(true);
                        dataLoading.set(false);
                    }
                });
    }

}
