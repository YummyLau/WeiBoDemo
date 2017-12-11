package yummylau.account.rxjava;

import io.reactivex.functions.Consumer;
import yummylau.account.Constants;
import yummylau.componentlib.router.RouterManager;
import yummylau.componentservice.exception.TokenInvalidException;

/**
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2017/12/11.
 */

public class ConsumerThrowable implements Consumer<Throwable> {

    private Consumer<Throwable> mConsumer;

    public ConsumerThrowable(Consumer<Throwable> consumer) {
        this.mConsumer = consumer;
    }

    @Override
    public void accept(Throwable throwable) throws Exception {
        if (throwable instanceof TokenInvalidException) {
            RouterManager.navigation(Constants.ROUTER_LOGIN);
        }
        mConsumer.accept(throwable);
    }
}
