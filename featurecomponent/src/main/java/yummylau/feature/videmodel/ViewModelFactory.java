package yummylau.feature.videmodel;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import yummylau.feature.data.FeatureRepository;
import yummylau.feature.data.local.LocalDataSource;
import yummylau.feature.data.remote.RemoteDataSource;
import yummylau.feature.data.local.db.AppDataBase;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by g8931 on 2017/12/6.
 */

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private static volatile ViewModelFactory INSTANCE;
    private final Application mApplication;
    private final FeatureRepository mFeatureRepository;

    private ViewModelFactory(Application application, FeatureRepository repository) {
        mApplication = application;
        mFeatureRepository = repository;
    }


    public static ViewModelFactory getInstance(Application application) {
        checkNotNull(application);
        if (INSTANCE == null) {
            synchronized (ViewModelFactory.class) {
                if (INSTANCE == null) {
                    AppDataBase appDataBase = AppDataBase.getInstance(application);
                    FeatureRepository featureRepository = FeatureRepository.getInstance(
                            RemoteDataSource.getInstance(application, appDataBase),
                            LocalDataSource.getInstance(appDataBase)
                    );
                    INSTANCE = new ViewModelFactory(application, featureRepository);
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(HomeViewModel.class)) {
            return (T) new HomeViewModel(mApplication, mFeatureRepository);
        } else if (modelClass.isAssignableFrom(FollowedViewModel.class)) {
            return (T) new FollowedViewModel(mApplication, mFeatureRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
