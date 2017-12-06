package yummylau.feature;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;

import yummylau.componentservice.interfaces.IFeatureService;

/**
 * Created by g8931 on 2017/11/29.
 */
@Route(path = IFeatureService.SERVICE_NAME)
public class FeatureServiceImpl implements IFeatureService {

    @Override
    public String getMainPath() {
        return Constants.ROUTER_MAIN;
    }

    @Override
    public void init(Context context) {
        Toast.makeText(context, "初始化功能模块", Toast.LENGTH_LONG).show();
    }

    @Override
    public void createAsLibrary(Application application) {
        //init database
//        DBManager.init(application);
    }
}
