package com.znn.demo.framework.aspectj.annotation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author 周闹闹
 * @version 1.0
 */

@TestAnnotation("dd")
public class Test {

    private int id;
    private String key;
    private String value;

    @TestFieldAnnotation(field = 7)
    private int field;

    @TestMethodAnnotation(method = 8)
    public void method(){

    }

    public static void main(String[] args) throws Exception {
        Class<?> testClass = Class.forName("com.znn.demo.framework.aspectj.annotation.Test");
        // 判断是否有该注解
        if (testClass.isAnnotationPresent(TestAnnotation.class)) {
            TestAnnotation annotation = testClass.getAnnotation(TestAnnotation.class);
            System.out.println(annotation.id());
            System.out.println(annotation.key());
            System.out.println(annotation.value());
        }
        Field field = testClass.getDeclaredField("field");
        field.setAccessible(true);
        if (field.isAnnotationPresent(TestFieldAnnotation.class)) {
            TestFieldAnnotation annotation = field.getAnnotation(TestFieldAnnotation.class);
            System.out.println(annotation.field());
        }
        Method method = testClass.getMethod("method");
        if (method.isAnnotationPresent(TestMethodAnnotation.class)){
            TestMethodAnnotation annotation = method.getAnnotation(TestMethodAnnotation.class);
            System.out.println(annotation.method());
        }


    }
}
