package yummylau.feature.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import yummylau.feature.bean.User;

/**
 * 数据实体
 * Created by g8931 on 2017/11/29.
 */

public class MainViewModel extends ViewModel {


    private MutableLiveData<User> user;
    private MutableLiveData<String> mCurrentName;

    public MutableLiveData<User> getUser() {
        return user;
    }

    public MutableLiveData<String> getCurrentName(){
        if(mCurrentName == null){
            mCurrentName = new MutableLiveData<String>();
        }
        return mCurrentName;
    }
}
