package yummylau.componentlib.di;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @Scope 标识注解域
 * 如果没有使用该注解，则在注入对象之后丢失它
 * 否则则会保留实例，作用域本身的实现取决于注入器
 *
 * Email yummyl.lau@gmail.com
 * Created by yummylau on 2017/12/12.
 */

@Scope
@Retention(RUNTIME)
public @interface AppScope {
}
