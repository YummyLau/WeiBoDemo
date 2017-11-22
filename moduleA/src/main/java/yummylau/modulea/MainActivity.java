package yummylau.modulea;

import android.arch.persistence.room.Room;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Actions;
import yummylau.common.bus.CommonBizLogin.Bean;
import yummylau.common.activity.BaseActivity;
import yummylau.common.bus.EventbusUtils;
import yummylau.common.router.RouterManager;
import yummylau.common.util.DbUtil;
import yummylau.common.util.FileUtils;
import yummylau.modulea.bean.Address;
import yummylau.modulea.bean.User;
import yummylau.modulea.db.AppDataBase;

/**
 * Created by g8931 on 2017/11/14.
 */
@Route(path = "/modulea/MainActivity")
public class MainActivity extends BaseActivity {

    private AppDataBase dataBase;
    private TextView mTextView;
    private TextView mLogView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modulea_activity_main_layout);
        initView();
        EventbusUtils.register(this);
    }

    private void initView() {
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RouterManager.navigation("/moduleb/MainActivity");
            }
        });
        mTextView = findViewById(R.id.textview);
        mLogView = findViewById(R.id.log_txt);
//        mTextView.setText(
//                FunctionBus.getFunction(ModuleBFuns.class).getModuleName());
        dataBase = Room.databaseBuilder(getApplicationContext(), AppDataBase.class, "myDb").build();

        new Thread(new Runnable() {
            @Override
            public void run() {
                User user = new User();
                user.id = 0;
                user.age = 18;
                user.name = "name0";
                Address address = new Address();
                address.city = "广东";
                address.street = "天河";
                address.state = "科韵路";
                user.address = address;
                dataBase.userDao().insertUser(user);
            }
        }).start();

        findViewById(R.id.dbout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbUtil.exportDatabase(MainActivity.this.getApplicationContext());
            }
        });
    }

    @Override
    protected void onDestroy() {
        EventbusUtils.unRegister(this);
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Bean bean) {
        StringBuilder builder = new StringBuilder(mLogView.getText().toString());
        builder.append("modulea收到： bean: name" + bean.name);
        mLogView.setText(builder.toString());
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
