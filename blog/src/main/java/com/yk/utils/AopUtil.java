package com.yk.utils;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author yk
 * @version 1.0
 * @date 2021/4/2 20:27
 */
public class AopUtil {


    /**
     * AOP获取request参数
     * @param joinPoint
     * @return
     */
    public static Map<String, Object> getRequestParams(JoinPoint joinPoint) {
        Map<String, Object> map = new HashMap<>();
        //参数名
        String[] parameterNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
        //参数值
        //参数名和参数值一一对应
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
//            if (!isFilterObject(args[i])) {
                map.put(parameterNames[i], args[i]);
//            }
        }
        return map;
    }

//    private static boolean isFilterObject(Object arg) {
//        return arg instanceof HttpServletRequest || arg instanceof HttpServletResponse || arg instanceof MultipartFile;
//    }

}
