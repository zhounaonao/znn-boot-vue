package com.znn.demo.dao.bo.impl;

import com.znn.demo.dao.SysOperLogDAO;
import com.znn.demo.dao.bo.SysOperLogBO;
import com.znn.demo.domain.entity.SysOperLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 周闹闹
 * @version 1.0
 */
@Service
public class SysOperLogBOImpl implements SysOperLogBO {

    @Autowired
    SysOperLogDAO sysOperLogDAO;

    @Override
    public void insertOperlog(SysOperLog operLog) {
        sysOperLogDAO.insertOperlog(operLog);
    }
}
