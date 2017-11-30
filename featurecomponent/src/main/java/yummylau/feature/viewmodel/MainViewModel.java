package yummylau.feature.viewmodel;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import yummylau.feature.bean.User;

/**
 * 数据实体
 * Created by g8931 on 2017/11/29.
 */

public class MainViewModel extends ViewModel {


    private MutableLiveData<User> user;
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

    public MutableLiveData<User> getUser() {
        return user;
    }

    public MutableLiveData<String> getCurrentName() {
        if (mCurrentName == null) {
            mCurrentName = new MutableLiveData<String>();
        }
        return mCurrentName;
    }
}
