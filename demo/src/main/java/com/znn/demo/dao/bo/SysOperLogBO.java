package com.znn.demo.dao.bo;

import com.znn.demo.domain.entity.SysOperLog;

/**
 * @author 周闹闹
 * @version 1.0
 */
public interface SysOperLogBO {

    /**
     * 新增操作日志
     *
     * @param operLog 操作日志对象
     */
    public void insertOperlog(SysOperLog operLog);
}
