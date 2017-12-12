package yummylau.componentlib.di;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.support.HasSupportFragmentInjector;

/**
 * activity统一注入
 * <p>
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2017/12/12.
 */

public class DaggerActivityLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {

    @Inject
    DaggerFragmentLifecycleCallbacks mFragmentLifecycleCallbacks;

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {
        AndroidInjection.inject(activity);
        if ((activity instanceof HasSupportFragmentInjector || activity.getApplication() instanceof HasSupportFragmentInjector)
                && activity instanceof FragmentActivity) {
            ((FragmentActivity) activity).getSupportFragmentManager()
                    .registerFragmentLifecycleCallbacks(mFragmentLifecycleCallbacks, true);
        }
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
