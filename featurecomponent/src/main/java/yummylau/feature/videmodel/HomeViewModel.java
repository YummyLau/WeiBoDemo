package yummylau.feature.videmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModelProvider;
import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;


import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.FlowableSubscriber;
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
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2017/12/11.
 */
public class HomeViewModel extends AndroidViewModel {

    @Autowired(name = IAccountService.SERVICE_NAME)
    public IAccountService accountService;
    private FeatureRepository mRepository;

    private final MutableLiveData<UserEntity> ownUserInfo = new MutableLiveData<>();


    public HomeViewModel(@NonNull Application application, FeatureRepository featureRepository) {
        super(application);
        mRepository = featureRepository;
        ARouter.getInstance().inject(this);
    }

    public MutableLiveData<UserEntity> getUser() {
        return ownUserInfo;
    }

    /**
     * 初始化用户信息
     */
    public void initOwnInfo() {
        mRepository.getOwnInfo()
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
                        throwable.getMessage();
                    }
                });
    }
}
