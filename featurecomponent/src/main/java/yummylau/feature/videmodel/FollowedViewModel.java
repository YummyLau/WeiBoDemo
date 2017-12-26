package yummylau.feature.videmodel;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableBoolean;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import yummylau.componentservice.interfaces.IAccountService;
import yummylau.feature.AbsentLiveData;
import yummylau.feature.data.DataHelper;
import yummylau.feature.data.FeatureRepository;
import yummylau.feature.data.Resource;
import yummylau.feature.data.local.db.entity.StatusEntity;
import yummylau.feature.data.local.db.entity.TimeZoneEntity;

/**
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2017/12/11.
 */

public class FollowedViewModel extends ViewModel {

    @Autowired(name = IAccountService.SERVICE_NAME)
    public IAccountService accountService;

    @Inject
    public FeatureRepository featureRepository;

    private final LiveData<Resource<List<StatusEntity>>> mAllStatus;
    private final MutableLiveData<Boolean> commandRefresh = new MutableLiveData<>();

    @Inject
    public FollowedViewModel() {
        ARouter.getInstance().inject(this);
        mAllStatus = Transformations.switchMap(commandRefresh, new Function<Boolean, LiveData<Resource<List<StatusEntity>>>>() {
            @Override
            public LiveData<Resource<List<StatusEntity>>> apply(Boolean toFreshList) {
                if (toFreshList) {
                    return featureRepository.getFollowedStatus();
                } else {
                    return AbsentLiveData.create();
                }
            }
        });
    }

    public LiveData<Resource<List<StatusEntity>>> getAllStatus() {
        return mAllStatus;
    }

    public void refresh() {
        commandRefresh.setValue(true);
    }
}
