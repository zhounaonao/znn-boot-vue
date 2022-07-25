package com.znn.demo.admin.controller.common;

import com.google.code.kaptcha.Producer;
import com.znn.demo.common.core.redis.RedisCache;
import com.znn.demo.common.utils.sign.Base64;
import com.znn.demo.common.utils.uuid.IdUtils;
import com.znn.demo.config.MainConfig;
import com.znn.demo.common.constant.Constants;
import com.znn.demo.domain.AjaxResult;
import com.znn.demo.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author 周闹闹
 * @version 1.0
 *
 * 生成验证码
 */
@RestController
public class CaptchaController {

    @Autowired
    ConfigService configService;

    @Autowired
    RedisCache redisCache;

    @Resource(name = "captchaProducer")
    Producer captchaProducer;

    @Resource(name = "captchaProducerMath")
    Producer captchaProducerMath;

    /**
     * 生成验证码
     */
    @GetMapping("/captchaImage")
    public AjaxResult getCode(){
        AjaxResult ajaxResult = AjaxResult.success();
        // 先从redis数据库取出验证码的开关
        boolean captchaOnOff = configService.selectCaptchaOnOff();

        // 保存验证码信息
        // 生成一个简易的UUID
        String uuid = IdUtils.simpleUUID();
        // 常量CAPTCHA_CODE_KEY = "captcha_codes:"是验证码的开头
        String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;

        String capStr = null, code = null;
        BufferedImage image = null;

        // 生成验证码
        //  /** 验证码类型 */
        //  private static String captchaType;
        //  # 验证码类型 math 数组计算 char 字符验证
        //  captchaType: math
        String captchaType = MainConfig.getCaptchaType();
        if ("math".equals(captchaType)){
            String capText = captchaProducerMath.createText();
            capStr = capText.substring(0, capText.indexOf("@"));
            code = capText.substring(capText.indexOf("@") + 1);
            image = captchaProducerMath.createImage(capStr);

        } else if ("char".equals(captchaType)){
            capStr = code = captchaProducer.createText();
            image = captchaProducer.createImage(capStr);
        }

        // 将验证正确的验证码保存到redis中
        redisCache.setCacheObject(verifyKey, code, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
        FastByteArrayOutputStream outputStream = new FastByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpg", outputStream);
        } catch (IOException e) {
            return AjaxResult.error(e.getMessage());
        }

        ajaxResult.put("uuid", uuid);
        ajaxResult.put("img", Base64.encode(outputStream.toByteArray()));

        return ajaxResult;
    }

}
