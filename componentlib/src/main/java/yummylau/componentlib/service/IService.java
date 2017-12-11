package yummylau.componentlib.service;

import android.app.Application;
import android.content.Context;

import com.alibaba.android.arouter.facade.template.IProvider;

/**
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2017/12/11.
 */

public interface IService extends IProvider {

    void createAsLibrary(Application application);
}
