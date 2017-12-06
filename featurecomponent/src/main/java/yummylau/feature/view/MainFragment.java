package yummylau.feature.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import yummylau.common.CommonViewPagerAdapter;
import yummylau.feature.R;
import yummylau.feature.ViewModelFactory;
import yummylau.feature.databinding.FeatureFragmentMainLayoutBinding;
import yummylau.feature.repository.local.db.entity.StatusEntity;
import yummylau.feature.videmodel.MainFragmentModel;
import yummylau.feature.videmodel.MainViewModel;
import yummylau.feature.view.adapter.StatusListAdapter;

/**
 * 首页 fragment
 * Created by g8931 on 2017/12/4.
 */

public class MainFragment extends Fragment {

    private FeatureFragmentMainLayoutBinding mBinding;

    private MainFragmentModel mModel;

    private LinearLayoutManager mLinearLayoutManager;
    private StatusListAdapter mStatusListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.feature_fragment_main_layout, container, false);
        if (mBinding == null) {
            mBinding = FeatureFragmentMainLayoutBinding.bind(root);
        }
        initView();
        initViewModel();
        return mBinding.getRoot();
    }

    private void initView() {
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mStatusListAdapter = new StatusListAdapter(R.layout.feature_item_status_layout, null);
        mBinding.statusList.setLayoutManager(mLinearLayoutManager);
        mBinding.swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mModel.fetchData();
            }
        });
        mBinding.statusList.setAdapter(mStatusListAdapter);
    }

    private void initViewModel() {

        mModel = ViewModelProviders.of(this, ViewModelFactory.getInstance(getActivity().getApplication())).get(MainFragmentModel.class);

        mModel.getAllStatus().observe(this, new Observer<List<StatusEntity>>() {
            @Override
            public void onChanged(@Nullable List<StatusEntity> statusEntity) {
                if (statusEntity != null) {
                    mStatusListAdapter.addData(statusEntity);
                    mBinding.swipeLayout.setRefreshing(false);
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mModel.start();
    }
}
