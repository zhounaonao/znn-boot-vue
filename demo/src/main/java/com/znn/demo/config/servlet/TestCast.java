package com.znn.demo.config.servlet;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 周闹闹
 * @version 1.0
 * testcast:
 *   book:
 *     id: ${random.int}           # 随机整数
 *     id2: ${random.int(10)}      # 10以内的整数
 * #    type: ${random.int!10,20!}  # 10-20的整数
 *     type: ${random.int(10,20)}  # 10-20的整数
 *     uuid: ${random.uuid}        # 随机uuid
 *     name: ${random.value}       # 随机字符串，md5字符串，32位
 *     publishTime: ${random.long} # 随机整数(long)
 */

@Component
@ConfigurationProperties("testcast.book")
public class TestCast {
    private Integer id;
    private Integer id2;
    private Integer type;
    private String uuid;
    private String name;
    private Long publishTime;

    @Override
    public String toString() {
        return "TestCast{" +
                "id=" + id +
                ", id2=" + id2 +
                ", type=" + type +
                ", uuid='" + uuid + '\'' +
                ", name='" + name + '\'' +
                ", publishTime=" + publishTime +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId2() {
        return id2;
    }

    public void setId2(Integer id2) {
        this.id2 = id2;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Long publishTime) {
        this.publishTime = publishTime;
    }
}
