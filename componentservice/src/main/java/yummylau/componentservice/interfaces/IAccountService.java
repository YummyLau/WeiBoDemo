package yummylau.componentservice.interfaces;

import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;
import yummylau.componentlib.service.IService;
import yummylau.componentservice.bean.Token;

/**
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2017/12/11.
 */

public interface IAccountService extends IService {

    String SERVICE_NAME = "/account/service";

    String getLoginPath();

    Flowable<Token> getToken();

    Consumer<Throwable> CreateConsumerThrowable(Consumer<Throwable> consumer);
}
