package com.znn.demo.framework.aspectj.aop;

import com.alibaba.fastjson.JSON;
import com.znn.demo.common.enums.BusinessStatus;
import com.znn.demo.common.enums.HttpMethod;
import com.znn.demo.common.utils.ServletUtils;
import com.znn.demo.common.utils.ip.IpUtils;
import com.znn.demo.common.utils.security.SecurityUtils;
import com.znn.demo.common.utils.text.StringUtils;
import com.znn.demo.domain.entity.SysOperLog;
import com.znn.demo.domain.model.LoginUser;
import com.znn.demo.framework.aspectj.annotation.Log;
import com.znn.demo.framework.manager.AsyncManager;
import com.znn.demo.framework.manager.factory.AsyncFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Map;

/**
 * @author 周闹闹
 * @version 1.0
 *
 * 实现Log注解的作用
 */

@Aspect
// @Component // 需要注释组件，因为有日志开关，开关开了这个才有用
public class LogAspectj {

    private static final Logger log = LoggerFactory.getLogger(LogAspectj.class);

    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "@annotation(controllerLog)", returning = "jsonResult")
    public void test(JoinPoint joinPoint, Log controllerLog, Object jsonResult) {

        handleLog(joinPoint, controllerLog, null, jsonResult);
    }

    private void handleLog(final JoinPoint joinPoint, Log controllerLog, final Exception e, Object jsonResult) {
        try {
            // 获取当前用户
            LoginUser loginUser = SecurityUtils.getLoginUser();

            // *========数据库日志=========*//
            SysOperLog operLog = new SysOperLog();
            // 状态为成功的序号，从0开始
            operLog.setStatus(BusinessStatus.SUCCESS.ordinal());
            // 请求的地址
            String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
            operLog.setOperIp(ip);
            operLog.setOperUrl(ServletUtils.getRequest().getRequestURI());
            if (loginUser != null){
                operLog.setOperName(loginUser.getUsername());
            }
            if (e != null){
                operLog.setStatus(BusinessStatus.FAIL.ordinal());
                operLog.setErrorMsg(StringUtils.substring(e.getMessage(), 0, 2000));
            }
            // 设置方法名称
            // 全类名
            String className = joinPoint.getTarget().getClass().getName();
            // 方法
            String methodName = joinPoint.getSignature().getName();
            operLog.setMethod(className + "." + methodName + "()");
            // 设置请求方式
            operLog.setRequestMethod(ServletUtils.getRequest().getMethod());
            // 处理设置注解上的参数
            getControllerMethodDescription(joinPoint, controllerLog, operLog, jsonResult);
            // 保存数据库(取出单例模式（饿汉式，加载该类的时候就加载好了）执行异步任务，)
            AsyncManager.me().execute(AsyncFactory.recordOper(operLog));
        } catch (Exception exp) {
            // 记录本地异常日志
            log.error("==前置通知异常==");
            log.error("异常信息:{}", exp.getMessage());
            exp.printStackTrace();
        }
    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     *
     * @param log 日志
     * @param operLog 操作日志
     * @throws Exception
     */
    private void getControllerMethodDescription(JoinPoint joinPoint, Log log, SysOperLog operLog, Object jsonResult) {
        // 设置action动作，注解log的businessType参数，取的是BusinessType枚举类的第几个枚举，默认是OTHER（0）
        operLog.setBusinessType(log.businessType().ordinal());
        operLog.setTitle(log.title());
        // 设置操作人类别，注解log的operatorType参数，取的是OperatorType枚举类的第几个枚举，默认是MANAGE（1）
        operLog.setOperatorType(log.operatorType().ordinal());
        // 是否需要保存request，参数和值，默认true
        if (log.isSaveRequestData()) {
            // 获取参数的信息，传入到数据库中
            setRequestValue(joinPoint, operLog);
        }
        // 是否需要保存response，参数和值，默认true
        if (log.isSaveResponseData() && StringUtils.isNotNull(jsonResult)){
            operLog.setJsonResult(StringUtils.substring(JSON.toJSONString(jsonResult), 0, 2000));
        }
    }

    /**
     * 获取请求的参数，放到log中
     *
     * @param operLog 操作日志
     * @throws Exception 异常
     */
    private void setRequestValue(JoinPoint joinPoint, SysOperLog operLog) {

        String requestMethod = operLog.getRequestMethod();
        if (HttpMethod.PUT.name().equals(requestMethod) || HttpMethod.POST.name().equals(requestMethod)){
            String params = argsArrayToString(joinPoint.getArgs());
            operLog.setOperParam(StringUtils.substring(params, 0, 2000));
        } else {
            Map<?, ?> paramsMap = (Map<?, ?>) ServletUtils.getRequest().getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            operLog.setOperParam(StringUtils.substring(paramsMap.toString(), 0, 2000));
        }
    }

    /**
     * 参数拼装
     */
    private String argsArrayToString(Object[] paramsArray) {
        String params = "";
        if (paramsArray != null && paramsArray.length > 0) {
            for (Object o : paramsArray) {
                if (StringUtils.isNotNull(o) && !isFilterObject(o)){
                    try{
                        Object jsonObj = JSON.toJSON(o);
                        params += jsonObj.toString() + " ";
                    }catch (Exception e){
                    }
                }
            }
        }
        return params.trim();
    }

    /**
     * 判断是否需要过滤的对象。
     *
     * @param o 对象信息。
     * @return 如果是需要过滤的对象，则返回true；否则返回false。
     */
    @SuppressWarnings("rawtypes")
    private boolean isFilterObject(Object o) {
        Class<?> clazz = o.getClass();
        if (clazz.isArray())
        {
            return clazz.getComponentType().isAssignableFrom(MultipartFile.class);
        }
        else if (Collection.class.isAssignableFrom(clazz))
        {
            Collection collection = (Collection) o;
            for (Object value : collection)
            {
                return value instanceof MultipartFile;
            }
        }
        else if (Map.class.isAssignableFrom(clazz))
        {
            Map map = (Map) o;
            for (Object value : map.entrySet())
            {
                Map.Entry entry = (Map.Entry) value;
                return entry.getValue() instanceof MultipartFile;
            }
        }
        return o instanceof MultipartFile || o instanceof HttpServletRequest || o instanceof HttpServletResponse
                || o instanceof BindingResult;
    }


}
