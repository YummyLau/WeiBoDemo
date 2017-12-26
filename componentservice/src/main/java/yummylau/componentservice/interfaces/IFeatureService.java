package yummylau.componentservice.interfaces;

import yummylau.componentlib.service.IService;

/**
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2017/12/11.
 */

public interface IFeatureService extends IService {

    String SERVICE_NAME = "/feature/service";

    String getMainPath();
}
