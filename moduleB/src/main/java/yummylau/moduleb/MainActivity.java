package yummylau.moduleb;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;

import yummylau.common.bus.CommonBizLogin.Bean;
import yummylau.common.bus.CommonBizLogin.ModuleAFuns;
import yummylau.common.activity.BaseActivity;
import yummylau.common.bus.EventbusUtils;
import yummylau.common.bus.FunctionBus;

/**
 * Created by g8931 on 2017/11/14.
 */

@Route(path = "/moduleb/MainActivity")
public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.moduleb_activity_main_layout);
        Toast.makeText(this, "B", Toast.LENGTH_LONG).show();
        ((TextView) findViewById(R.id.textview)).setText(
                FunctionBus.getFunction(ModuleAFuns.class).getModuleName());
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bean b = new Bean();
                b.name = "moduleBName";
                EventbusUtils.post(b);
            }
        });
    }

    @Override
    protected boolean supportHandlerStatusbar() {
        return true;
    }

    @Override
    public int getStatusbarColor() {
        return Color.GREEN;
    }
}
