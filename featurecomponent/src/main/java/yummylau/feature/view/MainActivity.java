package yummylau.feature.view;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jaeger.library.StatusBarUtil;

import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.Scheduler;
import rx.Subscriber;
import rx.schedulers.Schedulers;
import yummylau.common.activity.BaseActivity;
import yummylau.common.net.HttpManager;
import yummylau.common.net.HttpParam;
import yummylau.common.rx.RxUtils;
import yummylau.commonres.ColorGetter;
import yummylau.feature.Constants;
import yummylau.feature.R;
import yummylau.feature.api.WeiboApis;
import yummylau.feature.databinding.FeatureActivityMainLayoutBinding;
import yummylau.feature.viewmodel.MainViewModel;

/**
 * Created by g8931 on 2017/11/14.
 */

@Route(path = Constants.ROUTER_MAIN)
public class MainActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private FeatureActivityMainLayoutBinding mBinding;
    private MainViewModel mModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.feature_activity_main_layout);
        mModel = ViewModelProviders.of(this).get(MainViewModel.class);
        final Observer<String> nameObserver = new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Log.d(TAG, "onChanged-" + s);
            }
        };
        mModel.getCurrentName().observe(this, nameObserver);
        mBinding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mModel.getCurrentName().setValue("推送数据");
            }
        });
        initView();

        LiveData<Integer> obserable = new LiveData<Integer>() {
            @Override
            protected void onActive() {
                super.onActive();
                Log.d(TAG, "state-" + MainActivity.this.getLifecycle().getCurrentState());
                setValue(1);
            }

            @Override
            protected void onInactive() {
                Log.d(TAG, "state-" + MainActivity.this.getLifecycle().getCurrentState());
                setValue(0);
            }
        };
        obserable.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                Log.d(TAG, "自定义数据-" + integer);
            }
        });
        getLifecycle().addObserver(new Listener(this));
    }

    private void initView() {
        setSupportActionBar(mBinding.toolbar);
        mBinding.toolbar.setTitle("首页");
        mBinding.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.colorTextIcon));
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                mBinding.drawerLayout,
                mBinding.toolbar,
                R.string.feature_navigation_drawer_open,
                R.string.feature_navigation_drawer_close);
        mBinding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            mBinding.drawerLayout.openDrawer(GravityCompat.START);//打开侧滑菜单
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected boolean supportHandlerStatusBar() {
        return false;
    }

    @Override
    public void setStatusBar() {
        StatusBarUtil.setColorForDrawerLayout(this, mBinding.drawerLayout, ColorGetter.getStatusBarColor(this));
    }

    class Listener implements LifecycleObserver {

        LifecycleOwner lifecycleOwner;

        public Listener(LifecycleOwner lifecycleOwner) {
            this.lifecycleOwner = lifecycleOwner;
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
        void onCreate() {
            Log.d(TAG, "onCreate!");
            Log.d(TAG, "state-" + lifecycleOwner.getLifecycle().getCurrentState());
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_START)
        void onStart() {
            Log.d(TAG, "onStart!");
            Log.d(TAG, "state-" + lifecycleOwner.getLifecycle().getCurrentState());
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        void onPause() {
            Log.d(TAG, "onPause!");
            Log.d(TAG, "state-" + lifecycleOwner.getLifecycle().getCurrentState());
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
        void onStop() {
            Log.d(TAG, "onStop!");
            Log.d(TAG, "state-" + lifecycleOwner.getLifecycle().getCurrentState());
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        void onDestory() {
            Log.d(TAG, "onDestory!");
            Log.d(TAG, "state-" + lifecycleOwner.getLifecycle().getCurrentState());
        }

    }


}
