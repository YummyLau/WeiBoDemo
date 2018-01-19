package yummylau.account.viewmodel;

import android.arch.lifecycle.ViewModel;


import javax.inject.Inject;

/**
 * 登录模块viewmodel
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2017/12/11.
 */

public class LoginViewModel extends ViewModel {

    public String name;

    public String getName() {
        return name;
    }

    @Inject
    public LoginViewModel() {
    }


    public void onClickToLogin() {

    }

    public void onClickToUpdate() {

    }

    public void onClickToLogout() {

    }

}
