package yummylau.feature.view;


import android.arch.core.util.Function;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import yummylau.common.activity.BaseActivity;
import yummylau.commonres.ColorGetter;
import yummylau.feature.Constants;
import yummylau.feature.R;
import yummylau.feature.databinding.FeatureActivityMainLayoutBinding;
import yummylau.feature.repository.local.db.entity.UserEntity;
import yummylau.feature.videmodel.MainViewModel;

/**
 * Created by g8931 on 2017/11/14.
 */

@Route(path = Constants.ROUTER_MAIN)
public class MainActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private FeatureActivityMainLayoutBinding mBinding;
    private MainViewModel mModel;

    private List<Fragment> mFragments;
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "activity-onCreate");
        Log.d(TAG, "");
        mBinding = DataBindingUtil.setContentView(this, R.layout.feature_activity_main_layout);
        mFragments = new ArrayList<>();
        mFragments.add(new MainFragment());
        mFragments.add(new MessageFragement());
        mFragmentManager = super.getSupportFragmentManager();
        mFragmentManager.beginTransaction().replace(R.id.content_frame, mFragments.get(0), null).commit();
        initView();
        initViewModel();
    }

    private void initViewModel() {

        mModel = ViewModelProviders.of(this).get(MainViewModel.class);

        //for test lifecycler
        final Observer<String> nameObserver = new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Log.d(TAG, "onChanged-" + s);
            }
        };


        mModel.getCurrentName().observe(this, nameObserver);
        mModel.getAfterName().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Log.d(TAG, "After: onChanged-" + s);
            }
        });
        //是懒加载，只有LiveData被返回的时候，才会进行转化处理
        Transformations.map(mModel.getCurrentName(), new Function<String, String>() {

            public String apply(String input) {
                return "经过转化的数据—" + input;
            }
        });

        mBinding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mModel.getCurrentName().setValue("推送数据");
            }
        });
        getLifecycle().addObserver(new Listener(this));

        //weibo demo start
        mModel.getUser().observe(this, new Observer<UserEntity>() {
            @Override
            public void onChanged(@Nullable UserEntity userEntity) {

            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "activity-onPause");
        Log.d(TAG, "");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "activity-onStart");
        Log.d(TAG, "");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "activity-onResume");
        Log.d(TAG, "");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "activity-onStop");
        Log.d(TAG, "");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "activity-onDestroy");
        Log.d(TAG, "");
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
        mBinding.navigationLayout.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nav_main) {
                    mFragmentManager.beginTransaction().replace(R.id.content_frame, mFragments.get(0), null).commit();
                } else if (item.getItemId() == R.id.nav_message) {
                    mFragmentManager.beginTransaction().replace(R.id.content_frame, mFragments.get(1), null).commit();
                }
                mBinding.drawerLayout.closeDrawers();
                return true;
            }
        });
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
            Log.d(TAG, "LifecycleObserver-onCreate!");
            Log.d(TAG, "state-" + lifecycleOwner.getLifecycle().getCurrentState()
                    + "     isActive-" + lifecycleOwner.getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED));
            mModel.getCurrentName().setValue("onCreate!");
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_START)
        void onStart() {
            Log.d(TAG, "LifecycleObserver-onStart!");
            Log.d(TAG, "state-" + lifecycleOwner.getLifecycle().getCurrentState()
                    + "     isActive-" + lifecycleOwner.getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED));
            mModel.getCurrentName().setValue("onStart!");
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
        void onResume() {
            Log.d(TAG, "LifecycleObserver-onResume!");
            Log.d(TAG, "state-" + lifecycleOwner.getLifecycle().getCurrentState()
                    + "     isActive-" + lifecycleOwner.getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED));
            mModel.getCurrentName().setValue("onResume!");
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        void onPause() {
            Log.d(TAG, "LifecycleObserver-onPause!");
            Log.d(TAG, "state-" + lifecycleOwner.getLifecycle().getCurrentState()
                    + "     isActive-" + lifecycleOwner.getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED));
            mModel.getCurrentName().setValue("onPause!");
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
        void onStop() {
            Log.d(TAG, "LifecycleObserver-onStop!");
            Log.d(TAG, "state-" + lifecycleOwner.getLifecycle().getCurrentState()
                    + "     isActive-" + lifecycleOwner.getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED));
            mModel.getCurrentName().setValue("onStop!");
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        void onDestory() {
            Log.d(TAG, "LifecycleObserver-onDestory!");
            Log.d(TAG, "state-" + lifecycleOwner.getLifecycle().getCurrentState()
                    + "     isActive-" + lifecycleOwner.getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED));
            mModel.getCurrentName().setValue("onDestory!");
        }
    }


}
