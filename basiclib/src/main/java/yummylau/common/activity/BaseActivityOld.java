package yummylau.common.activity;

import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import yummylau.common.systemui.StatusbarUtil;
import yummylau.commonres.ColorGetter;

/**
 * baseActivity
 * Created by g8931 on 2017/11/17.
 */

@Deprecated
public class BaseActivityOld extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        if (supportHandlerStatusBar()) {
            setStatusBar();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    protected boolean supportHandlerStatusBar() {
        return true;
    }

    @ColorInt
    public int getStatusBarColor() {
        return ColorGetter.getStatusBarColor(this);
    }

    public void setStatusBar() {
        StatusbarUtil.setStatusbarColor(this, getStatusBarColor());
    }
}
