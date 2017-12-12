package yummylau.componentservice.di;

import javax.inject.Singleton;

import dagger.Component;

/**
 *
 *  |---------------------------------
 *  |       BaseAppComponent          |
 *  |        ----------------         |
 *  |       |   AppModule   |         |
 *  |        ----------------         |
 *  |           ----------------      |
 *  |           |SingletonModule|     |
 *  |            ---------------      |
 *  |----------------------------------
 *
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2017/12/11.
 */
@Singleton
@Component(modules = {AppModule.class, SingletonModule.class})
public interface BaseAppComponent {

}
