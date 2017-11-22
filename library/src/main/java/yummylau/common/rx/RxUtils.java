package yummylau.common.rx;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.exceptions.Exceptions;

public class RxUtils {

    private RxUtils(){}

    public static Subscription postDelayed(@NonNull int delay, @NonNull final Runnable runnable) {
        return  Observable.timer(delay, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Long>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(Long aLong) {
                            try {
                                runnable.run();
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    });
    }


    public static <T> Observable<T> getObservable(Callable<T> callable) {
        return Observable.fromCallable(callable);
    }

    public static <T> T fromJson(String json, Type type) {
        if (TextUtils.isEmpty(json)) {
            throw Exceptions.propagate(new RuntimeException("RxUtils.fromJson: json is empty: " + type.toString()));
        }
        try {
            return new Gson().fromJson(json, type);
        } catch (Throwable t) {
            throw Exceptions.propagate(t);
        }
    }
}
