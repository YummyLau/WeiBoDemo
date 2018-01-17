package yummylau.componentlib.component;

import android.app.Application;
import android.content.Context;

import com.alibaba.android.arouter.facade.template.IProvider;

/**
 * 基础组件实现接口
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2017/12/11.
 */

public interface IComponent {

    void createAsLibrary(Application application);

    void release();
}



