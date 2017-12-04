package yummylau.feature.videmodel;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import yummylau.feature.repository.UserRepository;
import yummylau.feature.repository.local.db.DBManager;
import yummylau.feature.repository.local.db.entity.UserEntity;


/**
 * 数据实体
 * Created by g8931 on 2017/11/29.
 */

public class MainViewModel extends ViewModel {

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


    private UserRepository mRepository;
    private MutableLiveData<UserEntity> ownUserInfo;

    public MutableLiveData<UserEntity> getUser() {
        if (ownUserInfo == null) {
            ownUserInfo = new MutableLiveData<>();
            DBManager.getInstance().userDao().getUsers();
        }
        return ownUserInfo;
    }
}
