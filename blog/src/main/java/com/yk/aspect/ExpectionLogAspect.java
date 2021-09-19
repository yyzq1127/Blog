package com.yk.aspect;

import com.yk.annotation.OperationLogger;
import com.yk.entity.ExceptionLog;
import com.yk.service.ExceptionLogService;
import com.yk.utils.AopUtil;
import com.yk.utils.IpAddressUtil;
import com.yk.utils.JacksonUtil;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author yk
 * @version 1.0
 * @date 2021/4/2 19:37
 */

@Component
@Aspect
public class ExpectionLogAspect {

    @Resource
    private ExceptionLogService exceptionLogService;

    @Pointcut("execution(* com.yk.controller.*.*(..))")
    public void logPointCut(){}

    @AfterThrowing(value = "logPointCut()",throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Exception e){
        ExceptionLog log = handleLog(joinPoint,e);
        exceptionLogService.addExceptionLog(log);
    }


    /**
     * 配置ExceptionLog对象属性
     * @param joinPoint
     * @param e
     * @return
     */
    private ExceptionLog handleLog(JoinPoint joinPoint, Exception e) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        ExceptionLog exceptionLog = new ExceptionLog();

        HttpServletRequest request = attributes.getRequest();
        String uri = request.getRequestURI();
        exceptionLog.setUri(uri);
        String method = request.getMethod();
        exceptionLog.setMethod(method);
        String ip = IpAddressUtil.getIpAddress(request);
        exceptionLog.setIp(ip);
        String userAgent = request.getHeader("User-Agent");
        exceptionLog.setUserAgent(userAgent);
        String description = getDescriptionFromAnnotations(joinPoint);
        exceptionLog.setDescription(description);
        String error = getStackTrace(e);
        exceptionLog.setError(error);
//        System.out.println(e.toString());

        //根据AOP joinPoint 获取request参数<参数名,参数值>
        Map<String,Object> requestParams = AopUtil.getRequestParams(joinPoint);
        exceptionLog.setParam(requestParams.toString());
        return exceptionLog;
    }


    /**
     * 获取异常堆栈信息
     * @param e
     * @return
     */
    private String getStackTrace(Exception e) {
        StringWriter writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        e.printStackTrace(printWriter);
        return writer.toString();
    }

    /**
     * 根据注解获取description
     * @param joinPoint
     * @return
     */
    private String getDescriptionFromAnnotations(JoinPoint joinPoint) {
        String description = "";
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        OperationLogger operationLogger = method.getAnnotation(OperationLogger.class);
        if (operationLogger != null) {
            description = operationLogger.value();
            return description;
        }
        //VisitLogger visitLogger = method.getAnnotation(VisitLogger.class);
        /*if (visitLogger != null) {
            description = visitLogger.behavior();
            return description;
        }*/
        return description;
    }

}
