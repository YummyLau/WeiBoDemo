package yummylau.componentservice.component;

import yummylau.componentlib.component.IComponent;

/**
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2017/12/11.
 */

public interface IFeatureComponent extends IComponent {

    String SERVICE_NAME = "/feature/service";

    String getMainPath();
}
