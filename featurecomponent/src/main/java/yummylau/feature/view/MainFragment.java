package yummylau.feature.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import yummylau.common.CommonViewPagerAdapter;
import yummylau.feature.R;
import yummylau.feature.databinding.FeatureFragmentMainLayoutBinding;

/**
 * 首页 fragment
 * Created by g8931 on 2017/12/4.
 */

public class MainFragment extends Fragment {

    private FeatureFragmentMainLayoutBinding mBinding;
    private StatusListWidget hotStatus;
    private StatusListWidget followStatus;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.feature_fragment_main_layout, null, false);
        initView();
        return mBinding.getRoot();
    }

    private void initView() {
        List<View> statusListViews = new ArrayList<>();
        hotStatus = new StatusListWidget(getContext());
        hotStatus.initView(StatusListWidget.HOT_TYPE);
        followStatus = new StatusListWidget(getContext());
        followStatus.initView(StatusListWidget.FOLLOW_TYPE);
        statusListViews.add(hotStatus);
        statusListViews.add(followStatus);
        List<String> statusListTabs = new ArrayList<>();
        statusListTabs.add(getContext().getString(R.string.feature_main_fragment_tab_hot));
        statusListTabs.add(getContext().getString(R.string.feature_main_fragment_tab_follow));
        CommonViewPagerAdapter<View> pagerAdapter = new CommonViewPagerAdapter<>(statusListViews, statusListTabs);
        mBinding.viewPager.setAdapter(pagerAdapter);
        mBinding.tabs.addTab(mBinding.tabs.newTab().setText(R.string.feature_main_fragment_tab_hot));
        mBinding.tabs.addTab(mBinding.tabs.newTab().setText(R.string.feature_main_fragment_tab_follow));
        mBinding.tabs.setupWithViewPager(mBinding.viewPager);
        mBinding.viewPager.setCurrentItem(0);
    }
}
