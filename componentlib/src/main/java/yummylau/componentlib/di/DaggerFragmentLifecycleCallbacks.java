package yummylau.componentlib.di;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

/**
 * Fragment 统一注入
 *
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2017/12/12.
 */

public class DaggerFragmentLifecycleCallbacks extends FragmentManager.FragmentLifecycleCallbacks {

    @Inject
    public DaggerFragmentLifecycleCallbacks() {
    }

    @Override
    public void onFragmentAttached(FragmentManager fm, Fragment f, Context context) {
        super.onFragmentAttached(fm, f, context);
        AndroidSupportInjection.inject(f);
    }
}
