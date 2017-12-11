package yummylau.feature.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


import yummylau.common.activity.BaseFragment;
import yummylau.feature.R;
import yummylau.feature.databinding.FeatureFragmentMainLayoutBinding;
import yummylau.feature.data.local.db.entity.StatusEntity;
import yummylau.feature.videmodel.FollowedViewModel;
import yummylau.feature.view.adapter.StatusListAdapter;

/**
 * 首页 fragment
 * Created by g8931 on 2017/12/4.
 */

public class FollowedFragment extends BaseFragment<FollowedViewModel, FeatureFragmentMainLayoutBinding> {

    private LinearLayoutManager mLinearLayoutManager;
    private StatusListAdapter mStatusListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        dataBinding.setViewmodel(viewModel);
        initView();
        viewModel.getAllStatus().observe(this, new Observer<List<StatusEntity>>() {
            @Override
            public void onChanged(@Nullable List<StatusEntity> statusEntity) {
                if (statusEntity != null) {
                    mStatusListAdapter.addData(statusEntity);
                }
            }
        });
        return dataBinding.getRoot();
    }

    @Override
    public Class<FollowedViewModel> getViewModel() {
        return FollowedViewModel.class;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.feature_fragment_main_layout;
    }

    private void initView() {
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mStatusListAdapter = new StatusListAdapter(R.layout.feature_item_status_layout, null);
        mStatusListAdapter.openLoadAnimation(StatusListAdapter.SLIDEIN_LEFT);
        dataBinding.statusList.setLayoutManager(mLinearLayoutManager);
        dataBinding.swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewModel.start();
            }
        });
        dataBinding.statusList.setAdapter(mStatusListAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.start();
    }
}
