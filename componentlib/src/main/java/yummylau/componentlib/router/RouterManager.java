package yummylau.componentlib.router;

import com.alibaba.android.arouter.launcher.ARouter;

import java.util.Map;

/**
 * 路由管理器
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2017/12/11.
 */

public class RouterManager {

    public static void navigation(String path) {
        ARouter.getInstance().build(path).navigation();
    }
}
