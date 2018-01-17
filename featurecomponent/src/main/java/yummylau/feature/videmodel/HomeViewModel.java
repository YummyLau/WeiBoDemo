package yummylau.feature.videmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;


import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;


import javax.inject.Inject;

import yummylau.componentservice.services.IAccountService;
import yummylau.feature.AbsentLiveData;
import yummylau.feature.data.FeatureRepository;
import yummylau.feature.data.Resource;
import yummylau.feature.data.local.db.entity.UserEntity;

/**
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2017/12/11.
 */
public class HomeViewModel extends ViewModel {

    @Inject
    public FeatureRepository featureRepository;

    private final LiveData<Resource<UserEntity>> ownUserInfo;
    private final MutableLiveData<Boolean> commandInitInfo = new MutableLiveData<>();

    @Inject
    public HomeViewModel() {
        ARouter.getInstance().inject(this);
        ownUserInfo = Transformations.switchMap(commandInitInfo, new android.arch.core.util.Function<Boolean, LiveData<Resource<UserEntity>>>() {
            @Override
            public LiveData<Resource<UserEntity>> apply(Boolean commandInitInfo) {
                if (commandInitInfo) {
                    return featureRepository.getUserInfo(11l);
                } else {
                    return AbsentLiveData.create();
                }
            }
        });
    }

    public LiveData<Resource<UserEntity>> getUser() {
        return ownUserInfo;
    }

    public void initInfo() {
        commandInitInfo.setValue(true);
    }

}
