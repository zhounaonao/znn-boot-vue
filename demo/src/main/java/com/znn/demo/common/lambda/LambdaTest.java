package com.znn.demo.common.lambda;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

/**
 * @author 周闹闹
 * @version 1.0
 */
public class LambdaTest {

    public static void main(String[] args) {
        // 未用lambda表达式
        System.out.println(m1(new Compare() {
            @Override
            public int add(int i1, int i2) {
                return i1 + i2;
            }
        }));

        // 用lambda表达式（舍去接口和方法，只保留参数和方法体）
        System.out.println(m1((int i1, int i2) -> {
                return i1 - i2;
            }
        ));

        // 用lambda表达式省略规则，参数类型可以省略，方法体内只有一个参数的时候大括号，return分号都可以省略
        System.out.println(m1((i1,i2) -> i1 - i2));

        // 用lambda表达式省略规则，当参数只有一个的时候可以省略小括号
        System.out.println(m2(str -> Integer.valueOf(str), "12"));

        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("a", "v");
        stringStringHashMap.put("b", "f");
        stringStringHashMap.put("c", "e");
        Set<Map.Entry<String, String>> entries = stringStringHashMap.entrySet();
        Stream<Map.Entry<String, String>> stream = entries.stream();
        stream
                .distinct()
                .filter(entry -> entry.getKey().equals("a"))
                .forEach(entry -> System.out.println(entry.getValue()));

    }

    public static int m1(Compare c){
        return c.add(2, 3);
    }

    public static int m2(StrToInteger s, String str){
        return s.strToInteger(str);
    }

}
