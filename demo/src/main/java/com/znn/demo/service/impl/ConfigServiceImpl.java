package com.znn.demo.service.impl;

import com.znn.demo.common.utils.text.StringUtils;
import com.znn.demo.common.core.redis.RedisCache;
import com.znn.demo.common.utils.text.Convert;
import com.znn.demo.common.constant.Constants;
import com.znn.demo.dao.SysConfigDAO;
import com.znn.demo.domain.entity.SysConfig;
import com.znn.demo.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 周闹闹
 * @version 1.0
 */
@Service
public class ConfigServiceImpl implements ConfigService {


    @Autowired
    RedisCache redisCache;

    @Autowired
    SysConfigDAO sysConfigDAO;

    /**
     * 根据键名查询参数配置信息
     *
     * @param configKey 参数key
     * @return 参数键值
     *
     * configKey = "sys.account.captchaOnOff"
     */
    @Override
    public String selectConfigByKey(String configKey) {
        // 从redis数据库取出开关key是sys_config:sys.account.captchaOnOff
        String configValue = Convert.toStr(redisCache.getCacheObject(getCacheKey(configKey)));
        if (StringUtils.isNotEmpty(configValue)){
            return configValue;
        }

        // redis取出来是空的，从数据库取
        SysConfig config = new SysConfig();
        config.setConfigKey(configKey);
        // Mybatis查询数据库的参数
        SysConfig resConfig = sysConfigDAO.selectConfig(config);
        if (StringUtils.isNotNull(resConfig)){
            // 非空则写入redis数据库
            redisCache.setCacheObject(getCacheKey(configKey), resConfig.getConfigValue());
            return resConfig.getConfigValue();
        }

        return StringUtils.EMPTY;
    }

    /**
     * 获取验证码开关
     *
     * @return true开启，false关闭
     */
    @Override
    public boolean selectCaptchaOnOff() {
        // 从redis数据库取出开关，没有则从mysql中取
        String captchaOnOff = selectConfigByKey("sys.account.captchaOnOff");
        if (StringUtils.isEmpty(captchaOnOff)){
            // redis中不存在，且数据库中也不存在，则默认为开启
            return true;
        }
        // String 转 boolean
        return Convert.toBool(captchaOnOff);
    }

    /**
     * 设置cache key
     *
     * @param configKey 参数键
     * @return 缓存键key
     */
    private String getCacheKey(String configKey)
    {
        return Constants.SYS_CONFIG_KEY + configKey;
    }
}
