package yummylau.feature;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jaeger.library.StatusBarUtil;

import yummylau.common.activity.BaseActivity;
import yummylau.commonres.ColorGetter;

/**
 * Created by g8931 on 2017/11/14.
 */

@Route(path = Constants.ROUTER_MAIN)
public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feature_activity_main_layout);
    }

//    private void initView() {
//        setSupportActionBar(mBinding.toolbar);
//        mBinding.toolbar.setTitle("首页");
//        mBinding.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.colorTextIcon));
//        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this,
//                mBinding.drawerlayout,
//                mBinding.toolbar,
//                R.string.navigation_drawer_open,
//                R.string.navigation_drawer_close);
//        mBinding.drawerlayout.addDrawerListener(toggle);
//        toggle.syncState();
//    }

//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//
//        if (id == android.R.id.home) {
//            mBinding.drawerlayout.openDrawer(GravityCompat.START);//打开侧滑菜单
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

//    protected boolean supportHandlerStatusBar() {
//        return false;
//    }
//
//    @Override
//    public void setStatusBar() {
//        StatusBarUtil.setColorForDrawerLayout(this, mBinding.drawerlayout, ColorGetter.getStatusBarColor(this));
//    }


}
