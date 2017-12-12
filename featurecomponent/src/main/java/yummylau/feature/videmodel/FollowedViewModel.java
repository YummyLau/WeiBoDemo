package yummylau.feature.videmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import yummylau.componentservice.interfaces.IAccountService;
import yummylau.feature.data.FeatureRepository;
import yummylau.feature.data.local.db.entity.StatusEntity;

/**
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2017/12/11.
 */

public class FollowedViewModel extends ViewModel {

    @Autowired(name = IAccountService.SERVICE_NAME)
    public IAccountService accountService;

    @Inject
    public FeatureRepository mRepository;

    public final ObservableBoolean dataLoading = new ObservableBoolean(false);
    public final ObservableBoolean error = new ObservableBoolean(false);
    private final MutableLiveData<List<StatusEntity>> mAllStatus = new MutableLiveData<>();

    @Inject
    public FollowedViewModel(FeatureRepository featureRepository) {
        mRepository = featureRepository;
        ARouter.getInstance().inject(this);
    }

    public MutableLiveData<List<StatusEntity>> getAllStatus() {
        return mAllStatus;
    }

    public void start() {
        dataLoading.set(true);
        mRepository.getFollowedStatus()
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
