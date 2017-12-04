package yummylau.feature.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import yummylau.feature.R;
import yummylau.feature.databinding.FeatureFragmentMainLayoutBinding;

/**
 * 首页 fragment
 * Created by g8931 on 2017/12/4.
 */

public class MainFragment extends Fragment {

    private FeatureFragmentMainLayoutBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.feature_fragment_main_layout, null, false);
        return mBinding.getRoot();
    }
}
