# RapidDvpt
>RapidDvpt项目持续集成
>采用谷歌 AppArchitecture 作为实践，借助微博api快速迭代客户端。

# App架构设计

## 技术选型
* 设计模式：MVVM
* 依赖注入：Dagger2
* 网络处理：Retrofit + Rxjava2
* 数据库管理：Room

## 项目基础结构
* 最上层-壳App，集成（component组件1，component组件2...）
    * 组件层-component（component组件xxx）
        * 组件服务层-componentservice（提供组件公用服务）
            * 组件依赖层-这部分是同底层lib抽离独立，专门处理组件间业务交互所提供的依赖工具
                * basiclib-基础sdk（包含第三方开源库+私有工具类）
                * basicres-基础资源（处理公用的基础资源）

## 模块内设计
* [Architecture Components](https://developer.android.com/topic/libraries/architecture/guide.html)

* [Data Binding with Android Architecture Components Preview](http://bytes.schibsted.com/data-binding-android-architecture-components-preview/)


**优秀文章链接**
* [LiveData（官方）](https://developer.android.com/topic/libraries/architecture/livedata.html)
* [ViewModels and LiveData: Patterns + AntiPatterns](https://medium.com/google-developers/viewmodels-and-livedata-patterns-antipatterns-21efaef74a54)
* [Room-Rxjava](https://medium.com/google-developers/room-rxjava-acb0cd4f3757)

* [Dagger2（官方）](https://google.github.io/dagger/users-guide.html)
* [Android and Dagger 2.10 AndroidInjector](https://android.jlelse.eu/android-and-dagger-2-10-androidinjector-5e9c523679a3)
* [Dagger2进阶必备技能](http://talentprince.github.io/2017/09/30/Advanced-Dagger2-Skills/)
* [Dependency injection with Dagger 2 - Custom scopes](http://frogermcs.github.io/dependency-injection-with-dagger-2-custom-scopes/)
* [Dependency injection with Dagger 2 - the API](http://frogermcs.github.io/dependency-injection-with-dagger-2-the-api/)

* [DataBinding实现原理探析](http://www.jianshu.com/p/de4d50b88437)
* [DataBinding难点解析之Observable和BindingAdapter（一）](http://www.jianshu.com/p/7c8b484cda91)
* [DataBinding难点解析之Observable和BindingAdapter（二）](http://www.jianshu.com/p/686bfc58bbb0)
* [DataBinding使用教程（三）：各个注解详解](http://blog.csdn.net/qiang_xi/article/details/75379321)


* [如何快速做Android MD风格的APP？](https://zhuanlan.zhihu.com/p/20870983)
* [android-material-design-icon-generator-plugin](https://github.com/konifar/android-material-design-icon-generator-plugin)
* [MDStudySamples](https://github.com/Mike-bel/MDStudySamples)
* [MaterialDesignLibrary](https://github.com/navasmdc/MaterialDesignLibrary)
* [MaterialViewPager](https://github.com/florent37/MaterialViewPager)
* [无设计开发漂亮App](https://www.race604.com/develpor-beautiful-app-without-ps/)
* [卡片式布局 MD风格设计 卡片式背景](http://blog.csdn.net/ygilove/article/details/57077745)
* [Android 使用CardView轻松实现卡片式设计](http://yifeng.studio/2016/10/18/android-cardview/)

* [material图标在线](https://www.materialpalette.com/icons)
* [material配色工具](https://www.materialui.co/colors)
* [iconfont](http://www.iconfont.cn/)
* [material](https://material.io/)





* [参考开源项目1-AndroidArchitecture](https://github.com/iammert/AndroidArchitecture)




## 模块化设计
1. 第三方依赖统一管理
配置dependencies.gradle，里面统一配置依赖版本及公用变量定义。
2. 模块资源命名冲突
在每一个子模块的build.gradle加上对应资源文件标识符resourcePrefix "xxx_"前缀，建议xxx以模块名命名即可。
3. App模块通过buildWithOtherModule动态build其他模块，module模块通过buildModule打开是否作为module使用。
4. 开源库的代码隔离设计（待补充）
5. DI管理（待补充）
    * Dagger2多module实践
    * DaggerComponent为主壳app注入
    * AppComponent为主module注入


# 打包构建
> 构建环境 ubuntu14 + Jenkins
> 软件版本 JDK1.8 + Gradle4.1 + Androidsdk
> 使用Gradle脚本打包githubs项目，打包成功之后Email通知
1. 下载[jenkinis.war](https://jenkins.io/download/)包，```java -jar jenkins.war --httpPort=<yourPort>```指定端口运行Jenkins。
2. 下载[JDK1.8](http://www.oracle.com/technetwork/cn/java/javase/downloads/jdk8-downloads-2133151-zhs.html),[Gradle](https://services.gradle.org/distributions/),[Androidsdk](https://developer.android.com/studio/command-line/sdkmanager.html),配置环境变量。
    打开编辑配置文件
    ```
    vim /etc/profile
    ```
    添加一下变量到配置文件
    >#set gradle
    >export GRADLE_HOME=/root/workspace/gradle/gradle-4.1
    >export PATH=${PATH}:${GRADLE_HOME}/bin
    >#set androidsdk
    >export ANDROID_HOME=/root/workspace/android/sdk/android-sdk
    >export PATH=${PATH}:${ANDROID_HOME}/tools
    >export PATH=${PATH}:${ANDROID_HOME}/platforms-tools
    >#set jdk
    >export JAVA_HOME=/root/workspace/java/jdk1.8.0_151
    >export CLASSPATH=.:$JAVA_HOME/lib/tools.jar
    >export PATH=$JAVA_HOME/bin:$PATH
    当前会话配置生效
    ```
    source /etc/profile
    ```

**文章参考**
[Ubuntu jenkins搭建](https://wiki.jenkins.io/display/JENKINS/Installing+Jenkins+on+Ubuntu)
[Ubuntu下jenkins+gradle+Android自动打包](https://www.jianshu.com/p/eb712145cf50)
[编译时替换资源 - Android重叠包与资源合并一见](http://blog.zhaiyifan.cn/2016/02/18/android-resource-overlay/)
[Android Studio Gradle实践之多渠道自动化打包+版本号管理](http://unclechen.github.io/2015/10/22/Android%20Studio%20Gradle%E5%AE%9E%E8%B7%B5%E4%B9%8B%E5%A4%9A%E6%B8%A0%E9%81%93%E8%87%AA%E5%8A%A8%E5%8C%96%E6%89%93%E5%8C%85+%E7%89%88%E6%9C%AC%E5%8F%B7%E7%AE%A1%E7%90%86/)

九宫格
https://jaeger.itscoder.com/android/2016/03/06/nine-grid-iamge-view-libaray.html

Storing Java objects other than primitive types in Room Database
https://medium.com/@amit.bhandari/storing-java-objects-other-than-primitive-types-in-room-database-11e45f4f6d22

http://www.open-open.com/jsoup/