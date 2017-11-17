package yummylau.modulea;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import yummylau.common.bus.CommonBizLogin.Bean;
import yummylau.common.bus.CommonBizLogin.ModuleBFuns;
import yummylau.common.activity.BaseActivity;
import yummylau.common.bus.EventbusUtils;
import yummylau.common.bus.FunctionBus;
import yummylau.common.router.RouterManager;

/**
 * Created by g8931 on 2017/11/14.
 */
@Route(path = "/modulea/MainActivity")
public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modulea_activity_main_layout);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RouterManager.navigation("/moduleb/MainActivity");
            }
        });
        ((TextView) findViewById(R.id.textview)).setText(
                FunctionBus.getFunction(ModuleBFuns.class).getModuleName());
        EventbusUtils.register(this);
    }

    @Override
    protected void onDestroy() {
        EventbusUtils.unRegister(this);
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Bean bean) {
        Toast.makeText(this, "modulea收到： bean: name" + bean.name, Toast.LENGTH_LONG).show();
    }


    @Override
    protected boolean supportHandlerStatusbar() {
        return true;
    }

    @Override
    public int getStatusbarColor() {
        return Color.RED;
    }
}
