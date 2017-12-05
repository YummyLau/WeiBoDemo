package yummylau.feature.videmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.support.annotation.NonNull;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;

import java.util.List;

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
import yummylau.feature.repository.MainFragmentRepository;
import yummylau.feature.repository.local.db.entity.StatusEntity;
import yummylau.feature.repository.local.db.entity.UserEntity;
import yummylau.feature.repository.remote.api.StatusResult;
import yummylau.feature.repository.remote.api.WeiboApis;

/**
 * Created by g8931 on 2017/12/5.
 */

public class MainFragmentModel extends AndroidViewModel {

    @Autowired(name = IAccountService.SERVICE_NAME)
    public IAccountService accountService;

    private MutableLiveData<List<StatusEntity>> mAllStatus;
    private MainFragmentRepository mRepository;
    private Context context;

    public MainFragmentModel(@NonNull Application application) {
        super(application);
        mRepository = new MainFragmentRepository();
        ARouter.getInstance().inject(this);
        mAllStatus = new MutableLiveData<>();
        context = application;
    }

    public MutableLiveData<List<StatusEntity>> getAllStatus() {
        return mAllStatus;
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
                .getAllStatus(accountService.getToken(context).accessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<StatusResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(StatusResult statusResult) {
                        if (statusResult != null && statusResult.statusList != null) {
                            mAllStatus.setValue(statusResult.statusList);
                        }
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
            return (T) new MainFragmentModel(mApplication);
        }
    }
}
