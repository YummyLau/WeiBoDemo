package yummylau.feature.view;

import android.arch.lifecycle.Observer;
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
import yummylau.feature.data.Resource;
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
    public StatusListAdapter statusListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        //设置viewmodel
        dataBinding.setViewmodel(viewModel);
        initView();
        viewModel.getAllStatus().observe(this, new Observer<Resource<List<StatusEntity>>>() {
            @Override
            public void onChanged(@Nullable Resource<List<StatusEntity>> listResource) {
                if (listResource.data != null) {
                    statusListAdapter.addData(listResource.data);
                }
                dataBinding.swipeLayout.setRefreshing(listResource.loading());
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
        statusListAdapter = new StatusListAdapter(R.layout.feature_item_status_layout, null);
        statusListAdapter.openLoadAnimation(StatusListAdapter.SLIDEIN_LEFT);
        dataBinding.statusList.setLayoutManager(mLinearLayoutManager);
        dataBinding.swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewModel.refresh();
            }
        });
        dataBinding.statusList.setAdapter(statusListAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
//        viewModel.refresh();
    }
}
