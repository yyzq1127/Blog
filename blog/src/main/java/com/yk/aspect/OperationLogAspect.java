package com.yk.aspect;

import com.yk.annotation.OperationLogger;
import com.yk.entity.OperationLog;
import com.yk.service.OperationLogService;
import com.yk.utils.IpAddressUtil;
import com.yk.utils.JwtUtil;
import com.yk.utils.AopUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

/**
 * @author yk
 * @version 1.0
 * @date 2021/5/30 16:21
 */
@Aspect
@Component
public class OperationLogAspect {

    @Resource
    private OperationLogService operationLogService;

    @Pointcut("@annotation(operationLogger)")
    public void pointCut(OperationLogger operationLogger){
    }

    //配置环绕通知
    @Around("pointCut(operationLogger)")
    public Object around(ProceedingJoinPoint joinPoint, OperationLogger operationLogger) throws Throwable {
        int before = (int) System.currentTimeMillis();
        Object result = joinPoint.proceed();
        //得到执行时间
        int times = (int) System.currentTimeMillis()-before;
        OperationLog operationLog = handleLog(joinPoint,times,operationLogger);
        operationLogService.addOperationLog(operationLog);
        return result;
    }

    private OperationLog handleLog(ProceedingJoinPoint joinPoint, int times,OperationLogger operationLogger) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        OperationLog log = new OperationLog();

        String username = JwtUtil.getTokenBody(request.getHeader("Authorization")).getSubject();
        log.setUsername(username);
        String uri = request.getRequestURI();
        log.setUri(uri);
        String method = request.getMethod();
        log.setMethod(method);
        String description = operationLogger.value();
        log.setDescription(description);
        String ip = IpAddressUtil.getIpAddress(request);
        log.setIp(ip);
        String userAgent = request.getHeader("User-Agent");
        log.setUserAgent(userAgent);
        Map<String, Object> requestParams = AopUtil.getRequestParams(joinPoint);
        log.setParam(requestParams.toString());
        log.setCreateTime(new Date());
        log.setTimes(times);
        return log;

    }

}
