package com.znn.demo.framework.manager.factory;

import com.znn.demo.common.utils.ip.AddressUtils;
import com.znn.demo.common.utils.spring.SpringUtils;
import com.znn.demo.dao.bo.SysOperLogBO;
import com.znn.demo.domain.entity.SysOperLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.TimerTask;

/**
 * 异步工厂（产生任务用）
 * 
 * @author ruoyi
 */
public class AsyncFactory
{
    private static final Logger sys_user_logger = LoggerFactory.getLogger("sys-user");


    /**
     * 操作日志记录
     * 
     * @param operLog 操作日志信息
     * @return 任务task
     */
    public static TimerTask recordOper(final SysOperLog operLog)
    {
        return new TimerTask()
        {
            @Override
            public void run()
            {
                // 远程查询操作地点
                operLog.setOperLocation(AddressUtils.getRealAddressByIP(operLog.getOperIp()));
                SpringUtils.getBean(SysOperLogBO.class).insertOperlog(operLog);
            }
        };
    }
}
