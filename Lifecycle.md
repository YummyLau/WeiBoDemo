# 源码解读

## Lifecycle
```

```

## LifecycleOwner
```
//用于获取Lifecycle对象
@NonNull
Lifecycle getLifecycle();
```

1. 实现LifecycleObserver接口，可使用
     @OnLifecycleEvent(Lifecycle.Event.ON_CREATE) 注解同步生命周期，自动回调；
2. LiveData onActive#setValue有效，onInactive#setValue无效
3. 在不同生命周期中setValue，会缓存LiveData数据，直到onActive回调。
4.

        INITIALIZED
onCreate
        CREATED         onInactive
onStart
        STARTED         onActive
onResume
        RESUMED         onActive
        STARTED         onActive
onPause
        CREATED         onInactive
onStop
        DESOTORYED      onInactive
onDestory

这件事为啥能说明文档描述的内容。



