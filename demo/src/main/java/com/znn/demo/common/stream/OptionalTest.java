package com.znn.demo.common.stream;

import com.znn.demo.domain.entity.SysUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author 周闹闹
 * @version 1.0
 */
public class OptionalTest {
    public static void main(String[] args) {

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

        Optional<SysUser> sysUser = Optional.ofNullable(sysUsers.get(0));
        sysUser = Optional.ofNullable(null); // 可以传null，因为内部会判断是否为空，不为空才使用of方法
        // 当Optional的value为空则ifPresent方法不会执行
        sysUser.ifPresent(u -> System.out.println(u.getUserName()));


        // 确定肯定不为空
//        sysUser = Optional.of(null); // 不能传null
        sysUser = Optional.empty(); // 相当于使用了null的参数
        sysUser = Optional.ofNullable(sysUsers.get(0));
        sysUser.ifPresent(u -> System.out.println(u.getUserName()));

        m1(sysUser);
        sysUser = Optional.empty();
        m2(sysUser);
    }

    // 获取Optional内的 get,如果为空则会报错
    static void m1(Optional<SysUser> a){
        System.out.println(a.get());
    }

    // 获取Optional内的 orElseGet 获取的不是null就返回，是null就 new SysUser()
    static void m2(Optional<SysUser> a){
        System.out.println(a.orElseGet(() -> new SysUser()));
    }
}
