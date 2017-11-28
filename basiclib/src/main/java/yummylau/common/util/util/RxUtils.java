package yummylau.common.util.util;

import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;


import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.exceptions.Exceptions;
import rx.functions.Action1;

/**
 * rx 工具类
 * Created by yummyLau on 17-4-30
 * Email: yummyl.lau@gmail.com
 */
public class RxUtils {

    private static final boolean DEBUG = true;
    private static Action1<Throwable> sEmptyThrowable;          //默认错误处理
    private static HandlerThread sWorkerThread;                 //工作线程，用于执行数据库访问
    private static Scheduler sWorkScheduler;                    // worker线程的scheduler
    private static final String TAG = RxUtils.class.getSimpleName();

    /**
     * 初始化
     */
    public static void init() {
        sWorkerThread = new HandlerThread(TAG + ": work_thread");
        sWorkerThread.start();
        sWorkScheduler = AndroidSchedulers.from(sWorkerThread.getLooper());
    }

    /** 默认错误处理 */
    public static Action1<Throwable> emptyThrowable(String tag) {
        return new EmptyThrowable(tag);
    }

    /** 默认错误处理 */
    public static Action1<Throwable> emptyThrowable(Class clazz) {
        return emptyThrowable(clazz.toString());
    }

    /***
     * 工作线程，针对数据库和文件读写操作，网络请求占用io线程
     * @return
     */
    public static Scheduler workScheduler() {
        return sWorkScheduler;
    }

    /***
     * 创建Observable
     * @param source
     * @param <T>
     * @return
     */
    public static <T> Observable<T> getObservable(final Source<T> source) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                try {
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onNext(source.call());
                    }
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onCompleted();
                    }
                } catch (Exception e) {
                    subscriber.onError(Exceptions.propagate(e));
                }
            }
        });
    }

    /**
     * 确保运行在主线程断言
     */
    public static void assertMainThread() {
        if (Looper.getMainLooper().getThread() != Thread.currentThread()) {
            throw new IllegalStateException("Must be called on the main thread.");
        }
    }

    /**
     * 确保运行在工作线程断言
     */
    public static void assertWorkThread() {
        if (sWorkerThread != Thread.currentThread()) {
            throw new IllegalStateException("Must be called on the work thread.");
        }
    }

    public interface Source<T> {
        T call() throws Exception;
    }

    public static class EmptyThrowable implements Action1<Throwable> {

        public String tag;

        public EmptyThrowable(String tag) {
            this.tag = tag;
        }

        @Override
        public void call(Throwable throwable) {
            if (throwable == null) {
                throwable = new NullPointerException("onError called with null. Null values are generally not allowed in 2.x operators and sources.");
            }
            if (DEBUG) {
                Log.e(TAG, "call :" + throwable.toString());
            }
        }

        @Override
        public String toString() {
            return tag + "-EmptyThrowable";
        }
    }
}
