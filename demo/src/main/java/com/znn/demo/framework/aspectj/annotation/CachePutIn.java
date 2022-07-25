package com.znn.demo.framework.aspectj.annotation;

import java.lang.annotation.*;

/**
 * @author 周闹闹
 * @version 1.0
 */
@Retention(RetentionPolicy.RUNTIME) // 可以保留到程序运行的时候
@Target(ElementType.METHOD) // 只能给方法注解
@Inherited() // 可以被继承的
public @interface CachePutIn {
    int value() default 2;

}
