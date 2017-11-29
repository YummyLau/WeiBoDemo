package yummylau.componentservice.interfaces;


import yummylau.componentlib.service.IService;

/**
 * Created by g8931 on 2017/11/29.
 */

public interface IFeatureService extends IService {

    String SERVICE_NAME = "/feature/service";

    String getMainPath();
}
