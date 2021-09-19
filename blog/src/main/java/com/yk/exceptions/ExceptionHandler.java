package com.yk.exceptions;

import com.yk.model.ResultInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * 捕获全局异常
 * @author yk
 * @version 1.0
 * @date 2021/3/31 21:17
 */

//@RestControllerAdvice
public class ExceptionHandler {

    Logger logger = LoggerFactory.getLogger(this.getClass());

//    ModelAndView modelAndView = new ModelAndView("error/error");

    /**
     * 捕获404异常
     * @param request
     * @param ex
     * @return
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(NotFoundException.class)
    public ResultInfo notFoundExceptHandler(HttpServletRequest request, NotFoundException ex){
        logger.error("Request URL : {} , Exception :",request.getRequestURL(),ex);
//        modelAndView.addObject("code",404);
//        modelAndView.addObject("msg","加载失败!");
        return new ResultInfo(404,ex.getMessage());
    }


    /**
     * 捕获自定义异常
     * @param request
     * @param ex
     * @return
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(MyException.class)
    public ResultInfo myExceptHandler(HttpServletRequest request, NotFoundException ex){
        logger.error("Request URL : {} , Exception :",request.getRequestURL(),ex);
        return new ResultInfo(300,"加载失败!");
    }


    /**
     * 捕获其他异常
     * @param request
     * @param ex
     * @return
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResultInfo exceptHandler(HttpServletRequest request, NotFoundException ex){
        logger.error("Request URL : {} , Exception :",request.getRequestURL(),ex);
        return new ResultInfo(500,"加载失败!");
    }

}
