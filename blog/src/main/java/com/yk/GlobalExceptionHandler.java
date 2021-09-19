package com.yk;

import com.alibaba.fastjson.JSON;
import com.yk.exceptions.MyException;
import com.yk.model.ResultInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 全局异常处理类
 */
@Component
public class GlobalExceptionHandler implements HandlerExceptionResolver {

    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Autowired
    private ResultInfo resultInfo;

    /**
     * 全局异常处理方法
     *  返回值:
     *      1.返回视图
     *      2.返回JSON数据
     *  如何判断方法的返回值?
     *      是否有@ResponseBody注解
     * @param request   请求对象
     * @param response  响应对象
     * @param handler   方法对象
     * @param ex    异常对象
     * @return
     */
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // 设置默认视图
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("code", "500");
        modelAndView.addObject("msg", "系统资源繁忙");

        /*
         * 非法请求拦截
         *  判断是否抛出未登录异常
         *      如果抛出异常,则要求用户登录并跳转页面
         */
        /*if (ex instanceof NoLoginException) {
            // 清除view设置
            modelAndView.clear();
            // 返回到登录页面
            modelAndView.setViewName("redirect:/index");
            logger.error("未登录异常");
            return modelAndView;
        }*/

        // 判断handler
        if (handler instanceof HandlerMethod) {
            //类型转换
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            //获取方法上声明的@ResponseBody注解对象
            ResponseBody responseBody = handlerMethod.getMethod().getDeclaredAnnotation(ResponseBody.class);
            // 返回视图
            if (responseBody == null) {//返回视图
                // 自定义异常
                if (ex instanceof MyException) {
                    MyException myException = (MyException) ex;
                    modelAndView.addObject("code", myException.getCode());
                    modelAndView.addObject("msg", myException.getMsg());
                } /*else if (ex instanceof AuthException) {
                    AuthException authException = (AuthException) ex;
                    modelAndView.addObject("code", authException.getCode());
                    modelAndView.addObject("msg", authException.getMsg());
                }*/
                logger.error("返回视图异常：viewName={}",modelAndView.getViewName());
            } else {    // 返回json数据
                // 设置默认值
                resultInfo.setAll(500, ex.getMessage(), null);
                if (ex instanceof MyException) {
                    MyException myException = (MyException) ex;
                    resultInfo.setAll(myException.getCode(), myException.getMsg(), null);
                }/*else if (ex instanceof AuthException) {
                    AuthException authException = (AuthException) ex;
                    resultInfo.setAll(authException.getCode(), authException.getMsg(), null);
                }*/
                // 设置响应编码utf-8
                response.setContentType("application/json;charset=UTF-8");
                PrintWriter out = null;
                try {
                    //得到输出流
                    out = response.getWriter();
                    // 将需要返回的对象转换成JSON格式的字符
                    String json = JSON.toJSONString(resultInfo);
                    out.write(json);
                } catch (Exception e) {
                    logger.error(e.getMessage());
                } finally {
                    if (out != null) {
                        out.close();
                    }
                }
                logger.error("返回json数据异常：ResultInfo={}",resultInfo);
                return null;
            }
        }
        return modelAndView;
    }
}
