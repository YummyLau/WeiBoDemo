package yummylau.networkmodule.router;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * 路由管理器
 * Created by g8931 on 2017/11/15.
 */

public class RouterManager {


    public static void navigation(String path) {
        ARouter.getInstance().build(path).navigation();
    }
}
