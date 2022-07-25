package com.znn;

import com.znn.demo.DemoApplication;
import com.znn.demo.dao.SysUserDAO;
import com.znn.demo.domain.entity.SysUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author 周闹闹
 * @version 1.0
 */
// 和上面的启动类不在同一个包或子包内，找不到启动类，需要定义classes指定启动类
// properties 加一个临时属性，会覆盖yml里面的
// args 参数可以为当前测试用例添加临时的，命令行参数
// args 和 properties 有同名的参数 args覆盖 properties
//@SpringBootTest(classes = DemoApplication.class, properties = {"baseDir=D:/win10"})
@SpringBootTest(classes = DemoApplication.class, properties = {"test.prop=testValue2"}, args = {"--test.prop=testValue2"})
public class Test1 {

    public static void main(String[] args) {
        // 普通定时任务
        quartz();
    }
    public static void quartz(){
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println("is running...");
            }
        };
        //1秒后执行任务task，每2秒重复一次
        timer.schedule(task, 1000, 2000);
    }

    @Autowired
    SysUserDAO sysUserDAO;

    @Value("${servers.ipAddress}")
    private String ipAddress;

    @Value("${dataSource.password}")
    private String password;

    @Value("${baseDir}")
    private String baseDir;

    @Value("${test.prop}")
    private String prop;

    @Test
    public void test1(){
        System.out.println(prop);
    }

    @Test
    public void test(){
        SysUser znn = sysUserDAO.selectByUserName("znn");
    }

}
