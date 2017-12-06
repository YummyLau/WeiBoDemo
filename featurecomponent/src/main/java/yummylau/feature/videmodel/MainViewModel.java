package yummylau.feature.videmodel;

import android.app.Application;
import android.arch.core.util.Function;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.support.annotation.NonNull;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;

import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import yummylau.common.net.HttpManager;
import yummylau.common.net.HttpParam;
import yummylau.componentlib.router.RouterManager;
import yummylau.componentservice.bean.Token;
import yummylau.componentservice.interfaces.IAccountService;
import yummylau.feature.repository.MainRepository;
import yummylau.feature.repository.local.db.entity.UserEntity;
import yummylau.feature.repository.remote.api.WeiboApis;


/**
 * 数据实体
 * Created by g8931 on 2017/11/29.
 */

public class MainViewModel extends AndroidViewModel {

    @Autowired(name = IAccountService.SERVICE_NAME)
    public IAccountService accountService;

    private MutableLiveData<String> mCurrentName = new MutableLiveData<>();
    private LiveData<String> mAfterTranName = Transformations.map(mCurrentName, new Function<String, String>() {
        @Override
        public String apply(String input) {
            return "转化后的数据" + input;
        }
    });

    public LiveData<String> getAfterName() {
        return mAfterTranName;
    }

    public MutableLiveData<String> getCurrentName() {
        if (mCurrentName == null) {
            mCurrentName = new MutableLiveData<String>();
        }
        return mCurrentName;
    }


    private MutableLiveData<UserEntity> ownUserInfo;
    private MainRepository mainRepository;
    private Context context;

    public MainViewModel(@NonNull Application application) {
        super(application);
        mainRepository = new MainRepository();
        ownUserInfo = new MutableLiveData<>();
        context = application;
        ARouter.getInstance().inject(this);
    }

    public MutableLiveData<UserEntity> getUser() {
        return ownUserInfo;
    }

    public void fetchData() {
        HttpParam httpParam =
                new HttpParam.Builder()
                        .baseUrl(WeiboApis.BASE_URL)
                        .callAdatperFactory(RxJavaCallAdapterFactory.create())
                        .converterFactory(GsonConverterFactory.create())
                        .build();
        Token token = accountService.getToken(context);
        if (token.accessToken == null) {
            RouterManager.navigation(accountService.getLoginPath());
        }
        HttpManager.create(httpParam, WeiboApis.class)
                .getUser(accountService.getToken(context).accessToken, accountService.getToken(context).uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<UserEntity>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.getMessage();
                    }

                    @Override
                    public void onNext(UserEntity s) {
                        ownUserInfo.setValue(s);
                    }
                });
    }


    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application mApplication;

        public Factory(@NonNull Application application) {
            mApplication = application;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new MainViewModel(mApplication);
        }
    }
}
