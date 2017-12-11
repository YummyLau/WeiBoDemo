package yummylau.common;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 公用viewpager
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2017/12/11.
 */

public class CommonViewPagerAdapter<T extends View> extends PagerAdapter {

    private List<T> mList;
    private List<String> mTabs;

    public CommonViewPagerAdapter(List<T> mList, List<String> tabs) {
        this.mList = mList;
        this.mTabs = tabs;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabs.get(position);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return object == view;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mList.get(position));
        return mList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mList.get(position));
    }

    public List<String> getTabs() {
        return mTabs;
    }
}

