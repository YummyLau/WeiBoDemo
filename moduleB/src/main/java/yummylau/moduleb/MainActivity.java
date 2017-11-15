package yummylau.moduleb;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;

/**
 * Created by g8931 on 2017/11/14.
 */

@Route(path = "/moduleb/MainActivity")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.moduleb_activity_main_layout);
        Toast.makeText(this, "B", Toast.LENGTH_LONG).show();
    }
}
