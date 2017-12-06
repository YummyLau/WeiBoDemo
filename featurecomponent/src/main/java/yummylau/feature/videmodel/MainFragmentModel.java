package yummylau.feature.videmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;

import java.util.List;

import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import yummylau.feature.repository.FeatureRepository;
import yummylau.feature.repository.local.db.entity.StatusEntity;
import yummylau.feature.repository.remote.api.StatusResult;

/**
 * Created by g8931 on 2017/12/5.
 */

public class MainFragmentModel extends AndroidViewModel {


    private MutableLiveData<List<StatusEntity>> mAllStatus;
    private FeatureRepository mRepository;


    public final ObservableBoolean dataLoading = new ObservableBoolean(false);
    public final ObservableBoolean error = new ObservableBoolean(false);


    public MainFragmentModel(@NonNull Application application, FeatureRepository featureRepository) {
        super(application);
        mRepository = featureRepository;
        mAllStatus = new MutableLiveData<>();
    }

    public MutableLiveData<List<StatusEntity>> getAllStatus() {
        return mAllStatus;
    }

    public void start() {
        fetchData();
    }

    public void fetchData() {
        dataLoading.set(true);
        mRepository.getAllStatus()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
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
