package yummylau.componentservice.interfaces;

import android.content.Context;


import io.reactivex.Flowable;
import yummylau.componentlib.service.IService;
import yummylau.componentservice.bean.Token;

/**
 * Created by g8931 on 2017/11/29.
 */

public interface IAccountService extends IService {

    String SERVICE_NAME = "/account/service";

    String getLoginPath();

    Flowable<Token> getToken();

}
