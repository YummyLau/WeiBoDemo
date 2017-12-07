package yummylau.componentservice.interfaces;

import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;
import yummylau.componentlib.service.IService;
import yummylau.componentservice.bean.Token;

/**
 * Created by g8931 on 2017/11/29.
 */

public interface IAccountService extends IService {

    String SERVICE_NAME = "/account/service";

    String getLoginPath();

    Flowable<Token> getToken();

    Flowable<Token> getToken(boolean checkInvalid);

    Consumer<Throwable> CreateConsumerThrowable(Consumer<Throwable> consumer);
}
