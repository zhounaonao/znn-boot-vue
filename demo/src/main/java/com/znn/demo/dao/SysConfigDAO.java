package com.znn.demo.dao;

import com.znn.demo.domain.entity.SysConfig;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 周闹闹
 * @version 1.0
 */
@Mapper
public interface SysConfigDAO {

    SysConfig selectConfig(SysConfig sysConfig);
}
