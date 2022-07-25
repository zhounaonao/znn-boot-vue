package com.znn.demo.framework.aspectj.annotation;

import com.znn.demo.framework.aspectj.aop.LogAspectj;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 周闹闹
 * @version 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(LogAspectj.class) // 开关的关键
public @interface EnableLogging {

}
