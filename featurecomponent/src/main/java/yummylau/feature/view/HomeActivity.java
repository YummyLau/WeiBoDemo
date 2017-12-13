package yummylau.feature.view;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
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

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import yummylau.common.activity.BaseActivity;
import yummylau.commonres.ColorGetter;
import yummylau.feature.Constants;
import yummylau.feature.data.Resource;
import yummylau.feature.data.local.db.AppDataBase;
import yummylau.feature.videmodel.HomeViewModel;
import yummylau.feature.databinding.FeatureActivityMainLayoutBinding;
import yummylau.feature.data.local.db.entity.UserEntity;
import yummylau.feature.R;

/**
 * Created by g8931 on 2017/11/14.
 */

@Route(path = Constants.ROUTER_MAIN)
public class HomeActivity extends BaseActivity<HomeViewModel, FeatureActivityMainLayoutBinding> {


    private List<Fragment> mFragments;
    private FragmentManager mFragmentManager;

    @Override
    public Class<HomeViewModel> getViewModel() {
        return HomeViewModel.class;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.feature_activity_main_layout;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragments = new ArrayList<>();
        mFragments.add(new FollowedFragment());
        mFragmentManager = super.getSupportFragmentManager();
        mFragmentManager.beginTransaction().replace(R.id.content_frame, mFragments.get(0), null).commit();
        initView();
        viewModel.getUser().observe(this, new Observer<Resource<UserEntity>>() {
            @Override
            public void onChanged(@Nullable Resource<UserEntity> userEntityResource) {
                if (userEntityResource.data != null) {
                    View view = dataBinding.navigationLayout.getHeaderView(0);
                    ((TextView) view.findViewById(R.id.nick)).setText(userEntityResource.data.name);
                    ((TextView) view.findViewById(R.id.status_tip)).setText(String.format(HomeActivity.this.getString(R.string.feature_weibo_count_tip), userEntityResource.data.statusesCount));
                    ((TextView) view.findViewById(R.id.follow_tip)).setText(String.format(HomeActivity.this.getString(R.string.feature_follows_count_tip), userEntityResource.data.friendsCount));
                    ((TextView) view.findViewById(R.id.fans_tip)).setText(String.format(HomeActivity.this.getString(R.string.feature_fans_count_tip), userEntityResource.data.followersCount));
                    Glide.with(HomeActivity.this)
                            .load(userEntityResource.data.profileImageUrl)
                            .into((ImageView) view.findViewById(R.id.avatar));
                }
            }
        });
        viewModel.initInfo();
    }


    private void initView() {
        setSupportActionBar(dataBinding.toolbar);
        dataBinding.toolbar.setTitle("首页");
        dataBinding.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.colorTextIcon));
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                dataBinding.drawerLayout,
                dataBinding.toolbar,
                R.string.feature_navigation_drawer_open,
                R.string.feature_navigation_drawer_close);
        dataBinding.drawerLayout.addDrawerListener(toggle);
        dataBinding.navigationLayout.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nav_main) {
                    mFragmentManager.beginTransaction().replace(R.id.content_frame, mFragments.get(0), null).commit();
                }
                dataBinding.drawerLayout.closeDrawers();
                return true;
            }
        });
        toggle.syncState();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            dataBinding.drawerLayout.openDrawer(GravityCompat.START);//打开侧滑菜单
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected boolean supportHandlerStatusBar() {
        return false;
    }

    @Override
    public void setStatusBar() {
        StatusBarUtil.setColorForDrawerLayout(this, dataBinding.drawerLayout, ColorGetter.getStatusBarColor(this));
    }
}
