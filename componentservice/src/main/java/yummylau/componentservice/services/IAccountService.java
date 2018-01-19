package yummylau.componentservice.services;

import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;
import yummylau.componentlib.component.IComponent;
import yummylau.componentlib.service.IService;
import yummylau.componentservice.bean.Token;

/**
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2017/12/11.
 */

public interface IAccountService extends IService {

    void login(String returnActivityPath);

    String getLoginPath();

    Flowable<Token> getToken();

    Consumer<Throwable> CreateConsumerThrowable(Consumer<Throwable> consumer);
}
