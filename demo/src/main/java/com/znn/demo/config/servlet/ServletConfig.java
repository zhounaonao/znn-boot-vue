package com.znn.demo.config.servlet;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DataSizeUnit;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.util.unit.DataSize;
import org.springframework.util.unit.DataUnit;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * @author 周闹闹
 * @version 1.0
 */
//@Component
@ConfigurationProperties(prefix = "servers")
// 开启对当前bean的属性注入校验
@Validated
public class ServletConfig {
    private String ipAddress;
    private String name;
    // 设置具体的规则
    @Max(value = 9999, message = "最大值不能超过9999")
    @Min(value = 202, message = "最小值不能低于202")
    private int port;
    private Long timeout;
    @DurationUnit(ChronoUnit.HOURS) // 时间单位,默认是毫秒
    private Duration serverTimeOut;
//    @DataSizeUnit(DataUnit.MEGABYTES) // 单位设置为MB // 默认B,配置文件内写单位后这句要注释
    private DataSize dataSize;

    public DataSize getDataSize() {
        return dataSize;
    }

    public void setDataSize(DataSize dataSize) {
        this.dataSize = dataSize;
    }

    public Duration getServerTimeOut() {
        return serverTimeOut;
    }

    public void setServerTimeOut(Duration serverTimeOut) {
        this.serverTimeOut = serverTimeOut;
    }

    @Override
    public String toString() {
        return "ServletConfig{" +
                "ipAddress='" + ipAddress + '\'' +
                ", name='" + name + '\'' +
                ", port='" + port + '\'' +
                ", timeout=" + timeout +
                ", serverTimeOut=" + serverTimeOut +
                ", dataSize=" + dataSize +
                '}';
    }

    public Long getTimeout() {
        return timeout;
    }

    public void setTimeout(Long timeout) {
        this.timeout = timeout;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
