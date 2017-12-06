package yummylau.feature.view;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import yummylau.common.activity.BaseActivity;
import yummylau.commonres.ColorGetter;
import yummylau.feature.Constants;
import yummylau.feature.R;
import yummylau.feature.videmodel.ViewModelFactory;
import yummylau.feature.databinding.FeatureActivityMainLayoutBinding;
import yummylau.feature.data.local.db.entity.UserEntity;
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
        mFragmentManager = super.getSupportFragmentManager();
        mFragmentManager.beginTransaction().replace(R.id.content_frame, mFragments.get(0), null).commit();
        initView();
        mModel = obtainViewModel(this);
        mModel.getUser().observe(this, new Observer<UserEntity>() {
            @Override
            public void onChanged(@Nullable UserEntity userEntity) {
                if (userEntity != null) {
                    View view = mBinding.navigationLayout.getHeaderView(0);
                    ((TextView) view.findViewById(R.id.nick)).setText(userEntity.name);
                    ((TextView) view.findViewById(R.id.status_tip)).setText(String.format(MainActivity.this.getString(R.string.feature_weibo_count_tip), userEntity.statusesCount));
                    ((TextView) view.findViewById(R.id.follow_tip)).setText(String.format(MainActivity.this.getString(R.string.feature_follows_count_tip), userEntity.friendsCount));
                    ((TextView) view.findViewById(R.id.fans_tip)).setText(String.format(MainActivity.this.getString(R.string.feature_fans_count_tip), userEntity.followersCount));
                    Glide.with(MainActivity.this)
                            .load(userEntity.profileImageUrl)
                            .into((ImageView) view.findViewById(R.id.avatar));
                }
                // TODO: 2017/12/5 收到用户信息
            }
        });
        mModel.loadUserInfo();
    }


    public static MainViewModel obtainViewModel(FragmentActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        MainViewModel viewModel =
                ViewModelProviders.of(activity, factory).get(MainViewModel.class);
        return viewModel;
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
}
