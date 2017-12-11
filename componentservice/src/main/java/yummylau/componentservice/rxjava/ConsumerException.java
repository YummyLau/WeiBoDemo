package yummylau.componentservice.rxjava;


import io.reactivex.functions.Consumer;
import yummylau.componentservice.exception.TokenInvalidException;

/**
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2017/12/11.
 */

public abstract class ConsumerException implements Consumer<Throwable> {

    @Override
    public void accept(Throwable throwable) throws Exception {
        if(throwable instanceof TokenInvalidException){

        }
    }

}
