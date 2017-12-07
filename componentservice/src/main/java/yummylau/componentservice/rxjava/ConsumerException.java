package yummylau.componentservice.rxjava;


import io.reactivex.functions.Consumer;
import yummylau.componentservice.exception.TokenInvalidException;

/**
 * Created by g8931 on 2017/12/7.
 */

public abstract class ConsumerException implements Consumer<Throwable> {

    @Override
    public void accept(Throwable throwable) throws Exception {
        if(throwable instanceof TokenInvalidException){

        }
    }

}
