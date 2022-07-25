package com.znn.demo.dao;

import com.znn.demo.domain.entity.SysOperLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 周闹闹
 * @version 1.0
 */
@Mapper
public interface SysOperLogDAO {

    /**
     * 新增操作日志
     *
     * @param operLog 操作日志对象
     */
    public void insertOperlog(SysOperLog operLog);


}
