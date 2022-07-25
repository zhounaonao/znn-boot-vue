package com.znn.demo.config.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 周闹闹
 * @version 1.0
 * yml中的配置
 * spring:
 *   redis:
 *     host: 192.168.32.129
 *     port: 6379
 *     database: 0
 */
@Component
/*
* 将配置信息导入该类
* 通过setter将配置信息导入该类的bean
* 当该属性没有setter方法时会报错
*
* 因为final属性不能变更值，且一开始就得赋值
* 所以肯定不会通过prefix的内容进行赋值，但是如果将该属性的setter方法修改为改其他属性，则其他属性会被赋值
*
* static可以通过prefix赋值
*
* @Value 不会赋值 static和final
*
* 案例：
* h和p没有通过yml赋值，但有参数，是经过了host和port的setter方法形成的
* MyRedis{host='local', port='127', database='0', fina=final', stat=static',
*  nor=znn', h=192.168.32.129', p=6379'}
* */
@ConfigurationProperties(prefix = "spring.redis")
public class MyRedis {
    private static final String host = "local";
    private final String port = "127";
    private static String database = "9";

    @Value("${token.JWT_KEY}")
    private final String fina = "final";

    @Value("${token.JWT_KEY}")
    private static String stat = "static";

    @Value("${token.JWT_KEY}")
    private String nor = "nor";

    private static String h;
    private static String p;

    @Override
    public String toString() {
        return "MyRedis{" +
                "host='" + host + '\'' +
                ", port='" + port + '\'' +
                ", database='" + database + '\'' +
                ", fina=" + fina + '\'' +
                ", stat=" + stat + '\'' +
                ", nor=" + nor + '\'' +
                ", h=" + h +'\'' +
                ", p=" + p + '\'' +
                '}';
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.h = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.p = port;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }
}
