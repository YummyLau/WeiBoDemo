package yummylau.componentlib.service;

import android.app.Application;
import android.content.Context;

import com.alibaba.android.arouter.facade.template.IProvider;

/**
 * Created by g8931 on 2017/11/29.
 */

public interface IService extends IProvider {

    void createAsLibrary(Application application);
}
