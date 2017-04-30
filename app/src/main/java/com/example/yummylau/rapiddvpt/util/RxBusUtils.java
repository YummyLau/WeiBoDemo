package com.example.yummylau.rapiddvpt.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.SparseArray;
import android.view.View;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;
import rx.subscriptions.CompositeSubscription;

/**
 * 取代EventBus,总线工具
 * Created by yummyLau on 17-4-30
 * Email: yummyl.lau@gmail.com
 */

public class RxBusUtils {

    //映射关系
    private static SparseArray<CompositeSubscription> subscriptionLists;
    //生产者消费者
    private final Subject<Object, Object> bus;
    // PublishSubject只会把在订阅发生的时间点之后来自原始Observable的数据发射给观察者
    private RxBusUtils() {
        bus = new SerializedSubject<>(PublishSubject.create());
        subscriptionLists = new SparseArray<>();
    }

    //获取单例
    public static RxBusUtils getDefault() {
        return RxBusHolder.sInstance;
    }

    private static class RxBusHolder {
        private static final RxBusUtils sInstance = new RxBusUtils();
    }


    // 提供了一个新的事件
    public void post(Object o) {
        bus.onNext(o);
    }

    // 根据传递的 eventType 类型返回特定类型(eventType)的 被观察者
    public <T> Observable<T> toObservable(Class<T> eventType) {
        return bus.ofType(eventType);
    }

    // 封装默认订阅
    public <T> Subscription toDefaultObservable(Class<T> eventType, Action1<T> act) {
        return bus.ofType(eventType).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(act, RxUtils.emptyThrowable(eventType));
    }

    /**
     * Activity接收
     *
     * @param t
     * @param subscriptions
     * @param <T>
     */
    public <T extends Context> void register(T t, Subscription... subscriptions) {
        if (subscriptions != null && subscriptions.length > 0) {
            CompositeSubscription mCompositeSubscription = new CompositeSubscription();
            for (Subscription sub : subscriptions) {
                mCompositeSubscription.add(sub);
            }
            subscriptionLists.put(t.hashCode(), mCompositeSubscription);
        }
    }

    /**
     * Fragment接收
     *
     * @param t
     * @param subscriptions
     * @param <T>
     */
    @TargetApi(11)
    public <T extends android.support.v4.app.Fragment> void register(T t, Subscription... subscriptions) {
        if (subscriptions != null && subscriptions.length > 0) {
            CompositeSubscription mCompositeSubscription = new CompositeSubscription();
            for (Subscription sub : subscriptions) {
                mCompositeSubscription.add(sub);
            }
            subscriptionLists.put(t.hashCode(), mCompositeSubscription);
        }
    }

    /**
     * View接收
     *
     * @param t
     * @param subscriptions
     * @param <T>
     */
    @TargetApi(11)
    public <T extends View> void register(T t, Subscription... subscriptions) {
        if (subscriptions != null && subscriptions.length > 0) {
            CompositeSubscription mCompositeSubscription = new CompositeSubscription();
            for (Subscription sub : subscriptions) {
                mCompositeSubscription.add(sub);
            }
            subscriptionLists.put(t.hashCode(), mCompositeSubscription);
        }
    }


    /**
     * 防止内存泄漏
     *
     * @param t
     * @param <T>
     */
    public <T extends Object> void ungister(T t) {
        CompositeSubscription compositeSubscription = subscriptionLists.get(t.hashCode());
        if (compositeSubscription != null) {
            compositeSubscription.unsubscribe();
            subscriptionLists.remove(t.hashCode());
        }
    }
}
