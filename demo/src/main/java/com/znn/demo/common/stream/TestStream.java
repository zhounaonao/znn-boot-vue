package com.znn.demo.common.stream;

import com.znn.demo.domain.entity.SysUser;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author 周闹闹
 * @version 1.0
 */
public class TestStream {

    public static void main(String[] args) {
        // 转换为流
        List<String> list = new ArrayList();
        list.add("1");
        Stream<String> stream1 = list.stream();

        Integer[] arr = {1,2,3,4,2,4,0};
        Stream<Integer> stream = Arrays.stream(arr);
        Stream<Integer> stream2 = Stream.of(arr);
        stream2
                .distinct()
                .filter(integer -> integer > 2)
                .forEach(System.out::println);


        Map<String, Object> map = new HashMap<>();
        Stream<Map.Entry<String, Object>> stream3 = map.entrySet().stream();

        // 中间操作
        // 数据
        Long[] l1 = new Long[]{1L,3L};
        Long[] l2 = new Long[]{2L,5L};
        Long[] l3 = new Long[]{3L,9L};
        Long[] l4 = new Long[]{4L,11L};
        List<SysUser> sysUsers = new ArrayList<>();
        sysUsers.add(new SysUser(4L, "ff", l1));
        sysUsers.add(new SysUser(5L, "pp", l2));
        sysUsers.add(new SysUser(2L,"zz", l3));
        sysUsers.add(new SysUser(6L,"nn", l4));
        sysUsers.add(new SysUser(3L,"pp", l2));

        // filter() 符合条件保留
        // map() 流中的元素进行计算或类型转换
        sysUsers.stream()
                .map(sysUser -> sysUser.getUserName())
                .map(userName -> "流操作后:" + userName)
                .distinct()
                .forEach(s -> System.out.println(s));

        // flatMap,可以将一个流拆分成多个流
        System.out.println("====flatMap====");
        sysUsers.stream()
                .flatMap(sysUser -> Arrays.stream(sysUser.getPostIds()))
                .distinct()
                .forEach(aLong -> System.out.println(aLong));


        // flatMap 字符串分割

        // distinct 默认根据equals方法进行判断是否重复,未重写equals就会用Object的equals
        sysUsers.stream()
                .distinct()
                .forEach(s -> System.out.println(s));

        System.out.println("================================去重排序==================================");
        // 去重排序 sorted()如果是空参的话，需要对象实现Comparator接口，否则需要指定参数实现匿名内部类
        Stream<SysUser> stream4 = sysUsers.stream();
        stream4
                .distinct()
                .sorted((o1, o2) -> Integer.parseInt(String.valueOf(o1.getUserId() - o2.getUserId())))
                .forEach(sysUser -> System.out.println(sysUser));
        // 一个流只能被消费（终结操作）一次，不可复用
        // 不会修改原数据

//        stream4
//                .distinct()
//                .sorted((o1, o2) -> Integer.parseInt(String.valueOf(o2.getUserId() - o1.getUserId())))
//                .forEach(sysUser -> System.out.println(sysUser));

        // limit(2)，去重排序打印前两个
        sysUsers.stream()
                .distinct()
                .sorted((o1, o2) -> (int) (o2.getUserId() - o1.getUserId()))
                .limit(2)
                .forEach(sysUser -> System.out.println(sysUser));

        System.out.println("skip");
        // skip(n) 跳过流前n个元素
        sysUsers.stream()
                .distinct()
                .sorted((o1, o2) -> (int) (o2.getUserId() - o1.getUserId()))
                .skip(2)
                .forEach(sysUser -> System.out.println(sysUser));

        // 中结操作
        // forEach
        // count() 计数
        // max(),min()
        Optional<SysUser> max = sysUsers.stream()
                .max((o1, o2) -> (int) (o1.getUserId() - o2.getUserId()));
        Optional<SysUser> min = sysUsers.stream()
                .min((o1, o2) -> (int) (o1.getUserId() - o2.getUserId()));
        System.out.println(max);
        System.out.println(min);

        // collect 流转为集合
        // List  所有岗位的id
        List<Long> collect = sysUsers.stream()
                .flatMap(sysUser -> Arrays.stream(sysUser.getPostIds()))
                .distinct()
                .collect(Collectors.toList());
        System.out.println(collect);

        // Set
        Set<Long> set = sysUsers.stream()
                .flatMap(sysUser -> Arrays.stream(sysUser.getPostIds()))
                .collect(Collectors.toSet());
        System.out.println(set);

        // Map
        Map<Long, Long[]> map2 = sysUsers.stream()
                .distinct()
                .collect(Collectors.toMap(sysUser -> sysUser.getUserId(), sysUser -> sysUser.getPostIds()));
        System.out.println(map2);

        // anyMatch 判断是否有任意符合匹配条件的元素，返回boolean(有一个元素符合就true)
        boolean b = sysUsers.stream()
                .anyMatch(sysUser -> sysUser.getUserId() > 6L);
        System.out.println(b);

        // allMatch 判断是所有的元素是否匹配条件，返回boolean(所有元素都符合true)
        boolean b1 = sysUsers.stream()
                .allMatch(sysUser -> sysUser.getUserId() > 1L);
        System.out.println(b1);

        // noneMatch 都不符合返回true
        boolean b2 = sysUsers.stream()
                .noneMatch(sysUser -> sysUser.getUserId() > 6L);
        System.out.println(b2);

        // findAny 获取任意(随机)一个元素（少）
        Optional<SysUser> any = sysUsers.stream()
                .filter(sysUser -> sysUser.getUserId() > 6L)
                .findAny();

        // 如果有数据就会返回，如果是空不会执行
        any.ifPresent(sysUser -> System.out.println(sysUser.getUserName()));

        // findFirst 获取第一个
        Optional<SysUser> first = sysUsers.stream()
                .sorted((o1, o2) -> (int) (o1.getUserId() - o2.getUserId()))
                .findFirst();

        first.ifPresent(sysUser -> System.out.println(sysUser.getUserName()));

        // reduce 缩减，归并，计算出一个结果
        /*
        * reduce内部代码
        * T result = identity;
        * for (T element: this stream)
        *     result = accumulator.apply(result, element)
        * return result;
        * */
        // 累加 0L初始值，遍历相加
        Long sum = sysUsers.stream()
                .map(sysUser -> sysUser.getUserId())
                .reduce(0L, (aLong, aLong2) -> aLong + aLong2);
        System.out.println(sum);

        // 求最大值max,min是基于reduce的
        Long max1 = sysUsers.stream()
                .map(sysUser -> sysUser.getUserId())
                .reduce(Long.MIN_VALUE, (o1, o2) -> o1 > o2 ? o1 : o2);
        System.out.println(max1);

        // 求最小值max,min是基于reduce的
        Long min1 = sysUsers.stream()
                .map(sysUser -> sysUser.getUserId())
                .reduce(Long.MAX_VALUE, (o1, o2) -> o1 > o2 ? o2 : o1);
        System.out.println(min1);

        // reduce(只有一个参数的时候)
        /*
        * boolean foundAny = false;
        * T result = null;
        * for (T element : this stream) {
        *    if (!foundAny) {
        *        foundAny = true;
        *        result = element;
        *    }
        *    else
        *        result = accumulator.apply(result, element);
        * }
        * return foundAny ? Optional.of(result) : Optional.empty();
        *
        * 第一个参数作为初始化值identity
        * */

        // 求最大值max,min是基于reduce的
        Optional<Long> max2 = sysUsers.stream()
                .map(sysUser -> sysUser.getUserId())
                .reduce((o1, o2) -> o1 > o2 ? o1 : o2);
        max2.ifPresent(l -> System.out.println(l));

        // 求最小值max,min是基于reduce的
        Optional<Long> min2 = sysUsers.stream()
                .map(sysUser -> sysUser.getUserId())
                .reduce((o1, o2) -> o1 > o2 ? o2 : o1);
        min2.ifPresent(l -> System.out.println(l));

        // Optional 避免空指针异常

    }


}
