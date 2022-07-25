package com.znn.demo.framework.aspectj.annotation;

import java.lang.annotation.*;

/**
 * @author 周闹闹
 * @version 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE}) // 只能给方法,和类、接口、枚举、注解注解
@Inherited() // 可以被继承的
public @interface TestAnnotation {
    int id() default 1;
    String key() default "";
    String value() default "";
}
