package yummylau.feature.view;

import android.arch.lifecycle.Observer;
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
    private static int requestTime = 0;

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
        initView();
        mBinding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HttpParam param = new HttpParam.Builder()
                        .converterFactory(ScalarsConverterFactory.create())
                        .baseUrl("http://star.pt.163.com/")
                        .build();
                HttpManager
                        .create(param, WeiboApis.class)
                        .getTest("http://star.pt.163.com/history/apps/xyq/client/users?uids=5a1c0827f628337afef50d01&uid=5a1c0827f628337afef50d01")
//                        .getTest("https://www.baidu.com/s?wd=a&rsv_spt=1&rsv_iqid=0xc05e669d00043ec1&issp=1&f=8&rsv_bp=0&rsv_idx=2&ie=utf-8&tn=baiduhome_pg&rsv_enter=1&rsv_sug3=3&rsv_sug1=2&rsv_sug7=100&rsv_sug2=0&inputT=689&rsv_sug4=766")
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Subscriber<String>() {

                            @Override
                            public void onStart() {
                                super.onStart();
                                requestTime = requestTime + 1;
                                Log.d(TAG, "请求次数： " + requestTime);
                            }

                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.d(TAG, e.getMessage());
                            }

                            @Override
                            public void onNext(String s) {
                                Log.d(TAG, "请求成功");
                            }
                        });

            }
        });
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


}
