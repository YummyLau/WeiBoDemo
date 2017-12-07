package yummylau.feature.videmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;


import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;

import org.reactivestreams.Publisher;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import yummylau.componentservice.bean.Token;
import yummylau.componentservice.interfaces.IAccountService;
import yummylau.feature.data.FeatureRepository;
import yummylau.feature.data.local.db.entity.StatusEntity;
import yummylau.feature.data.local.db.entity.UserEntity;

/**
 * Created by g8931 on 2017/12/5.
 */
public class MainViewModel extends AndroidViewModel {

    @Autowired(name = IAccountService.SERVICE_NAME)
    public IAccountService accountService;
    private FeatureRepository mRepository;

    private final MutableLiveData<UserEntity> ownUserInfo = new MutableLiveData<>();
    private final MutableLiveData<List<StatusEntity>> mAllStatus = new MutableLiveData<>();
    public final ObservableBoolean dataLoading = new ObservableBoolean(false);
    public final ObservableBoolean error = new ObservableBoolean(false);


    public MainViewModel(@NonNull Application application, FeatureRepository featureRepository) {
        super(application);
        mRepository = featureRepository;
        ARouter.getInstance().inject(this);
    }

    public MutableLiveData<List<StatusEntity>> getAllStatus() {
        return mAllStatus;
    }

    public MutableLiveData<UserEntity> getUser() {
        return ownUserInfo;
    }


    public void loadUserInfo() {
        accountService.getToken()
                .flatMap(new Function<Token, Publisher<UserEntity>>() {
                    @Override
                    public Publisher<UserEntity> apply(Token token) throws Exception {
                        return mRepository.getUserInfo(token.uid);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<UserEntity>() {
                    @Override
                    public void accept(UserEntity userEntity) throws Exception {
                        ownUserInfo.setValue(userEntity);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

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
//        dataLoading.set(true);
//        mRepository.getAllStatus()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<List<StatusEntity>>() {
//                    @Override
//                    public void accept(List<StatusEntity> statusEntities) throws Exception {
//                        error.set(false);
//                        dataLoading.set(false);
//                        mAllStatus.setValue(statusEntities);
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//                        error.set(true);
//                        dataLoading.set(false);
//                    }
//                });
    }

}
