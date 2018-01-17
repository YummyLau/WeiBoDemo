package yummylau.feature;

import android.app.Application;
import android.util.Log;

import yummylau.componentlib.component.IComponent;
import yummylau.componentservice.component.IFeatureComponent;
import yummylau.componentlib.service.ServiceManager;

/**
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2017/12/11.
 */
public class FeatureComponentImpl implements IFeatureComponent {

    @Override
    public void release() {

    }

    @Override
    public String getMainPath() {
        return Constants.ROUTER_MAIN;
    }


    @Override
    public void createAsLibrary(Application application) {
        //初始化时区
        Log.d(IComponent.class.getSimpleName(), "feature create as library...");
        Log.d(IComponent.class.getSimpleName(), "feature init timezone...");
    }

}
